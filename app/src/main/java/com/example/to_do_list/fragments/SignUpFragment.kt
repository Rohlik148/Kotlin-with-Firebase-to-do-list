package com.example.to_do_list.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.to_do_list.R
import com.example.to_do_list.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun registerEvents() {

        binding.authTextView.setOnClickListener {
            navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.RegButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val pass1 = binding.passwrodText.text.toString()
            val pass2 = binding.passwrodText2.text.toString()

            if(email.isNotEmpty() && pass1.isNotEmpty() && pass2.isNotEmpty()) {
                if (pass1 == pass2 && isValidEmail(email)) {
                    binding.progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Registration Complete", Toast.LENGTH_SHORT).show()
                                navControl.navigate(R.id.action_signUpFragment_to_homeFragment)
                            } else {
                                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                            binding.progressBar.visibility = View.GONE
                        })
                }
                else{Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()}
            }
            else{Toast.makeText(context, "Empty field is restricted", Toast.LENGTH_SHORT).show()}

        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }
}