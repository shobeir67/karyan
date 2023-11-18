package ir.shobeir.avayekaryan



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.shobeir.avayekaryan.ui.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetImageViewModel @Inject constructor(
    private val apiService: ApiService,
    ):ViewModel()  {
    val imageState = MutableStateFlow<ImageState>(ImageState.StartState)
    init {
        fetchImage()
    }

   private fun fetchImage(){

                viewModelScope.launch {
                    imageState.tryEmit(ImageState.LoadingState)
                    try {
                        val imagePost = apiService.getImageGallery()
                        imageState.tryEmit(ImageState.Success(imagePost))
                    } catch (e: Exception) {
                        imageState.tryEmit(ImageState.Error(e.localizedMessage!!))
                    }
                }
            }

}

sealed class ImageState{
    object StartState: ImageState()
    object LoadingState: ImageState()
    data class Success(val imagesPost:List<ModelGallery>):ImageState()
    data class Error(val errorMessage:String):ImageState()
}