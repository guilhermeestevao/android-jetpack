package dev.guilherme.dog.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<DogBreed>) : List<Long>

    @Insert
    suspend fun inset(dog: DogBreed): Long

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()

}