package com.aspencarsi.dwchar_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aspencarsi.dwchar_app.CharacterApplication

import com.aspencarsi.dwchar_app.domain.CharacterRepository
import com.aspencarsi.dwchar_app.domain.model.CharacterM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterViewModel (private val characterRepository: CharacterRepository):ViewModel(){

    private val _character = MutableStateFlow(CharacterM(
        id = 1,
        name = "",
        description = "",
        status = "",
        relationsWithTheDoctor = "",
        image = ""
    ))
    val character:StateFlow<CharacterM> = _character


    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error


    fun fetchOne(id:String) {
        _loading.value = true
        viewModelScope.launch (Dispatchers.IO){
            characterRepository.fetchOne(id)
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch {
                    _error.value = "Se ha producido un error ${it.message}"
                }
                .collect{
                    _character.value = it
                }
        }
    }



    companion object{
        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CharacterApplication)
                CharacterViewModel(application.characterRepository)
            }
        }
    }
}
