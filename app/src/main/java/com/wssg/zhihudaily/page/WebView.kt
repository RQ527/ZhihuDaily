package com.wssg.zhihudaily.page

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/7
 * @Description:
 */
@Composable
fun WebView(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply { loadUrl(url) }
    })
}