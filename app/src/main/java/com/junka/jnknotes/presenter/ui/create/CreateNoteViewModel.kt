package com.junka.jnknotes.presenter.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junka.jnknotes.data.entities.Note
import com.junka.jnknotes.domain.Repository
import com.junka.jnknotes.presenter.ui.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNoteViewModel(
    private val repository: Repository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : ViewModel() {

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> get() = _note

    private val _saveNote = MutableLiveData<Event<Long>>()
    val saveNote: LiveData<Event<Long>> get() = _saveNote

    private val _noteColor = MutableLiveData<Int>()
    val noteColor: LiveData<Int> get() = _noteColor

    private val _noteImage = MutableLiveData<String>()
    val noteImage: LiveData<String> get() = _noteImage

    private val _notePassword = MutableLiveData<String>()
    val notePassword: LiveData<String> get() = _notePassword

    fun getNote(id: Long) {

        //si es 0, no busca
        if (id == 0L) return

        viewModelScope.launch {
            _note.value = withContext(ioDispatcher) { repository.getNote(id) }
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {

            var id = 0L
            withContext(ioDispatcher) {

                if (note.id == 0L) {
                    id = repository.insertNote(note)

                } else {
                    repository.updateNote(note)
                }
            }
            _saveNote.value = Event(id)

        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                repository.deleteNote(note)
            }

            _saveNote.value = Event(0)
        }
    }

    fun setNoteColor(color: Int) {
        _noteColor.value = color
    }

    fun setImagePath(path: String) {
        _noteImage.value = path
    }

    fun setPassword(password: String) {
        _notePassword.value = password
    }


}