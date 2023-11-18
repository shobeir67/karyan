package ir.shobeir.avayekaryan

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ir.shobeir.avayekaryan.ui.theme.Dimension
import timber.log.Timber

@Composable
fun ImageItemLayout(
    images: List<ImagePost>,
    postBookmarked: Boolean,
    onPostBookmarkChange: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(0.8f)),
    ) {
        PostImagesWithReactions(
            images = images,
            postBookmarked = postBookmarked,
            onPostBookmarkChange = onPostBookmarkChange,
        )
       }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostImagesWithReactions(
    images: List<ImagePost>,
    postBookmarked: Boolean,
    onPostBookmarkChange: () -> Unit,
) {
    val pagerState = rememberPagerState()
    var showHeart by remember { mutableStateOf(false) }
    var heartSize by remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0) }
    val animationDuration = 1500

    val heartTransition =
        updateTransition(targetState = showHeart, label = "heart")

    /** Keep track of when animation end, we should hide the heart icon */
    animateIntAsState(
        targetValue = heartSize,
        animationSpec = TweenSpec(durationMillis = animationDuration),
        finishedListener = {
            Timber.d("Update size ... ")
            showHeart = false
            heartSize = 0
        }
    )
    val animatedHeartSize by heartTransition.animateInt(
        label = "size",
        transitionSpec = {
            TweenSpec(durationMillis = animationDuration)
        },
        targetValueByState = {
            if (it) heartSize else 0
        }
    )
    val animatedOffset by heartTransition.animateInt(
        label = "offset",
        transitionSpec = {
            TweenSpec(durationMillis = animationDuration)
        },
        targetValueByState = {
            if (it) offset else 0
        }
    )
    val animatedAlpha by heartTransition.animateFloat(
        label = "alpha",
        transitionSpec = {
            TweenSpec(durationMillis = animationDuration)
        },
        targetValueByState = {
            if (it) 0f else 1f
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimension.xs),
    ) {
        val imageModifier = if (images.size > 1) Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f) else Modifier.fillMaxWidth()

        Box(modifier = Modifier.fillMaxWidth()) {
            val baseUrl = "https://baladom.ir/lovar/"
            /** Horizontal pager section */
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                count = images.size,
                state = pagerState,
                itemSpacing = Dimension.zero,
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .placeholder(if (MaterialTheme.colors.isLight) R.drawable.loading_light else R.drawable.loading_dark)
                        .data(baseUrl+images[this.currentPage].src!!)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = imageModifier

                )

            }

            /** The image's count when the post contains more than images */
            if (images.size > 1) {
                Text(
                    text = "${pagerState.currentPage.inc()}/${images.size}",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .padding(Dimension.pagePadding)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.onBackground.copy(alpha = 0.8f))
                        .padding(horizontal = Dimension.sm, vertical = Dimension.xs.div(2))
                        .align(Alignment.TopEnd),
                    color = MaterialTheme.colors.background,
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.pagePadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /** Again, if there are many images, then show the pager indicators */
            if (images.size > 1) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = Dimension.pagePadding.times(2)),
                        horizontalArrangement = Arrangement.spacedBy(Dimension.xs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        items(pagerState.pageCount) { index ->
                            Box(
                                modifier = Modifier
                                    .size(
                                        if (pagerState.currentPage == index) Dimension.sm.times(0.7f)
                                        else Dimension.sm.times(0.6f)
                                    )
                                    .clip(CircleShape)
                                    .background(
                                        if (pagerState.currentPage == index) Blue
                                        else MaterialTheme.colors.onBackground.copy(alpha = 0.3f)
                                    )
                            )
                        }
                    }
                }
            }
            DrawableButton(
                backgroundColor = MaterialTheme.colors.background,
                iconTint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                shape = CircleShape,
                painter = painterResource(id = if (postBookmarked) R.drawable.ic_bookmark_filled
                else R.drawable.ic_bookmark
                ),
                onButtonClicked = onPostBookmarkChange,
            )
        }
    }
}


@Composable
fun DrawableButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconTint: Color,
    onButtonClicked: () -> Unit,
    painter: Painter,
    shape: Shape = MaterialTheme.shapes.medium,
    iconSize: Dp = Dimension.mdIcon.times(0.8f),
    elevation: Dp = Dimension.zero,
    paddingValue: PaddingValues = PaddingValues(Dimension.xs),
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable {
                onButtonClicked()
            }
            .padding(paddingValues = paddingValue)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(iconSize),
            painter = painter,
            contentDescription = "icon next",
            tint = iconTint,
        )
    }
}