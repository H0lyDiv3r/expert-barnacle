package com.example.sqlpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.sqlpractice.Data.NetworkPostsRepository
import com.example.sqlpractice.Data.PostRepository
import com.example.sqlpractice.network.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(private val postRepository: PostRepository) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()


    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            _posts.value = postRepository.getPosts()
        }

        println("printing a line here ${posts.value}")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PostsApplication)
                val postsRepository = application.container.postRepository
                PostsViewModel(postsRepository)
            }
        }
    }

}