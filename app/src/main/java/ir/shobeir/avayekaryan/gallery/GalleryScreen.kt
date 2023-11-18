package ir.shobeir.avayekaryan.gallery


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.shobeir.avayekaryan.*
import ir.shobeir.avayekaryan.R
import ir.shobeir.avayekaryan.navGragh.Screen
import ir.shobeir.avayekaryan.ui.theme.shabnamBold



@Composable
fun GalleryScreen(sharedViewModel: SharedViewModel,
                  navController: NavHostController,) {
    val viewModel = hiltViewModel<GetImageViewModel>()
    val baseUrl = "http://baladom.ir/lovar/"
    val imageState by viewModel.imageState.collectAsState()
    when (imageState) {
        is ImageState.LoadingState -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ImageState.Success -> {
            val posts = (imageState as ImageState.Success).imagesPost
            if (posts.isEmpty()){
                Box(modifier= Modifier.fillMaxSize()) {
                    Text(text = "تصویری وجود ندارد...")
                
                }
            }else{
                    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(3.dp, bottom = 45.dp)
                    ){
                        items(posts.size){index->
                            Box(
                                Modifier
                                       .height(150.dp)
                                       .clickable {
                                        sharedViewModel.addCode(posts[index].code!!)
                                        navController.navigate(Screen.DetailGallery.route)
                                    }
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .placeholder(if (MaterialTheme.colors.isLight) R.drawable.loading_light else R.drawable.loading_dark)
                                        .data(baseUrl+posts[index].src)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription ="banner",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Box(
                                    Modifier
                                        .padding()
                                        .fillMaxWidth()
                                        .align(Alignment.BottomStart)
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color.Black,

                                                    )
                                            )
                                        )
                                ) {
                                    androidx.compose.material.Text(
                                        text = posts[index].title!!,
                                        fontSize = 14.sp, fontFamily = shabnamBold,
                                        color = Color.White,
                                        modifier =  Modifier
                                            .padding(5.dp)
                                            .align(Alignment.Center)
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                }
                            }
                        }
                }
            }
        }
        is ImageState.Error->{

        }
        else->{}
    }

}