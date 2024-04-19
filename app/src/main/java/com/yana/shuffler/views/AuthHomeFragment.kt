package com.yana.shuffler.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.yana.shuffler.AuthActivity
import com.yana.shuffler.MainActivity
import com.yana.shuffler.R
import com.yana.shuffler.databinding.FragmentAuthHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthHomeFragment : Fragment() {
    private var _binding: FragmentAuthHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            delay(1500L)
            (activity as AuthActivity).replaceFragment(LoginFragment())
        }
    }
}