import com.google.gson.annotations.SerializedName
import com.gunishjain.newsapp.data.local.entity.Article
import com.gunishjain.newsapp.data.model.ApiSource
import com.gunishjain.newsapp.data.model.toSourceEntity

data class ApiArticle(
    val title: String = "",
    val description: String = "",
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("source")
    val apiSource: ApiSource
)


fun ApiArticle.toArticleEntity(): Article {
    return Article(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = apiSource.toSourceEntity()
    )
}