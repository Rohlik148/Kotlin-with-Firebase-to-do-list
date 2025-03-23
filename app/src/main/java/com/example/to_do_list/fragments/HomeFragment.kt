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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_list.R
import com.example.to_do_list.databinding.FragmentHomeBinding
import com.example.to_do_list.fragments.utils.ToDoAdapter
import com.example.to_do_list.fragments.utils.ToDoData
import com.example.to_do_list.viewmodels.HomeViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListener,
    ToDoAdapter.TodoAdapterClicksInterface {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private var popUpFragment: AddTodoPopupFragment? = null
    private lateinit var adapter: ToDoAdapter

    private val viewModel: HomeViewModel by viewModels()


    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding ?: error("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                    viewModel.todoList.collect { list ->
                        adapter.setList(list)
                    }
                }
                launch {
                    viewModel.error.collect { error ->
                        // Show error message
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun registerEvents() {
        binding.addBtnHome.setOnClickListener {
            popUpFragment?.let {
                if (it.isAdded) {
                    childFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
                    popUpFragment = null
                }
            }
            popUpFragment = AddTodoPopupFragment()
            popUpFragment?.setListener(this)
            popUpFragment?.show(childFragmentManager, AddTodoPopupFragment.Tag)
        }
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            navController.navigate(R.id.action_homeFragment_to_signInFragment)
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ToDoAdapter(mutableListOf())
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onSaveTask(todo: String, todoEt: TextInputEditText) {
        viewModel.saveTask(todo)
        todoEt.text = null
        popUpFragment?.dismiss()
    }

    override fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText) {
        viewModel.updateTask(toDoData)
        todoEt.text = null
        popUpFragment?.dismiss()
    }

    override fun onDeleteItemClicked(toDoData: ToDoData) {
        viewModel.deleteTask(toDoData.taskId)
    }

    override fun onEditItemClicked(toDoData: ToDoData) {
        popUpFragment?.let {
            if (it.isAdded) {
                childFragmentManager.beginTransaction().remove(it).commitAllowingStateLoss()
                popUpFragment = null
            }
        }

        popUpFragment = AddTodoPopupFragment.newInstance(toDoData.taskId, toDoData.task)
        popUpFragment?.setListener(this)
        popUpFragment?.show(childFragmentManager, AddTodoPopupFragment.Tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}