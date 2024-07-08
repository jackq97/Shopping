package com.jask.shopping.screens.address_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.data.model.Address
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {


    private fun addAddress(address: Address) = viewModelScope.launch {

        repository.addAddress(address).collect { response ->
            when (response) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                }
                is Resource.Error -> {
                }
            }
        }
    }

    fun onEvent(event: AddressEvents){
        when(event){
            is AddressEvents.AddAddress -> {
                addAddress(event.address)
            }
        }
    }

}