package ir.shobeir.avayekaryan.gallery

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.buildSpannedString
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.shobeir.avayekaryan.*
import ir.shobeir.avayekaryan.R
import ir.shobeir.avayekaryan.ui.theme.shabnamBold

@Composable
fun DetailsGallery(sharedViewModel: SharedViewModel) {
    val viewModel = hiltViewModel<ViewModelImage>()
    val context = LocalContext.current
    val baseUrl = "http://baladom.ir/lovar/"
    val code = sharedViewModel.code
    LaunchedEffect(key1 = Unit){
        viewModel.fetchImageGalleryPost(code!!)
    }
    val imageGalleryState by viewModel.imageGalleryState.collectAsState()
    when (imageGalleryState) {
        is GalleryState.LoadingState -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is GalleryState.Success -> {
            val images = (imageGalleryState as GalleryState.Success).imageGallery
                if (images.isEmpty()){
                    Text(text = "تصویری وجود ندارد...")
                }else
                    
               LazyColumn(modifier = Modifier.fillMaxSize().padding(5.dp, bottom = 45.dp)){
                   items(images.size){index ->
                       Card(modifier = Modifier.fillMaxSize()) {
                           Column {
                               AsyncImage(
                                   model = ImageRequest.Builder(LocalContext.current)
                                       .placeholder(if (MaterialTheme.colors.isLight) R.drawable.loading_light else R.drawable.loading_dark)
                                       .data(baseUrl+images[index].src)
                                       .crossfade(true)
                                       .build(),
                                   contentDescription ="banner",
                                   contentScale = ContentScale.FillWidth,
                                   modifier = Modifier.fillMaxWidth()
                               )
                               if (images[index].onvan != ""){

                                   Text(text = images[index].onvan, fontFamily = shabnamBold, fontSize = 18.sp,
                                       modifier = Modifier.padding(horizontal = 5.dp, vertical = 8.dp)
                                   )
                               }

                           }
                       }
                      
                           
                       
                   }

               }


        }

        is GalleryState.Error -> {
            val message = (imageGalleryState as GalleryState.Error).errorMessage
            Toast.makeText(context, "خطا در دریافت اطلاعات...", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }


}