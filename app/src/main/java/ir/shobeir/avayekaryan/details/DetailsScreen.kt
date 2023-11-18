package ir.shobeir.avayekaryan.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.shobeir.avayekaryan.*
import ir.shobeir.avayekaryan.R
import ir.shobeir.avayekaryan.model.Comment
import ir.shobeir.avayekaryan.navGragh.Screen
import ir.shobeir.avayekaryan.ui.theme.LightBlack
import ir.shobeir.avayekaryan.ui.theme.green
import ir.shobeir.avayekaryan.ui.theme.shabnamBold
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(sharedViewModel: SharedViewModel, navController: NavHostController) {
    val viewModel = hiltViewModel<ViewModelImage>()
    val viewModelComment = hiltViewModel<CommentViewModel>()
    val context = LocalContext.current
    val news = sharedViewModel.news
    var comments by remember {
        mutableStateOf(emptyList<Comment>())
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchImagePost(news?.code!!)
        viewModelComment.getComment(news.id)
        viewModelComment.commentList.collectLatest { allComment ->
            comments = allComment
        }
    }
    var listImage by remember {
        mutableStateOf(emptyList<ImagePost>())
    }
    val imagePostState by viewModel.imagePostState.collectAsState()
    when (imagePostState) {
        is ImagePostState.LoadingState -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ImagePostState.Success -> {
            val images = (imagePostState as ImagePostState.Success).images
            listImage = images
        }
        is ImagePostState.Error -> {
            val message = (imagePostState as ImagePostState.Error).errorMessage

        }
        else -> {}
    }

    val scrollState = rememberScrollState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp, bottom = 47.dp)
            .background(LightBlack)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .graphicsLayer {
                        alpha = 1f - (scrollState.value.toFloat() / scrollState.maxValue)
                        translationY = 0.5f * scrollState.value
                    },
                contentAlignment = Alignment.Center
            ) {
                if (listImage.isEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.ic_baseline_photo_24),
                        contentDescription = "slider",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillWidth,
                        colorFilter = ColorFilter.tint(color = Color.Gray)
                    )
                } else {
                    ImageItemLayout(
                        images = listImage,
                        postBookmarked = false,
                    ){}
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = news?.created_time!!,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd),
                        fontFamily = shabnamBold,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = news?.title!!,
                    modifier = Modifier.padding(8.dp),
                    fontFamily = shabnamBold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold

                    )
                Text(
                    text = news.description,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontFamily = shabnamBold,
                    fontSize = 16.sp
                )
            }

        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "نظرات",
                    fontFamily = shabnamBold, fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)

                Button(
                    onClick = {
                    news?.let { n ->
                        navController.navigate(route = Screen.Comments.route + "/${n.id}")} },
                 shape = RoundedCornerShape(10)
                ) {
                    Text(text = "ثبت نظر",
                        fontFamily = shabnamBold, color = Color.White)
                }
            }
        }
        items(comments.size) { index ->
            ItemComment(comments[index])
        }

    }
}


@Composable
fun ItemComment(comment: Comment) {
    val viewModelComment = hiltViewModel<CommentViewModel>()
    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = comment.name,
                    fontFamily = shabnamBold,
                    fontSize = 14.sp,
                    color = green
                )
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = comment.zaman,
                        fontFamily = shabnamBold,
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = comment.title,
                fontFamily = shabnamBold,
                fontSize = 14.sp,
                )
            if (comment.status == "0"){
                Button(
                    onClick = {
                        scope.launch {
                            viewModelComment.updateComment(comment.id)
                        }
                    },
                    shape = RoundedCornerShape(10)
                ) {
                    Text(text = "تایید",
                        fontFamily = shabnamBold, color = Color.White)
                }
            }

        }
    }
}