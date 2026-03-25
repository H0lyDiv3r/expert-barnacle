package com.example.sqlpractice.Data

import com.example.sqlpractice.network.Post
import com.example.sqlpractice.network.PostApiService
import retrofit2.http.GET

interface PostRepository {
    suspend fun getPosts() : List<Post>
}

class NetworkPostsRepository(private val postApiService: PostApiService) : PostRepository {

    override  suspend fun getPosts(): List<Post> = postApiService.getPosts()
}