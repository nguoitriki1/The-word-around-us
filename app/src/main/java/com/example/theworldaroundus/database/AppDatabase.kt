package com.example.theworldaroundus.database

import android.app.Application
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.theworldaroundus.data.Country

private const val DATABASE_NAME = "the_world_around_us_database"

@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        fun create(application: Application): AppDatabase {
            return Room.databaseBuilder(
                context = application,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    suspend fun getAllCountry(): List<Country>?

    @Query("SELECT COUNT(*) FROM Country")
    suspend fun getCountCountry(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountries(countries: List<Country>)

    @Query("DELETE FROM Country")
    suspend fun deleteAllCountries()
}