package com.study.android.myapplication.MVC

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.study.android.myapplication.R


// User.kt
data class User(val id: Int, val name: String, val email: String)

class UserActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var loadUserButton: Button

    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userNameTextView = findViewById(R.id.userName)
        userEmailTextView = findViewById(R.id.userEmail)
        loadUserButton = findViewById(R.id.loadUserButton)

        userController = UserController(this)

        loadUserButton.setOnClickListener {
            userController.onLoadUser(1) // Загрузка пользователя с ID = 1
        }
    }

    fun displayUser(user: User) {
        userNameTextView.text = user.name
        userEmailTextView.text = user.email
    }
}

class UserController(private val view: UserActivity) {

    private val userModel = UserModel()

    fun onLoadUser(userId: Int) {
        val user = userModel.getUserById(userId)
        view.displayUser(user)
    }
}

class UserModel {

    private val users = listOf(
        User(1, "John Doe", "john.doe@example.com"),
        User(2, "Jane Smith", "jane.smith@example.com")
    )

    fun getUserById(userId: Int): User {
        return users.firstOrNull { it.id == userId } ?: throw Exception("User not found")
    }
}