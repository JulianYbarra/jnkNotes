package com.junka.jnknotes.presenter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.junka.jnknotes.R
import com.junka.jnknotes.databinding.LayoutDeleteNoteBinding

class DialogDeleteNote : DialogFragment() {

    private lateinit var binding: LayoutDeleteNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LayoutDeleteNoteBinding.inflate(inflater).apply {
            textDeleteNote.setOnClickListener {
                findNavController().popBackStack()
            }
            textCancelNote.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}
