package ir.shobeir.avayekaryan.home


import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.shobeir.avayekaryan.*
import ir.shobeir.avayekaryan.R
import ir.shobeir.avayekaryan.model.Image
import ir.shobeir.avayekaryan.model.News
import ir.shobeir.avayekaryan.navGragh.Screen
import ir.shobeir.avayekaryan.ui.theme.shabnamBold

@SuppressLint("UnrememberedMutableState")
@Composable
fun PostItemLayout(
    news: News,
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    image:List<Image>,
) {

    val newImage= mutableStateListOf<Image>()

    image.forEach {
        if (it.code == news.code){
            newImage.add(it)
        }
    }
    val baseUrl = "http://baladom.ir/lovar/"
     Row(
         modifier = Modifier
             .fillMaxWidth()
             .height(120.dp)
             .padding(5.dp)
             .clickable {
                 sharedViewModel.addNews(news)
                 navController.navigate(Screen.Detail.route)
             },
            horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            text = news.title,
                            fontFamily = shabnamBold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Icon(painter = painterResource(id = R.drawable.calendar), contentDescription ="date", tint = Color.Gray)
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Text(
                                    text = news.created_time,
                                    fontFamily = shabnamBold,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                        
                    }
                    if (newImage[0].src!!.isEmpty()){
                        Image(painter = painterResource(R.drawable.ic_baseline_photo_24),
                            contentDescription = "slider",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.FillWidth,
                            colorFilter = ColorFilter.tint(color = Color.Gray)
                        )
                    }else{
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .placeholder(if (MaterialTheme.colors.isLight) R.drawable.loading_light else R.drawable.loading_dark)
                                .data(baseUrl+newImage[0].src)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(10.dp)),

                        )
                    }
                }
}






