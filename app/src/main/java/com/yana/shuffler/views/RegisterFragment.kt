package com.yana.shuffler.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.contracts.RegisterContract
import com.yana.shuffler.databinding.FragmentRegisterBinding
import com.yana.shuffler.models.RegisterModel
import com.yana.shuffler.presenters.RegisterPresenter

class RegisterFragment : Fragment(), RegisterContract.View {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var registerPresenter: RegisterContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = Firebase.auth
        registerPresenter = RegisterPresenter(this, RegisterModel())

        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            registerPresenter.verifyUserInfoInput(email, password, requireActivity())
        }

        binding.loginButton.setOnClickListener{
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerAuth, LoginFragment())
                .commit()
        }
    }

    override fun displayRegisterResult(result: String) {
        Toast.makeText(
            requireContext(),
            result,
            Toast.LENGTH_SHORT,
        ).show()
    }

    override fun displayRegisterSuccess() {
        val intent = Intent(this@RegisterFragment.context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}