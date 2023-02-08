package com.example.formulariocompose.ui.pantallas.pantallalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.formulariocompose.ui.theme.FormularioComposeTheme
import com.example.formulariocompose.domain.modelo.Persona
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
    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
        elevation = 8.dp
    ) {
        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
        ) {
            Text(text = "Nombre: ${persona.nombre}")
            Text(text = "Email: ${persona.email}")
        }
    }
}

@Composable
fun PantallaList(
    viewModel: ListViewModel = hiltViewModel()
) {
    viewModel.handleEvent(ListEvent.GetPersonas)
    val state = viewModel.uiState.collectAsState()
    LazyColumn(content = {
        state.value.lista?.let {
            items(it.size) { persona ->
                PersonaItem(persona = it[persona])
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FormularioComposeTheme {
        PantallaList()
    }
}