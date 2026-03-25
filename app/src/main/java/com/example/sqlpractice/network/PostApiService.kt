package com.example.sqlpractice.network

import retrofit2.http.GET


data class Post (val id: Int, val title: String, val body: String)

public interface PostApiService {
    @GET("posts")
    suspend fun getPosts() : List<Post>
}
