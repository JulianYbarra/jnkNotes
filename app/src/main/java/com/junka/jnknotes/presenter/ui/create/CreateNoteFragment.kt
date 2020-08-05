package com.junka.jnknotes.presenter.ui.create

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.junka.jnknotes.R
import com.junka.jnknotes.data.entities.Note
import com.junka.jnknotes.databinding.FragmentCreateNoteBinding
import com.junka.jnknotes.presenter.ui.REQUEST_CODE_SELECT_IMAGE_STORAGE
import com.junka.jnknotes.presenter.ui.REQUEST_CODE_STORAGE_PERMISSION
import com.junka.jnknotes.presenter.ui.observer
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel
import java.util.*

class CreateNoteFragment : Fragment() {

    private val viewModel: CreateNoteViewModel by lifecycleScope.viewModel(this)

    private lateinit var binding: FragmentCreateNoteBinding

    private val args by navArgs<CreateNoteFragmentArgs>()

    var mNote: Note? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentCreateNoteBinding.inflate(layoutInflater).apply {

            textDate.text = Date().toString()

            //Miscellaneous
            includeMiscellaneous.apply {
                val behavior = BottomSheetBehavior.from(layoutMiscellaneous)

                textMiscellaneous.setOnClickListener {
                    behavior.state = if (behavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
                        BottomSheetBehavior.STATE_COLLAPSED
                    } else {
                        BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                colorDefault.setOnClickListener {
                    viewModel.setNoteColor(R.color.colorDefaultNoteColor)
                }
                colorYellow.setOnClickListener {
                    viewModel.setNoteColor(R.color.colorNoteYellow)
                }
                colorPink.setOnClickListener {
                    viewModel.setNoteColor(R.color.colorNotePink)
                }
                colorBlue.setOnClickListener {
                    viewModel.setNoteColor(R.color.colorNoteBlue)
                }
                colorBlack.setOnClickListener {
                    viewModel.setNoteColor(R.color.colorNoteBlack)
                }

                layoutAddImage.setOnClickListener {

                    if (behavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }

                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_CODE_STORAGE_PERMISSION
                        )
                    } else {
                        selectImage()
                    }

                }
            }
        }

        with(viewModel) {

            setNoteColor(R.color.colorDefaultNoteColor)

            getNote(args.id)

            observer(saveNote) {
                it.getContentIfNotHandled()?.let { id ->
                    findNavController().popBackStack()
                }
            }
            observer(noteColor) {
                binding.viewSubtitleIndicator.setBackgroundResource(it)
            }
            observer(noteImage) {
                binding.noteImage.apply {
                    Glide.with(this).load(it).into(this)
                    visibility = View.VISIBLE
                }
            }
            observer(note) {
                mNote = it

                setNoteColor(it.color)
                setImagePath(it.image)

                with(binding) {
                    inputNoteTitle.setText(it.title)
                    inputNoteSubtitle.setText(it.subtitle)
                    inputNote.setText(it.body)
                    textDate.text = it.date.toString()

                    if (it.image.isNotEmpty()) {
                        Glide.with(noteImage).load(it.image).into(noteImage)
                        noteImage.visibility = View.VISIBLE
                    } else {
                        noteImage.visibility = View.GONE
                    }

                }
            }
        }

        return binding.root
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE_STORAGE)
    }


    //TODO cambiar a Android 11
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_STORAGE_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage()
                } else {
                    Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }


    //TODO cambiar a Android 11
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE_STORAGE && resultCode == -1) {
            data?.let { itData ->
                itData.data?.let {
                    val path = getPathFromUri(it)

                    path?.let {
                        viewModel.setImagePath(path)
                    }
                }
            }
        }
    }

    //TODO Sacar a un archivo aparte
    private fun getPathFromUri(uri: Uri): String? {

        var filepath = uri.path
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)

        cursor?.let {
            it.moveToFirst()
            val index = it.getColumnIndex("_data")
            filepath = it.getString(index)
            it.close()
        }
        return filepath
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_create_note, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_note -> {
                onClickSave()
            }
        }
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }


    private fun onClickSave() = with(binding) {

        mNote?.let {
            it.title = inputNoteTitle.editableText.toString()
            it.subtitle = inputNoteSubtitle.editableText.toString()
            it.body = inputNote.editableText.toString()
            it.color = viewModel.noteColor.value ?: R.color.colorDefaultNoteColor
            it.image = viewModel.noteImage.value ?: ""

        } ?: run {

            mNote = Note(
                id = 0,
                title = inputNoteTitle.editableText.toString(),
                subtitle = inputNoteSubtitle.editableText.toString(),
                body = inputNote.editableText.toString(),
                color = viewModel.noteColor.value ?: R.color.colorDefaultNoteColor,
                image = viewModel.noteImage.value ?: ""
            )
        }

        mNote?.let {
            viewModel.saveNote(it)
        }
    }

}