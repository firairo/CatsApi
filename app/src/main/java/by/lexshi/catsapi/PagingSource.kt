package by.lexshi.catsapi

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.lexshi.catsapi.model.ResponseItem
import by.lexshi.catsapi.network.Api
import retrofit2.HttpException
import kotlin.streams.toList

class PagingSource(private val apiService: Api): PagingSource<Int, ResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, ResponseItem> {
        return try{
            val pageNumber: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getDataFromAPI(page = pageNumber)

            if (response.isSuccessful) {
                val data = checkNotNull(response.body())
                val repos = data.stream().map { it }.toList()


                val nextPageNumber = if (data.isEmpty()) null else pageNumber + 5
                val prevPageNumber = if (pageNumber > 1) pageNumber - 5 else null

                LoadResult.Page(data = repos, prevKey = prevPageNumber, nextKey = nextPageNumber)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }



}