package com.example.week10
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao interface CityDao{
    //Observe all cities in the database
    //This provides a continous stream of updated results while the Flow is being collected
    @Query("SELECT * FROM cities ORDER BY name")
    fun observeAll(): Flow<List<CityEntity>>

    @Insert suspend fun insert(city: CityEntity)
    @Update suspend fun update(city: CityEntity)
    @Delete suspend fun delete(city: CityEntity)
}
