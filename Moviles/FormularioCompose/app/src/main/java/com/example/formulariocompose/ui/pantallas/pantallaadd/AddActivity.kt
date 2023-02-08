package com.example.formulariocompose.ui.pantallas.pantallaadd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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

}
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colors.background),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Arrangement.Center
//    ) {
//        val persona = Persona()
//        val (nombre, onNombreChange) = viewModel.nombre
//        val (email, onEmailChange) = viewModel.email
//        val (password, onPasswordChange) = viewModel.password
//        val (mensaje, onMensajeChange) = viewModel.mensaje
//
//        OutlinedTextField(
//            value = nombre,
//            onValueChange = { onNombreChange(it) },
//            label = { Text("Nombre") }
//        )
//        OutlinedTextField(
//            value = email,
//            onValueChange = { onEmailChange(it) },
//            label = { Text("Email") }
//        )
//        OutlinedTextField(
//            value = password,
//            onValueChange = { onPasswordChange(it) },
//            label = { Text("Password") }
//        )
//        Button(onClick = {
//            persona.nombre = nombre
//            persona.email = email
//            persona.password = password
//            viewModel.handleEvent(AddEvent.AddPersona(persona))
//        }) {
//            Text("Guardar")
//        }
//        Text(text = mensaje)
//    }