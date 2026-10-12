package com.example.week10
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class CityDaoTest {
    private lateinit var db: CityDatabase
    private lateinit var dao: CityDao
    @Before fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CityDatabase::class.java).build()
        dao = db.cityDao()
    }
    @After fun tearDown() = db.close()
    @Test fun insertUpdateDelete_arePersistentInDatabase() = runBlocking {
        dao.insert(CityEntity(name = "Melbourne", state = "VIC"))
        val inserted = dao.observeAll().first().single()
        Assert.assertEquals("Melbourne", inserted.name)
        dao.update(inserted.copy(name = "Geelong"))
        Assert.assertEquals("Geelong", dao.observeAll().first().single().name)
        dao.delete(inserted.copy(name = "Geelong"))
        Assert.assertTrue(dao.observeAll().first().isEmpty())
    }
}
