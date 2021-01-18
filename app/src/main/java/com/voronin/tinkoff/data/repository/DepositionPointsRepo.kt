package com.voronin.tinkoff.data.repository

import com.voronin.api.clients.TinkoffApiClient
import com.voronin.api.dto.DepositionPartnerDto
import com.voronin.tinkoff.data.mappers.DepositionMapper
import com.voronin.tinkoff.data.mappers.ImageUrlMapper
import com.voronin.tinkoff.db.AppDatabase
import com.voronin.tinkoff.db.DatabaseStorage
import com.voronin.tinkoff.db.entities.DepositionPointRequestEntity
import com.voronin.tinkoff.db.entities.RequestWithDepositionPointEntity
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DepositionPointsRepo @Inject constructor(
    private val apiClient: TinkoffApiClient,
    private val depositionMapper: DepositionMapper,
    private val imageUrlMapper: ImageUrlMapper,
    databaseStorage: DatabaseStorage,
) {

    private val database: AppDatabase = databaseStorage.db

    companion object {
        private const val STORED_REQ_TIME = 10 * 60 * 1000L
    }

    fun getAllPointsFromDatabase(): Single<List<DepositionPoint>> {
        return database.depositionPointDao().getAll().map { list ->
            list.map { depositionMapper.fromEntityToModel(it) }
        }
    }

    fun getDepositionPoints(
        latitude: Double,
        longitude: Double,
        radius: Int,
    ): Single<List<DepositionPoint>> {
        return database.requestWithDepositionPointEntityDao().getRequestWithPoints().flatMap { list ->
            val timeNow = System.currentTimeMillis()

            list.forEach {
                val req = it.request
                if (timeNow - req.timestamp > STORED_REQ_TIME) {
                    database.depositionPointReqDao().delete(req) // удаление запроса по истечению времени
                    database.requestWithDepositionPointEntityDao().delete(req.id)
                } else {
                    val isReqHasSameLocation = req.latitude == latitude && req.longitude == longitude && req.radius == radius
                    if (isReqHasSameLocation && it.points.isNotEmpty()) {
                        return@flatMap Single.just(
                            it.points.map { item -> depositionMapper.fromEntityToModel(item) } // запрос найден в кеше
                        )
                    }
                }
            }
            return@flatMap getPointsFromApi(latitude, longitude, radius)
        }
    }

    fun setPointViewed(point: DepositionPoint): Completable {
        val entity = depositionMapper.fromModelToEntity(point).copy(
            isViewed = true
        )
        return database.depositionPointDao().insert(entity)
    }

    private fun getPointsFromApi(
        latitude: Double,
        longitude: Double,
        radius: Int,
    ): Single<List<DepositionPoint>> {
        return Single.zip(
            apiClient.getDepositionPoints(latitude, longitude, radius),
            getDepositionsPartner(),
            { points, partners ->
                val listWithImages = points.map { point ->
                    depositionMapper.fromApiToModel(point).copy(
                        images = imageUrlMapper.getImageUrl(
                            partners.find { it.id == point.partnerName }?.picture ?: ""
                        )
                    )
                }
                saveRequest(latitude, longitude, radius, listWithImages)
                listWithImages
            }
        )
    }

    private fun getDepositionsPartner(): Single<List<DepositionPartnerDto>> {
        return database.depositionPartnerDao().getAll().flatMap { partners ->
            return@flatMap if (partners.isEmpty()) {
                apiClient.getDepositionPartners().doOnSuccess {
                    saveDepositionsPartner(it)
                }
            } else {
                Single.just(partners.map { depositionMapper.fromEntityToModel(it) })
            }
        }
    }

    private fun saveRequest(latitude: Double, longitude: Double, radius: Int, list: List<DepositionPoint>) {
        val req = DepositionPointRequestEntity.createInstance(latitude, longitude, radius)
        val reqId = database.depositionPointReqDao().insert(req)

        list.forEach {
            val point = depositionMapper.fromModelToEntity(it)

            database.depositionPointDao().insert(point)
            database.requestWithDepositionPointEntityDao().insert(
                RequestWithDepositionPointEntity(
                    requestId = reqId,
                    pointId = point.id
                )
            )
        }
    }

    private fun saveDepositionsPartner(list: List<DepositionPartnerDto>) {
        val entities = list.map { depositionMapper.fromApiToEntity(it) }
        database.depositionPartnerDao().insertAll(*entities.toTypedArray())
    }
}
