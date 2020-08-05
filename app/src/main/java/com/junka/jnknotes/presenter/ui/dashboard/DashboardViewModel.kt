package com.junka.jnknotes.presenter.ui.dashboard

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


class DashboardViewModel(
    private val repository: Repository,
    private val ioDispacher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> get() = _searchText

    private val _notes : LiveData<List<Note>> = repository.getNotes()
    val notes: LiveData<List<Note>> get() = _notes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToNote = MutableLiveData<Event<Long>>()
    val navigateToNote: LiveData<Event<Long>> get() = _navigateToNote

    init {
        fetchNotes()
    }

    private fun fetchNotes() {

        viewModelScope.launch {

            _loading.value = true

            _loading.value = false
        }
    }

    fun onNoteItemClick(noteItem: Note) {
        _navigateToNote.value = Event(noteItem.id)
    }

}