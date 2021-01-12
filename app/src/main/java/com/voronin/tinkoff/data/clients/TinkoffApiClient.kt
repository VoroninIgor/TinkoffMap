package com.voronin.tinkoff.data.clients

import com.voronin.tinkoff.data.TinkoffApiService
import javax.inject.Inject

class TinkoffApiClient @Inject constructor(
    private val api: TinkoffApiService,
) {

}
