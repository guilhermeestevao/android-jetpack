package dev.guilherme.dog.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {

    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dog_breed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dog_breed WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): DogBreed

    @Query("DELETE FROM dog_breed")
    suspend fun deleteAllDogs()

}