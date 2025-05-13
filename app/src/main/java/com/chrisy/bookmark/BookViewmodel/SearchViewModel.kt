import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisy.bookmark.repository.BookRepository
import com.chrisy.bookmark.network.BookItem
import com.chrisy.bookmark.data.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: BookRepository) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<BookItem>>(emptyList())
    val searchResults: StateFlow<List<BookItem>> = _searchResults

    fun searchBooks(query: String, apiKey: String) {
        println("[SearchViewModel] Received apiKey: $apiKey")

        if (apiKey.isBlank()) {
            println("[SearchViewModel] Error: API key is missing or blank.")
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.searchBooks(query, apiKey)
                _searchResults.value = response.items
            } catch (e: Exception) {
                println("[SearchViewModel] API search failed: ${e.message}")
                _searchResults.value = emptyList()
            }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.addBook(book)
        }
    }
}
