package by.lexshi.catsapi

import androidx.paging.PagingData
import by.lexshi.catsapi.model.ResponseItem
import kotlinx.coroutines.flow.Flow

interface WorksRepository{
    suspend fun getAllWorks(): Flow<PagingData<ResponseItem>>
}