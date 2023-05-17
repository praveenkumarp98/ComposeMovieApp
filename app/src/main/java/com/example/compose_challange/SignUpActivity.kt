package com.example.compose_challange

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.compose_challange.ui.theme.Compose_challangeTheme
import com.google.gson.Gson

class SignUpActivity : ComponentActivity() {

    var phone : String by mutableStateOf("")
    var otp : String by mutableStateOf("")

    fun phoneChange(value : String){
        phone = value
    }

    fun otpChange(value : String){
        otp = value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Compose_challangeTheme {
                // A surface container using the 'background' color from the theme
               //detailScreenUI()
                signupUI()
            }
        }


    }



    @Composable
    fun signupUI(){


        Surface(color = MaterialTheme.colors.background) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create an Account",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { if (it.length <= 10 )phoneChange(it) },
                        label = { Text("Phone") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = otp,
                        onValueChange = { if (it.length <= 6 )otpChange(it) },
                        label = { Text("OTP") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )


                    Button(
                        onClick = { gotoMainActivty() },
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Sign Up")
                    }
                }
            }
        }

    private fun gotoMainActivty() {
        if (phone.length == 10 && otp.length == 6 ) {
            startActivity(Intent(this, MainActivity::class.java))
        }else {
            Toast.makeText(this,"Phone or Otp mismatch !", Toast.LENGTH_SHORT).show()
        }
    }
}