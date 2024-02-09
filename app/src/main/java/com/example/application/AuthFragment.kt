package com.example.application

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.application.data.model.ApiClient
import com.example.application.data.model.User
import com.example.application.databinding.FragmentAuthBinding
import kotlinx.coroutines.launch

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding by viewBinding(FragmentAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAuth.setOnClickListener {
            val inputEmail = binding.editTextEmail.text.toString()
            val inputPassword = binding.editTextPassword.text.toString()

            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(
                    email = inputEmail,
                    password = inputPassword,
                )
                auth(user)
            }
        }
    }

    private fun auth(user: User) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.userService.authUser(user)
                if (response.isSuccessful) {
                    val str = response.body()
                    if (str != null) {
                        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("APISERVICE", "Response body is null")
                    }
                } else {
                    Toast.makeText(requireContext(), response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("APISERVICE", "Error fetching data", e)
            }
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return AuthFragment()
        }
    }
}