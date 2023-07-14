package com.android.exemple.planapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.dao.PropertyDao
import com.android.exemple.planapp.db.entities.Property
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(private val propertyDao: PropertyDao): ViewModel() {

    val properties = propertyDao.getAll().distinctUntilChanged()

    data class UiState(
        val title: String = "",
    )

    sealed class Event {
        data class TitleChanged(val title: String): Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.TitleChanged -> {
                    _uiState.update {
                        it.copy(title = event.title)
                    }
                }
            }
        }
    }
    fun createProperty() {
        viewModelScope.launch {
            val newProperty = Property(
                title = _uiState.value.title
            )
            propertyDao.insertProperty(newProperty)
        }
    }

    fun deleteProperty(property: Property) {
        viewModelScope.launch {
            propertyDao.deleteProperty(property)
        }
    }
}

