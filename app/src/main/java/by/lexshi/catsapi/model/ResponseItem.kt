package by.lexshi.catsapi.model

data class ResponseItem(
    val breeds: List<Any>,
    val categories: List<Category>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)