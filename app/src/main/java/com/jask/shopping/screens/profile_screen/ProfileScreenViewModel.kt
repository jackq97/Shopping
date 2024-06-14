package com.jask.shopping.screens.profile_screen

import androidx.lifecycle.ViewModel
import com.jask.shopping.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    fun onEvent(event: ProfileEvents){
        when(event){
            is ProfileEvents.SignOut -> {
                signOut()
            }
        }
    }

    private fun signOut() {
        repository.googleSignOut()
    }
}