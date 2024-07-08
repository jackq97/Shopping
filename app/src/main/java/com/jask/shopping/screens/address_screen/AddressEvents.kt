package com.jask.shopping.screens.address_screen

import com.jask.shopping.data.model.Address

sealed class AddressEvents {
    data class AddAddress(val address: Address): AddressEvents()
}