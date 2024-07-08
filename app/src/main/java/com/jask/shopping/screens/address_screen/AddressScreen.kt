package com.jask.shopping.screens.address_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jask.shopping.data.model.Address

@Composable
fun AddressScreen(
    onEvent: (AddressEvents) -> Unit
){

    val context = LocalContext.current

    var address by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    Surface(modifier = Modifier
        .fillMaxSize()) {

        Column(modifier = Modifier
            .padding(16.dp)
        ) {

            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                Text(text = "Addresses",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            AddressTextField(
                value = address,
                modifier = Modifier,
                onValueChange = {
                    address = it
                },
                label = "Address Location Ex: Home"
            )

            AddressTextField(
                value = fullName,
                modifier = Modifier,
                onValueChange = {
                    fullName = it
                },
                label = "Full Name"
            )

            AddressTextField(
                value = street,
                modifier = Modifier,
                onValueChange = {
                    street = it
                },
                label = "Street"
            )
            AddressTextField(
                value = phone,
                modifier = Modifier,
                onValueChange = {
                    phone = it
                },
                label = "Phone",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done, // ** Go to next **
                    keyboardType = KeyboardType.Phone
                )
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                AddressTextField(
                    value = city,
                    modifier = Modifier
                        .weight(1f),
                    onValueChange = {
                        city = it
                    },
                    label = "City"
                )
                Spacer(modifier = Modifier.width(4.dp))
                AddressTextField(
                    value = state,
                    modifier = Modifier
                        .weight(1f),
                    onValueChange = {
                        state = it
                    },
                    label = "State"
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                AddressButton(
                    modifier = Modifier
                        .weight(1f),
                    value = "Delete",
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(4.dp))
                AddressButton(
                    modifier = Modifier
                        .weight(1f),
                    value = "Save",
                    containerColor = Color(0xFF0013B3),
                    onClick = {
                        if (address.isNotEmpty() && fullName.isNotEmpty() && street.isNotEmpty() && phone.isNotEmpty() && city.isNotEmpty() && state.isNotEmpty()) {
                            onEvent(
                                AddressEvents.AddAddress(
                                    address = Address(
                                        addressTitle = address,
                                        fullName = fullName,
                                        street = street,
                                        phone = phone,
                                        city = city,
                                        state = state
                                    )
                                )
                            )
                        } else {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AddressTextField(
    modifier: Modifier,
    value: String= "",
    label: String = "Address",
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done, // ** Go to next **
        keyboardType = KeyboardType.Text
    )
){

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(20),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = keyboardOptions,
        placeholder = { Text(text = label,
            color = Color.Gray
        )
        }
    )
}

@Composable
fun AddressButton(
    modifier: Modifier = Modifier,
    value: String = "Save",
    containerColor: Color = Color.Gray,
    shape: Shape = RoundedCornerShape(20),
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = onClick) {
        Text(text = value)
    }
}

@Composable
@Preview(
    showBackground = true
)
fun AddressScreenPreview(){

    //AddressTextField()
    AddressScreen(
        onEvent = {}
    )
    //AddressButton()
}
