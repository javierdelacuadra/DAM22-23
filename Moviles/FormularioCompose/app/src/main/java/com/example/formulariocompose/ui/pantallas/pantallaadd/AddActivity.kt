package com.example.formulariocompose.ui.pantallas.pantallaadd

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.ui.pantallas.pantallalist.ListActivity
import com.example.formulariocompose.ui.theme.FormularioComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioComposeTheme {
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
@Preview(showBackground = true)
fun PantallaAdd(
    viewModel: AddViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var nombre by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Text(text = "Formulario", style = MaterialTheme.typography.h4)
        OutlinedTextField(value = nombre, onValueChange = {
            nombre = it
        },modifier = Modifier.padding(bottom = 8.dp), label = { Text(text = "Nombre") })
        OutlinedTextField(value = password, onValueChange = {
            password = it
        },modifier = Modifier.padding(bottom = 8.dp), label = { Text(text = "Password") })
        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, modifier = Modifier.padding(bottom = 8.dp), label = { Text(text = "Email") })
        Button(
            onClick = {
                val persona = Persona(nombre, password, email)
                viewModel.handleEvent(AddEvent.AddPersona(persona))
                context.startActivity(Intent(context, ListActivity::class.java))
            },
            enabled = nombre.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()
        ) {
            Text(text = "Add Persona")
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, ListActivity::class.java))
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Volver atrás")
        }
    }
}