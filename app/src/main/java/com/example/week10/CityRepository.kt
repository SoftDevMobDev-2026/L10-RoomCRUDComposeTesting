package com.example.week10
import kotlinx.coroutines.flow.Flow

interface CityRepository{
    //describing the operations available to the ViewModel
    val cities: Flow<List<CityEntity>>

    suspend fun add(name: String, state: String)
    suspend fun update(city: CityEntity)
    suspend fun delete(city: CityEntity)
}

class OfflineCityRepository(// implementing the repository layer
    private val cityDao: CityDao) : CityRepository{

    override val cities = cityDao.observeAll()

    override suspend fun add(name: String, state: String) = cityDao.insert(CityEntity(name = name, state = state))

    override suspend fun update(city: CityEntity) = cityDao.update(city)

    override suspend fun delete(city: CityEntity) = cityDao.delete(city)
}
