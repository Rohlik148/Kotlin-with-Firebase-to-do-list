package com.example.to_do_list.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.to_do_list.R
import com.example.to_do_list.databinding.FragmentSplashBinding
import com.example.to_do_list.viewmodels.SplashViewModel
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var navController: NavController
    private val viewModel: SplashViewModel by viewModels()

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding ?: error("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigateToHome.collect { navigate ->
                        if (navigate == true) {
                            navController.navigate(R.id.action_splashFragment_to_homeFragment)
                            viewModel.resetNavigation()
                        }
                    }
                }
                launch {
                    viewModel.navigateToSignIn.collect { navigate ->
                        if (navigate == true) {
                            navController.navigate(R.id.action_splashFragment_to_signInFragment)
                            viewModel.resetNavigation()
                        }
                    }
                }
            }
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}