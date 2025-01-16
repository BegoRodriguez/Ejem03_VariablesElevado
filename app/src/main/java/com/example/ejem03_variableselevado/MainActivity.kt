package com.example.ejem03_variableselevado

import androidx.compose.runtime.saveable.rememberSaveable

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InterfazPrincipal()
        }
    }
}

@Composable
fun InterfazPrincipal() {
    var contadorEdad by rememberSaveable { mutableStateOf(0) }
    var nombre by rememberSaveable { mutableStateOf("") }
    var sexo by rememberSaveable { mutableStateOf("Hombre") }
    var isSet by rememberSaveable { mutableStateOf(false) }
    var listaPersonas = remember { mutableStateListOf<Persona>() }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            EntradaDatos(
                contadorEdad = contadorEdad,
                nombre = nombre,
                sexo = sexo,
                onContadorEdadChange = { contadorEdad = it },
                onNombreChange = { nombre = it },
                onSexoChange = { sexo = it },
                onAddPersona = {
                    listaPersonas.add(Persona(nombre, contadorEdad, sexo))
                    isSet = true
                }
            )

            /* Column(
                 modifier = Modifier
                     .padding(20.dp)
                     .fillMaxWidth(),

             ) {
                 EjemploCheckbox()
                 EjemploSwitch()
                 EjemploSlider()
             }*/

            if (isSet) {
                ListaPersonas(listaPersonas = listaPersonas)
            }
        }
    }
}

@Composable
fun EntradaDatos(
    contadorEdad: Int,
    nombre: String,
    sexo: String,
    onContadorEdadChange: (Int) -> Unit,
    onNombreChange: (String) -> Unit,
    onSexoChange: (String) -> Unit,
    onAddPersona: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Nombre", modifier = Modifier.weight(0.25f))
            TextField(
                value = nombre, onValueChange = onNombreChange, modifier = Modifier.weight(0.75f)
            )
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sexo", modifier = Modifier.weight(0.25f))

            // RadioButton para "Hombre"
            RadioButton(
                selected = sexo == "Hombre",
                onClick = { onSexoChange("Hombre") }
            )
            Text(text = "Hombre", modifier = Modifier.weight(0.25f))

            // RadioButton para "Mujer"
            RadioButton(
                selected = sexo == "Mujer",
                onClick = { onSexoChange("Mujer") }
            )
            Text(text = "Mujer", modifier = Modifier.weight(0.25f))
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { onContadorEdadChange(contadorEdad + 1) }) {
                Text(text = "Pulsar para incrementar EDAD")
            }
            Text(text = contadorEdad.toString(), modifier = Modifier.padding(20.dp))
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onAddPersona) {
                Text(text = "Mostrar persona")
            }
        }
    }
}

@Composable
fun ListaPersonas(listaPersonas: List<Persona>) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LazyColumn {
            itemsIndexed(listaPersonas) { index, item ->
                Text(
                    text = "Hola ${item.nombre} ${item.edad} ${item.hombre}",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
@Parcelize data class Persona(val nombre: String, val edad: Int, val hombre: @RawValue String) : Parcelable, Serializable@Preview(showBackground = true)

@Composable fun EjemploCheckbox() {
    var checked by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox( checked = checked, onCheckedChange = { checked = it } )
        Text("Ejemplo Checkbox", modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable fun EjemploSwitch() {
    var isSwitched by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = isSwitched, onCheckedChange = { isSwitched = it })
        Text("Ejemplo Switch", modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable fun EjemploSlider() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            modifier = Modifier.padding(16.dp) )
        Text("Valor del slider: ${sliderPosition.toInt()}", modifier = Modifier.padding(16.dp)) }
}

