package ir.shobeir.avayekaryan.ui


import ir.shobeir.avayekaryan.ImagePost
import ir.shobeir.avayekaryan.ModelGallery
import ir.shobeir.avayekaryan.ModelNews
import ir.shobeir.avayekaryan.Slider
import ir.shobeir.avayekaryan.model.Comment
import ir.shobeir.avayekaryan.model.GalleryImagePost
import ir.shobeir.avayekaryan.model.ResultNews
import ir.shobeir.avayekaryan.model.Status
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("get-news.php")
    suspend fun getCategory(): ResultNews

    @FormUrlEncoded
    @POST("get-image-news.php")
    suspend fun getImagePost(@Field("code") code: String): List<ImagePost>


//    @FormUrlEncoded
//    @POST("get-comments.php")
//    suspend fun getComment(@Field("id_post") id_post: String): Response<List<Comment>>

    @FormUrlEncoded
    @POST("get-comments-admin.php")
    suspend fun getComment(@Field("id_post") id_post: String): Response<List<Comment>>

    @FormUrlEncoded
    @POST("update-comment.php")
    suspend fun updateComment(@Field("id_post") id_post: String): Response<Status>

    @FormUrlEncoded
    @POST("get-image-gallery-post.php")
    suspend fun getImageGalleryPost(@Field("code") code: String): List<GalleryImagePost>

    @FormUrlEncoded
    @POST("set-comment.php")
    suspend fun setComment(
        @Field("title") title: String,
        @Field("name") name: String,
        @Field("idPost") idPost: String,
    ): Response<Status>


    @GET("get-image-gallery.php")
    suspend fun getImageGallery(): List<ModelGallery>


}