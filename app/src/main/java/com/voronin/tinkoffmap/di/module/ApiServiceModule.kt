package com.voronin.tinkoffmap.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ApiServiceModule {

    companion object {
        private const val CERT_PASSWORD = "CbLeZRCh"
    }

//    @Provides
//    @Singleton
//    fun provideManagedChannel(
//        appContext: Context,
//        unauthenticatedInterceptor: UnauthenticatedInterceptor,
//        metadataInterceptor: MetadataInterceptor,
//        endpointRepository: EndpointRepository,
//    ): ManagedChannel {
//        val address = endpointRepository.provideEndpoint()
//        val sslContext = if (address == endpointRepository.provideDevEndpoint()) {
//            val certFile = appContext.resources.openRawResource(R.raw.stage_client_hh)
//            val keyStore = KeyStore.getInstance("PKCS12").apply {
//                load(certFile, CERT_PASSWORD.toCharArray())
//            }
//            val keyManagerFactory = KeyManagerFactory.getInstance("X509").apply {
//                init(keyStore, CERT_PASSWORD.toCharArray())
//            }
//            SSLContext.getInstance("TLS").apply {
//                init(keyManagerFactory.keyManagers, null, SecureRandom())
//            }
//        } else {
//            null
//        }
//        return OkHttpChannelBuilder
//            .forAddress(address.address, address.port)
//            .intercept(
//                LoggingInterceptor(address),
//                TimeoutInterceptor(),
//                GrpcChuckerInterceptor(
//                    address = address.address,
//                    port = address.port,
//                    context = appContext,
//                    onErrorMapper = object : OnErrorMapper {
//                        override fun mapErrorMessageToString(status: Status, metadata: Metadata): String {
//                            return if (!status.isOk) {
//                                val prettyGson = GsonBuilder().setPrettyPrinting().create()
//                                prettyGson.toJson(StatusException(status, metadata).getErrorModel())
//                            } else {
//                                ""
//                            }
//                        }
//                    }
//                ),
//                unauthenticatedInterceptor
//            )
//            .intercept(metadataInterceptor)
//            .apply {
//                if (sslContext != null) {
//                    sslSocketFactory(sslContext.socketFactory)
//                }
//            }
//            .build()
//    }

//    @Provides
//    @Singleton
//    fun provideVerifyApi(channel: ManagedChannel): VerifyAPIGrpcKt.VerifyAPICoroutineStub {
//        return VerifyAPIGrpcKt.VerifyAPICoroutineStub(channel)
//    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}
