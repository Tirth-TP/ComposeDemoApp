package com.composeDemoApp.data.remote.model.response.Photos

data class PhotosListResponceItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)