package br.com.example.googlebooks

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.example.googlebooks.repository.AppDatabase
import br.com.example.googlebooks.repository.Book
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val db = AppDatabase.getDatabase(appContext)
        val dao = db.getBookDao()

        runBlocking {
            val bookUnderTest = Book(
                "MEU_ID",
                "http://algumacoisa",
                "Meu livro",
                "Descrição",
                listOf("EU", "fulano"),
                "NovaTEC",
                "2019-03-01",
                1000,
                "http://small",
            "http://thumbnail"
            )
            val rowId = dao.save(bookUnderTest)
            assertTrue(rowId > -1)

            val books = dao.allFavorites().first()
            books.forEach {
                assertEquals(it.title, "Meu livro")
            }

            val isFavorite = dao.isFavorite("MEU_ID")
            assertTrue(isFavorite == 1)

            val result = dao.delete(bookUnderTest)
            assertTrue(result == 1)
        }
    }
}