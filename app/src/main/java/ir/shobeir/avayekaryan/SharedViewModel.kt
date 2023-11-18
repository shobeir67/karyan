package ir.shobeir.avayekaryan


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ir.shobeir.avayekaryan.model.News


class SharedViewModel :ViewModel(){

    var news by  mutableStateOf<News?>(null)
        private set

    fun addNews(newNews:News){
        news = newNews
    }

    var code by  mutableStateOf<String?>(null)
        private set

    fun addCode(newCode:String){
       code = newCode
    }


}