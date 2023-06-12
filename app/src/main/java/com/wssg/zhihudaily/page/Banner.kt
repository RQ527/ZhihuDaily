package com.wssg.zhihudaily.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.wssg.zhihudaily.source.data.HomeData
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/6
 * @Description:
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(articles: List<HomeData.TopStory>, onItemClicked: (url: String) -> Unit) {
    val pagerState = rememberPagerState()
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (pager, indicator) = createRefs()
        HorizontalPager(
            pageCount = Int.MAX_VALUE,
            Modifier
                .height(300.dp)
                .fillMaxWidth()
                .constrainAs(pager) {},
            state = pagerState
        ) {
            val index = it % articles.size
            BannerItem(
                titleStr = articles[index].title,
                authorStr = articles[index].hint,
                url = articles[index].image
            ) {
                onItemClicked(articles[index].url)
            }
        }
        BannerIndicator(
            Modifier
                .padding(bottom = 5.dp, end = 5.dp)
                .constrainAs(indicator) {
                    end.linkTo(pager.end)
                    bottom.linkTo(pager.bottom)
                },
            count = articles.size,
            currentIndex = pagerState.currentPage % articles.size
        )
        val coroutineScope = rememberCoroutineScope()
        DisposableEffect(key1 = Unit) {
            val time = Timer()
            time.schedule(object : TimerTask() {
                override fun run() {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                    }
                }
            }, 2000, 2000)
            onDispose {
                time.cancel()
            }
        }
    }
}

@Composable
fun BannerItem(
    modifier: Modifier = Modifier,
    titleStr: String,
    authorStr: String,
    url: String,
    clicked: () -> Unit
) {
    ConstraintLayout(modifier.clickable { clicked() }) {
        val (image, title, author) = createRefs()
        AsyncImage(
            model = url, contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {

                },
            contentScale = ContentScale.Crop,
        )
        Text(text = titleStr, modifier = Modifier
            .constrainAs(title) {
                bottom.linkTo(author.top)
            }
            .wrapContentSize()
            .padding(start = 17.dp, end = 17.dp),
            color = Color.White, fontSize = 20.sp, fontStyle = FontStyle.Normal)
        Text(
            modifier = Modifier
                .padding(start = 17.dp, end = 17.dp, top = 5.dp, bottom = 20.dp)
                .constrainAs(author) {
                    bottom.linkTo(title.bottom)
                    start.linkTo(image.start)
                },
            text = authorStr,
            color = Color.LightGray,
            fontSize = 13.sp
        )
    }
}

@Composable
fun BannerIndicator(
    modifier: Modifier = Modifier,
    count: Int,
    currentIndex: Int,
    itemSize: Dp = 6.dp,
    padding: Dp = 3.dp
) {
    LazyRow(
        modifier
            .wrapContentSize()
            .padding(end = 10.dp)
    ) {
        items(count = count) {
            Box(Modifier.padding(padding)) {
                Box(
                    modifier = Modifier
                        .height(itemSize)
                        .width(itemSize)
                        .clip(CircleShape)
                        .background(if (currentIndex == it) Color.Gray else Color.LightGray)
                )
            }
        }
    }
}