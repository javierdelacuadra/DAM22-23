package com.example.formulariocompose.ui.pantallas.pantallalist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.formulariocompose.domain.modelo.Persona
import com.example.formulariocompose.ui.pantallas.pantallaadd.AddActivity
import com.example.formulariocompose.ui.theme.FormularioComposeTheme
import com.example.formulariocompose.ui.theme.Purple200
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormularioComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
//                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PantallaList()
                }
            }
        }
    }
}

@Composable
fun PersonaItem(persona: Persona) {
    val viewModel: ListViewModel = hiltViewModel()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp,
        backgroundColor = Purple200
    ) {
        //do a column and center the content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Nombre: ${persona.nombre}", Modifier.padding(4.dp))
            Text(text = "Email: ${persona.email}", Modifier.padding(4.dp))
            Button(
                onClick = {
                    viewModel.handleEvent(ListEvent.DeletePersona(persona))
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Eliminar")
            }
        }
    }
}

@Composable
fun PantallaList(
    viewModel: ListViewModel = hiltViewModel()
) {
    viewModel.handleEvent(ListEvent.GetPersonas)
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LazyColumn {
        items(state.lista) { persona ->
            PersonaItem(persona)
        }
        item {
            Button(onClick = {
                context.startActivity(Intent(context, AddActivity::class.java))
            }, modifier = Modifier.fillMaxSize()) {
                Text(text = "Añadir persona")
            }
        }
    }
    state.mensaje?.let {
        LaunchedEffect(key1 = true) {
            //show a toast
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            //reset the state
            viewModel.handleEvent(ListEvent.ResetMensaje)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FormularioComposeTheme {
        PantallaList()
    }
}