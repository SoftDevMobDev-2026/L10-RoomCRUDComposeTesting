package com.example.week10
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class CityViewModel(private val repository: CityRepository) : ViewModel() {
    // Observe the cities from the repository
    val cities = repository.cities.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Add a new city to the repository
    fun addCity(name: String, state: String) = viewModelScope.launch {
        if (name.isNotBlank() && state.isNotBlank())
            repository.add(name.trim(), state.trim())
    }
   // Update an existing city in the repository
    fun updateCity(city: CityEntity) = viewModelScope.launch {
        if (city.name.isNotBlank() && city.state.isNotBlank())
            repository.update(city.copy(name = city.name.trim(), state = city.state.trim()))
    }
    // Delete a city from the repository
    fun deleteCity(city: CityEntity) = viewModelScope.launch {
        repository.delete(city)
    }
}
class CityViewModelFactory(private val repository: CityRepository) : ViewModelProvider.Factory {
    // Create a new instance of the CityViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(CityViewModel::class.java))
        return CityViewModel(repository) as T
    }
}
