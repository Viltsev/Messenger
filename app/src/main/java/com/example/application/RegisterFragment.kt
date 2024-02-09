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
import com.example.application.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding by viewBinding(FragmentRegisterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener {
            val inputUsername = binding.editTextUsername.text.toString()
            val inputEmail = binding.editTextEmail.text.toString()
            val inputPassword = binding.editTextPassword.text.toString()

            if (inputUsername.isEmpty() || inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(
                    username = inputUsername,
                    email = inputEmail,
                    password = inputPassword,
                )
                register(user)
            }
        }
    }

    private fun register(user: User) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.userService.registerUser(user)
                if (response.isSuccessful) {
                    val str = response.body()
                    if (str != null) {
                        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("APISERVICE", "Response body is null")
                    }
                } else {
                    Toast.makeText(requireContext(), response.errorBody()?.string(), Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("APISERVICE", "Error fetching data", e)
            }
        }
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}