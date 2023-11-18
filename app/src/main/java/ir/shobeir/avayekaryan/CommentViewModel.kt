package ir.shobeir.avayekaryan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.shobeir.avayekaryan.model.Comment
import ir.shobeir.avayekaryan.model.Status
import ir.shobeir.avayekaryan.ui.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val apiService: ApiService
):ViewModel() {
    var commentList = MutableStateFlow<List<Comment>>(emptyList())
    val postError= MutableStateFlow<String?>("")
    val loading = MutableStateFlow(false)
    val status= MutableStateFlow<String?>(null)
    val statusComment= MutableStateFlow<String?>(null)
    fun getComment(idPost:String){
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response= apiService.getComment(idPost)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {comment->
                        commentList.value= comment
                        loading.value = false
                    }
                }
            }

        }
    }

   suspend fun setComment(title:String,name:String,idPost: String){
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response= apiService.setComment(title, name, idPost)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {msg->
                        status.value= msg.status
                        loading.value = false
                    }
                }
            }
        }
    }

   suspend fun updateComment(idPost: String){
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response= apiService.updateComment(idPost)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {msg->
                        statusComment.value= msg.status
                        loading.value = false
                    }
                }
            }
        }
    }
}