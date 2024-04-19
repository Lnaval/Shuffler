package com.yana.shuffler.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.MainActivity
import com.yana.shuffler.databinding.FragmentLoginBinding

private const val SP_STRING = "sharedPrefs"
private const val AUTH_KEY = "name"
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = Firebase.auth

        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Both fields are required", Toast.LENGTH_SHORT).show()
            } else {
                authenticateUser(email, password)
            }
        }
    }



    private fun authenticateUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser

                    val sharedPreferences =
                        requireContext().getSharedPreferences(SP_STRING, MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString(AUTH_KEY, email)
                    editor.apply()

                    val intent = Intent(this@LoginFragment.context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    when (task.exception) {
                        is FirebaseNetworkException -> {
                            Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(
                                requireContext(),
                                "Invalid Credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
    }
}