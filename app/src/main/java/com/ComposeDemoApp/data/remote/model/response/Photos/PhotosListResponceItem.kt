package com.ComposeDemoApp.data.remote.model.response.Photos

data class PhotosListResponceItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)