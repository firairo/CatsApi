package by.lexshi.catsapi.network

import by.lexshi.catsapi.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {


    @GET(Constants.END_POINT)
    suspend fun getDataFromAPI(
        @Query("api_key") api_key: String ="609b232f-0fc1-4bc4-839e-204848c256db",
        @Query("limit") limit: Int = 5,
        @Query("page") page: Int = 10,
        @Query("order") order: String = "Desc"
    ): Response<by.lexshi.catsapi.model.Response>


    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 10
    }
}