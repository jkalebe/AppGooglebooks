package br.com.example.googlebooks.model

data class VolumeInfo(
    val title: String,
    val descriptions: String?,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?,
    val pageCount: Int?,
    val imageLinks: ImageLinks?


)