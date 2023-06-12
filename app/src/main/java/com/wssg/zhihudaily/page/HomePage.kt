package com.wssg.zhihudaily.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.wssg.zhihudaily.viewmodel.HomeViewModel

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/6
 * @Description:
 */
@Composable
fun HomePage(viewModel: HomeViewModel = viewModel(), navigatePage: (url: String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = viewModel.getHomePagingData().collectAsLazyPagingItems()
    if (!uiState.isLoading && !uiState.isError)
        LazyColumn() {
            items(pagingData.itemCount + 1) {
                if (it == 0) Banner(articles = uiState.topArticle) { url -> navigatePage(url) }
                else {
                    val article = pagingData[it - 1]!!
                    HomeItem(
                        titleStr = article.title,
                        authorStr = article.hint,
                        imageStr = article.images[0],
                    ) {
                        navigatePage(article.url)
                    }
                }
            }
        }
}

@Composable
fun HomeItem(
    titleStr: String,
    authorStr: String,
    imageStr: String,
    clicked: () -> Unit
) {
    ConstraintLayout(
        Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable { clicked() }
    ) {
        val (title, author, image) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.top)
                    start.linkTo(parent.start)
                }
                .width(250.dp),
            text = titleStr,
            color = Color.Black,
            fontSize = 15.sp
        )
        Text(
            modifier = Modifier
                .constrainAs(author) {
                    bottom.linkTo(image.bottom)
                    start.linkTo(parent.start)
                }
                .width(250.dp),
            text = authorStr,
            color = Color.Gray,
            fontSize = 12.sp
        )
        AsyncImage(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .constrainAs(image) {
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            model = imageStr,
            contentDescription = null
        )
    }
}