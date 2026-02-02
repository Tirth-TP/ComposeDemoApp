package com.composeDemoApp.data.Photos

data class PhotosListResponseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)