package com.example.formulariocompose.ui.pantallas.pantallaadd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.ui.theme.FormularioComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : ComponentActivity() {
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
    val nombre = viewModel.nombre.collectAsState()
    val password = viewModel.password.collectAsState()
    val email = viewModel.email.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        TextField(value = "", onValueChange = {

        })
        TextField(value = "", onValueChange = {

        })
        Button(onClick = {
            val persona = Persona(nombre = nombre.value, email = email.value, password = password.value)
            viewModel.handleEvent(AddEvent.AddPersona(persona))
        }) {
            Text(text = "Guardar")
        }
    }
}