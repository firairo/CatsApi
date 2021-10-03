package by.lexshi.catsapi

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.lexshi.catsapi.model.ResponseItem
import by.lexshi.catsapi.network.RestClient
import kotlinx.coroutines.flow.Flow

class WorksRepositoryImpl (private val apiService: RestClient) : WorksRepository {
    override suspend fun getAllWorks(): Flow<PagingData<ResponseItem>> = Pager(
        // не применяй магические значения, выноси всегда в именованые константы
        config = PagingConfig(pageSize = 10, maxSize = 30),
        pagingSourceFactory = { PagingSource(apiService) }
    ).flow

}