package com.example.week10
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.room.Room

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            CityDatabase::class.java,
            "city_database"
        ).build()

       val repository = OfflineCityRepository(database.cityDao())

        // Create the ViewModel
        setContent {
            MaterialTheme {
                val viewModel: CityViewModel = viewModel(
                    factory = CityViewModelFactory(repository)
                )

                val cities by viewModel.cities.collectAsStateWithLifecycle()

                CityScreen(
                    cities = cities,
                    onAdd = viewModel::addCity,
                    onUpdate = viewModel::updateCity,
                    onDelete = viewModel::deleteCity
                )
            }
        }
    }
}
