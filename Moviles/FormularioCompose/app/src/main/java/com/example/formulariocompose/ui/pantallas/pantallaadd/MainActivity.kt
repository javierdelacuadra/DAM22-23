package com.example.formulariocompose.ui.pantallas.pantallaadd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.formulariocompose.ui.theme.FormularioComposeTheme
import com.example.recyclerview.domain.modelo.Persona
import com.example.recyclerview.ui.addactivity.AddEvent
import com.example.recyclerview.ui.addactivity.AddViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PantallaAdd()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FormularioComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun PantallaAdd(
viewModel: AddViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        //add 3 textfields
        //add 1 button
        TextField(
            //add an id to the textfield to identify it
            value = "",
            onValueChange = { },
            label = { Text(text = "Nombre") },
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        )
        TextField(
            value = "",
            onValueChange = { },
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        )
        TextField(
            value = "",
            onValueChange = { },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        )
        Button(onClick = {
            //create a persona with the values of the textfields
            //send the persona to the viewmodel
            val persona = Persona(nombre = "", password = "", email = "")
            viewModel.handleEvent(AddEvent.AddPersona(persona))
        }) {
            Text(text = "Enviar")
        }
    }
}