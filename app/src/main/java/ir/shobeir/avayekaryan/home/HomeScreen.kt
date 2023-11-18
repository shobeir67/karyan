package ir.shobeir.avayekaryan.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import ir.shobeir.avayekaryan.*
import ir.shobeir.avayekaryan.model.Image
import ir.shobeir.avayekaryan.model.News
import ir.shobeir.avayekaryan.ui.theme.shabnamBold
import ir.shobeir.baladomapp.cheknet.ConnectionState
import ir.shobeir.baladomapp.cheknet.connectivityState


import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel:SharedViewModel
) {
    val viewModelCategory = hiltViewModel<MainViewModel>()
    val context = LocalContext.current
    val categoryState by viewModelCategory.categoryState.collectAsState()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
                when (categoryState) {
                    is CategoryState.LoadingState -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is CategoryState.Success -> {
                        val category = (categoryState as CategoryState.Success).categories
                        Column( modifier = Modifier.fillMaxSize()) {
                            MainScreen(
                                news = category.news,
                                image=category.image,
                                navController = navController,
                                sharedViewModel = sharedViewModel,
                            )
                        }
                    }

                    is CategoryState.Error -> {
                        val message = (categoryState as CategoryState.Error).errorMessage
                    }
                    else -> {}
                }
        }
}
@OptIn(ExperimentalPagerApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun MainScreen(
    news: List<News>,
    image:List<Image>,
    navController: NavHostController,
    sharedViewModel: SharedViewModel) {
    val newsList = mutableListOf<News>()
    news.forEach {
        if (it.type == "1") {
            newsList.add(it)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
    ) {
        item {
            ViewPagerSlider(news = newsList,navController=navController, sharedViewModel = sharedViewModel,image=image)
        }
        items(news.size) { index ->
            PostItemLayout(
                news = news[index],
                image=image,
                navController = navController,
                sharedViewModel = sharedViewModel,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
        }

    }
}








