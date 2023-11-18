package ir.shobeir.avayekaryan


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.shobeir.avayekaryan.model.GalleryImagePost
import ir.shobeir.avayekaryan.model.News
import ir.shobeir.avayekaryan.ui.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelImage @Inject constructor(
    private val apiService: ApiService
):ViewModel()  {
    var searchList = mutableStateListOf<News>()
    val imagePostState = MutableStateFlow<ImagePostState>(ImagePostState.StartState)

    suspend fun fetchImagePost(code:String){
            viewModelScope.launch {
                imagePostState.tryEmit(ImagePostState.LoadingState)
                try {
                    val images = apiService.getImagePost(code)
                    imagePostState.tryEmit(ImagePostState.Success(images))
                }catch (e:Exception){
                    imagePostState.tryEmit(ImagePostState.Error(e.localizedMessage!!))
                }
            }
    }
    val imageGalleryState = MutableStateFlow<GalleryState>( GalleryState.StartState)
    suspend fun fetchImageGalleryPost(code:String){
        viewModelScope.launch {
            imageGalleryState.tryEmit(GalleryState.LoadingState)
            try {
                val images = apiService.getImageGalleryPost(code)
                imageGalleryState.tryEmit(GalleryState.Success(images))
            }catch (e:Exception){
                imageGalleryState.tryEmit(GalleryState.Error(e.localizedMessage!!))
            }
        }
    }
}

sealed class ImagePostState{
    object StartState: ImagePostState()
    object LoadingState: ImagePostState()
    data class Success(val images:List<ImagePost>): ImagePostState()
    data class Error(val errorMessage:String): ImagePostState()
}


sealed class GalleryState{
    object StartState: GalleryState()
    object LoadingState: GalleryState()
    data class Success(val imageGallery:List<GalleryImagePost>): GalleryState()
    data class Error(val errorMessage:String): GalleryState()
}

