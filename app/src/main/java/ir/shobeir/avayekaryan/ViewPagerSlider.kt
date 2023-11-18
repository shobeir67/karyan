package ir.shobeir.avayekaryan


import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.*
import ir.shobeir.avayekaryan.model.Image
import ir.shobeir.avayekaryan.model.News
import ir.shobeir.avayekaryan.navGragh.Screen
import ir.shobeir.avayekaryan.ui.theme.shabnamBold
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue
@SuppressLint("SuspiciousIndentation")
@ExperimentalPagerApi
@Composable
fun ViewPagerSlider(
    news:List<News>,
   navController: NavHostController,
   sharedViewModel: SharedViewModel,
   image:List<Image>
)
{
    val pageCount = news.size
    val pagerState = rememberPagerState(pageCount)

      LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            tween<Float>(600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        } }
    Column {
        HorizontalPager(state = pagerState, count = pageCount) { page ->
            Card(modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.85f, stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f, stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
            ) {
                val newKids = news[page]
                Banner(newKids=newKids,navController=navController, sharedViewModel = sharedViewModel,image=image)
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState, spacing = 4.dp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            activeColor = Color(0xFF26625E),
            inactiveColor = Color(0xFFBFE6E3)
        ) } }


@SuppressLint("UnrememberedMutableState")
@Composable
fun Banner(newKids: News,navController: NavHostController,sharedViewModel: SharedViewModel,image: List<Image>) {
    val baseUrl = "http://baladom.ir/lovar/"
    val newImage= mutableStateListOf<Image>()
    image.forEach {
        if (it.code == newKids.code){
            newImage.add(it)
        }
    }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        sharedViewModel.addNews(newKids)
                        navController.navigate(Screen.Detail.route)
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(baseUrl+newImage[0].src)
                        .crossfade(true)
                        .build(),
                    contentDescription ="banner",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    Modifier
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
                    Text(text = newKids.title,
                        fontSize = 16.sp, fontFamily = shabnamBold,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }


}

