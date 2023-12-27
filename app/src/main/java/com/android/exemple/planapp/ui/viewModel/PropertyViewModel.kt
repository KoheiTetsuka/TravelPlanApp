package com.android.exemple.planapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.dao.PropertyDao
import com.android.exemple.planapp.db.entities.Property
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(private val propertyDao: PropertyDao): ViewModel() {

    val properties = propertyDao.getAll().distinctUntilChanged()

    data class UiState(
        val title: String = "",
        val planId: Int? = null,
        val deleteFlag: String = "",
        val titleErrorMessage: String = "",
        val properties: List<Property>? = null
    )

    sealed class Event {
        data class Init(val planId: Int): Event()
        data class CreateInit(val planId: Int): Event()
        data class TitleChanged(val title: String): Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.Init -> {
                    val properties = propertyDao.getAllByPlanId(event.planId).first()
                    _uiState.update {
                        it.copy(
                            properties = properties,
                            planId = event.planId,
                        )
                    }
                }

                is Event.CreateInit -> {
                    _uiState.update {
                        it.copy(planId = event.planId)
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

    fun createProperty() {
        viewModelScope.launch {
            if (_uiState.value.title.isEmpty()) {
                _uiState.update {
                    it.copy(titleErrorMessage = "タイトルは必須です。")
                }
                return@launch
            }
            val newProperty = Property(
                title = _uiState.value.title,
                planId = _uiState.value.planId!!
            )
            propertyDao.insertProperty(newProperty)
        }
    }

    fun softDeleteProperty(property: Property) {
        viewModelScope.launch {
            var newProperty: Property = if (property.deleteFlag == "1") {
                Property(
                    id = property.id,
                    title = property.title,
                    planId = property.planId,
                    deleteFlag = "0",
                )
            } else {
                Property(
                    id = property.id,
                    title = property.title,
                    planId = property.planId,
                    deleteFlag = "1",
                )
            }
            propertyDao.softDeleteProperty(newProperty)
        }
    }

    fun deleteProperty(property: Property) {
        viewModelScope.launch {
            propertyDao.deleteProperty(property)
        }
    }
}

