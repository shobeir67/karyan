package ir.shobeir.avayekaryan

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.shobeir.avayekaryan.model.News
import ir.shobeir.avayekaryan.model.ResultNews
import ir.shobeir.avayekaryan.ui.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
):ViewModel()  {
     var searchList = mutableStateListOf<News>()
     val categoryState = MutableStateFlow<CategoryState>(CategoryState.StartState)

    init {
        fetchCategory()
    }

    private fun fetchCategory(){
        viewModelScope.launch {
            categoryState.tryEmit(CategoryState.LoadingState)
            try {
                val categories = apiService.getCategory()
                categoryState.tryEmit(CategoryState.Success(categories))
            }catch (e:Exception){
                categoryState.tryEmit(CategoryState.Error(e.localizedMessage!!))
            }
        }
    }

}

sealed class CategoryState{
    object StartState: CategoryState()
    object LoadingState: CategoryState()
    data class Success(val categories:ResultNews): CategoryState()
    data class Error(val errorMessage:String): CategoryState()
}



