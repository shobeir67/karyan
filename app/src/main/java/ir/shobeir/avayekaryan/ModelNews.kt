package ir.shobeir.avayekaryan

import ir.shobeir.avayekaryan.model.News

data class ModelNews(
    val news:List<News>? = null,
    val images:List<ImagePost>? = null
)
