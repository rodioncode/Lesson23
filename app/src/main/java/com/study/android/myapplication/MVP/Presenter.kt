package com.study.android.myapplication.MVP

import com.study.android.myapplication.R
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
// UserView.kt
interface UserView {
    fun displayUser(user: User)
    fun showError(message: String)
}

// UserPresenter.kt
class UserPresenter(private val view: UserView, private val model: UserModel) {

    fun loadUser(userId: Int) {
        val user = model.getUserById(userId)
        if (user != null) {
            view.displayUser(user)
        } else {
            view.showError("User not found")
        }
    }
}

// User.kt
data class User(val id: Int, val name: String, val email: String)

// UserModel.kt
class UserModel {

    private val users = listOf(
        User(1, "John Doe", "john.doe@example.com"),
        User(2, "Jane Smith", "jane.smith@example.com")
    )

    fun getUserById(userId: Int): User? {
        return users.firstOrNull { it.id == userId }
    }
}


class UserActivity : AppCompatActivity(), UserView {

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var loadUserButton: Button

    private lateinit var userPresenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userNameTextView = findViewById(R.id.userName)
        userEmailTextView = findViewById(R.id.userEmail)
        loadUserButton = findViewById(R.id.loadUserButton)

        userPresenter = UserPresenter(this, UserModel())

        loadUserButton.setOnClickListener {
            userPresenter.loadUser(1) // Загрузка пользователя с ID = 1
        }
    }

    override fun displayUser(user: User) {
        userNameTextView.text = user.name
        userEmailTextView.text = user.email
    }

    override fun showError(message: String) {
        userNameTextView.text = message
        userEmailTextView.text = ""
    }
}