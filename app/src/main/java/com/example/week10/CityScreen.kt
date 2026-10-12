package com.example.week10
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun CityScreen(
    cities: List<CityEntity>,
    onAdd: (String, String) -> Unit,
    onUpdate: (CityEntity) -> Unit,
    onDelete: (CityEntity) -> Unit
) {
    // State variables for the city name and state
    var name by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var editing by remember { mutableStateOf<CityEntity?>(null) }
    val valid = name.isNotBlank() && state.isNotBlank()

    // UI elements for adding a new city
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("City Manager", style = MaterialTheme.typography.headlineMedium)
        // Text fields for the city name and state
        OutlinedTextField(name, { name = it },
            label = { Text("City name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(state, { state = it },
            label = { Text("State") },
            modifier = Modifier.fillMaxWidth()
        )

        // Button to add or update the city
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(enabled = valid, onClick = {
                val city = editing
                if (city != null) onUpdate(city.copy(name = name, state = state))
                else onAdd(name, state)
                name = ""; state = ""; editing = null
            }) { Text(if (editing == null)
                    "Add City"
                else
                    "Save Changes")
            }
            if (editing != null) TextButton(onClick = { editing = null; name = ""; state = "" }) { Text("Cancel Edit") }
        }
        // List of cities
        if (cities.isEmpty()) Text("No cities yet")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(cities, key = { it.id }) { city ->
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${city.name}, ${city.state}")
                        Row {
                            TextButton(onClick = { editing = city; name = city.name; state = city.state }) { Text("Edit ${city.name}") }
                            TextButton(onClick = { onDelete(city) }) { Text("Delete ${city.name}") }
                        }
                    }
                }
            }
        }
    }
}
