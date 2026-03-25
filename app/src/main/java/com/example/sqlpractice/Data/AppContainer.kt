package com.example.sqlpractice.Data

import android.content.Context
import com.example.sqlpractice.network.PostApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val tasksRepository : TasksRepository
    val postRepository : PostRepository
}

class AppDataContainer ( private val context: Context): AppContainer {

    private val baseUrl = "https://jsonplaceholder.typicode.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: PostApiService by lazy {
        retrofit.create(PostApiService::class.java)
    }

    override val postRepository : PostRepository by lazy {
        NetworkPostsRepository(retrofitService)
    }
    override val tasksRepository: TasksRepository by lazy{
        OfflineTasksRepository(TaskDatabase.getTaskDatabase(context).taskDao())
    }



}
