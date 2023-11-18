package ir.shobeir.avayekaryan.model

data class News(
    val code: String,
    val created_time: String,
    val description: String,
    val id: String,
    val status: String,
    val title: String,
    val type: String,
    val view: String
)