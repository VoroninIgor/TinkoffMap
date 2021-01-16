package com.voronin.tinkoff.data.repository

import com.voronin.api.clients.TinkoffApiClient
import com.voronin.tinkoff.data.mappers.DepositionPointMapper
import com.voronin.tinkoff.db.AppDatabase
import com.voronin.tinkoff.db.DatabaseStorage
import com.voronin.tinkoff.db.entities.DepositionPointRequestEntity
import com.voronin.tinkoff.db.entities.RequestWithDepositionPointEntity
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import io.reactivex.Single
import javax.inject.Inject

class DepositionPointsRepo @Inject constructor(
    private val apiClient: TinkoffApiClient,
    private val depositionPointMapper: DepositionPointMapper,
    databaseStorage: DatabaseStorage,
) {

    private val database: AppDatabase = databaseStorage.db

    companion object {
        private const val STORED_REQ_TIME = 10 * 60 * 1000L
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
                    if (req.latitude == latitude && req.longitude == longitude && req.radius == radius) {
                        return@flatMap Single.just(
                            it.points.map { item -> depositionPointMapper.fromEntityToModel(item) } // запрос найден в кеше
                        )
                    }
                }
            }
            return@flatMap getPointsFromApi(latitude, longitude, radius)
        }
    }

    private fun getPointsFromApi(
        latitude: Double,
        longitude: Double,
        radius: Int,
    ): Single<List<DepositionPoint>> {
        return apiClient.getDepositionPoints(latitude, longitude, radius)
            .map { it.map { apiItem -> depositionPointMapper.fromApiToModel(apiItem) } }
            .doOnSuccess {
                saveRequest(latitude, longitude, radius, it)
            }
    }

    private fun saveRequest(latitude: Double, longitude: Double, radius: Int, list: List<DepositionPoint>) {
        val req = DepositionPointRequestEntity.createInstance(latitude, longitude, radius)
        val reqId = database.depositionPointReqDao().insert(req)

        list.forEach {
            val point = depositionPointMapper.fromModelToEntity(it)

            database.depositionPointDao().insert(point)
            database.requestWithDepositionPointEntityDao().insert(
                RequestWithDepositionPointEntity(
                    requestId = reqId,
                    pointId = point.id
                )
            )
        }
    }
}
