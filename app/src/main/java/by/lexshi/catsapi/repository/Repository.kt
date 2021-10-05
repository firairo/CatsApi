package by.lexshi.catsapi

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.lexshi.catsapi.model.ResponseItem
import by.lexshi.catsapi.network.Api
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorksRepositoryImpl @Inject constructor(private val apiService: Api) : WorksRepository {

    override suspend fun getAllWorks(): Flow<PagingData<ResponseItem>> = Pager(
        // не применяй магические значения, выноси всегда в именованые константы
        config = PagingConfig(pageSize = 5, maxSize = 30),
        pagingSourceFactory = { PagingSource(apiService) }
    ).flow

}