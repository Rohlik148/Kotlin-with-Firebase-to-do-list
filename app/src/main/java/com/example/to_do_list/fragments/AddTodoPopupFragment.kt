package com.example.to_do_list.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.to_do_list.databinding.FragmentAddTodoPopupBinding
import com.example.to_do_list.fragments.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {

    private var _binding: FragmentAddTodoPopupBinding? = null
    private val binding get() = _binding ?: error("Binding is not null")
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val Tag = "AddToDoPopUpFragment"

        @JvmStatic
        fun newInstance(taskID: String, task: String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskID", taskID)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskID").toString(),
                arguments?.getString("task").toString()
            )

            binding.todoEt.setText(toDoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if (toDoData == null) {
                    listener.onSaveTask(todoTask, binding.todoEt)
                } else {
                    toDoData?.task = todoTask
                    toDoData?.let {
                        listener.onUpdateTask(it, binding.todoEt)
                    }
                }
                dismiss()

            } else {
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}