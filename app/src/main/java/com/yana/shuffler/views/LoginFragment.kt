package com.yana.shuffler.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.contracts.LoginContract
import com.yana.shuffler.databinding.FragmentLoginBinding
import com.yana.shuffler.models.LoginModel
import com.yana.shuffler.presenters.LoginPresenter

class LoginFragment : Fragment(), LoginContract.View {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginPresenter: LoginPresenter
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

        loginPresenter = LoginPresenter(this, LoginModel())

        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            loginPresenter.onClickLogin(email, password)
        }

        binding.registerButton.setOnClickListener{
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerAuth, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun displayOnError(result: String) {
        Toast.makeText(
            requireContext(),
            result,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun displayOnSuccess() {
        val intent = Intent(this@LoginFragment.context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}