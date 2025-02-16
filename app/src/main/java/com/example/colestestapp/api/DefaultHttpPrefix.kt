package com.example.colestestapp.api

const val DEFAULT_HTTP_PREFIX = "https://coles.com.au"

fun getFullUrl(url: String): String {
    return DEFAULT_HTTP_PREFIX + url
}