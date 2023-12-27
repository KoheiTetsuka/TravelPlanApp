package com.android.exemple.planapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.dao.PropertyDao
import com.android.exemple.planapp.db.entities.Property
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyEditViewModel @Inject constructor(private val propertyDao: PropertyDao): ViewModel() {

    data class UiState(
        val title: String = "",
        val planId: Int? = null,
        val deleteFlag: String = "",
        val titleErrorMessage: String = "",
        val properties: List<Property>? = null
    )

    sealed class Event {
        data class Init(val id: Int): Event()
        data class TitleChanged(val title: String): Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.Init -> {
                    val property = propertyDao.getAllById(event.id).first()
                    _uiState.update {
                        it.copy(
                            title = property.title,
                            planId = property.planId,

                        )
                    }
                }

                is Event.TitleChanged -> {
                    _uiState.update {
                        it.copy(title = event.title)
                    }
                }
            }
        }
    }
}