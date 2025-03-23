package com.example.to_do_list.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.to_do_list.R
import com.example.to_do_list.databinding.FragmentSignInBinding
import com.example.to_do_list.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private lateinit var navControl: NavController
    private val viewModel: SignInViewModel by viewModels()

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding ?: error("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.signInSuccess.collect { success ->
                        if (success == true) {
                            Toast.makeText(context, "Signed In", Toast.LENGTH_SHORT).show()
                            navControl.navigate(R.id.action_signInFragment_to_homeFragment)
                            viewModel.resetState()
                        }
                    }
                }
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        if (isLoading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
                launch {
                    viewModel.error.collect { error ->
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            viewModel.resetState()
                        }
                    }
                }
            }
        }
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
    }

    private fun registerEvents() {

        binding.authTextView.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.SignInButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val pass1 = binding.passwrodText.text.toString()

            if (email.isNotEmpty() && pass1.isNotEmpty()) {
                if (isValidEmail(email)) {
                    viewModel.signIn(email, pass1)
                }
            } else {
                Toast.makeText(context, "Empty field is restricted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}