package com.study.android.myapplication.MVI

// UserActivity.kt
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
// UserViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.study.android.myapplication.R

class UserActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var loadUserButton: Button

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userNameTextView = findViewById(R.id.userName)
        userEmailTextView = findViewById(R.id.userEmail)
        loadUserButton = findViewById(R.id.loadUserButton)

        // Подписка на изменения состояния
        userViewModel.userState.observe(this, Observer { state ->
            when (state) {
                is UserState.Loading -> showLoading()
                is UserState.Loaded -> displayUser(state.user)
                is UserState.Error -> showError(state.message)
            }
        })

        loadUserButton.setOnClickListener {
            userViewModel.processIntent(UserIntent.LoadUser(1)) // Загрузка пользователя с ID = 1
        }
    }

    private fun showLoading() {
        userNameTextView.text = "Loading..."
        userEmailTextView.text = ""
    }

    private fun displayUser(user: User) {
        userNameTextView.text = user.name
        userEmailTextView.text = user.email
    }

    private fun showError(message: String) {
        userNameTextView.text = message
        userEmailTextView.text = ""
    }
}

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userState = MutableLiveData<UserState>()
    val userState: LiveData<UserState> get() = _userState

    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadUser -> loadUser(intent.userId)
        }
    }

    private fun loadUser(userId: Int) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                val user = userRepository.getUserById(userId)
                if (user != null) {
                    _userState.value = UserState.Loaded(user)
                } else {
                    _userState.value = UserState.Error("User not found")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error loading user")
            }
        }
    }
}

// UserIntent.kt
// Интенты представляют собой действия, которые могут инициировать изменения состояния.
sealed class UserIntent {
    data class LoadUser(val userId: Int) : UserIntent()
}

// User.kt
data class User(val id: Int, val name: String, val email: String)

// UserState.kt
sealed class UserState {
    object Loading : UserState()
    data class Loaded(val user: User) : UserState()
    data class Error(val message: String) : UserState()
}
