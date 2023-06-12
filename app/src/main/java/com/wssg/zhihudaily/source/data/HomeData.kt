package com.wssg.zhihudaily.source.data

/**
 * ...
 * @author RQ527 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2023/6/1
 * @Description:
 */
data class HomeData(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
) {
    data class TopStory(
        val ga_prefix: String,
        val hint: String,
        val id: Int,
        val image: String,
        val image_hue: String,
        val title: String,
        val type: Int,
        val url: String
    )
}

data class Story(
    val ga_prefix: String,
    val hint: String,
    val id: Int,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String
)