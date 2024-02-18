package com.android.exemple.planapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.entities.Property
import com.android.exemple.planapp.ui.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    val properties = propertyRepository.getAll()

    data class UiState(
        val title: String = "",
        val planId: Int? = null,
        val deleteFlag: String = "",
        val titleErrorMessage: String = "",
        val properties: List<Property>? = null
    )

    sealed class Event {
        data class Init(val planId: Int) : Event()
        data class CreateInit(val planId: Int) : Event()
        data class EditInit(val id: Int) : Event()
        data class TitleChanged(val title: String) : Event()
        data class OnCreatePropertyClicked(val uiState: UiState) : Event()
        data class OnUpdatePropertyClicked(val uiState: UiState, val propertyId: Int) : Event()
        data class OnSoftDeletePropertyClicked(val property: Property) : Event()
        data class OnDeletePropertyClicked(val property: Property) : Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.Init -> {
                    val properties =
                        propertyRepository.getAllByPlanId(event.planId).first()
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

                is Event.EditInit -> {
                    val property = propertyRepository.getAllById(event.id).first()
                    _uiState.update {
                        it.copy(
                            title = property.title,
                            planId = property.planId,

                            )
                    }
                }

                is Event.TitleChanged -> {
                    _uiState.update {
                        it.copy(title = event.title, titleErrorMessage = "")
                    }
                }

                is Event.OnCreatePropertyClicked -> {
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
                        propertyRepository.insertProperty(newProperty)
                    }
                }

                is Event.OnUpdatePropertyClicked -> {
                    viewModelScope.launch {
                        if (_uiState.value.title.isEmpty()) {
                            _uiState.update {
                                it.copy(titleErrorMessage = "タイトルは必須です。")
                            }
                            return@launch
                        }

                        val newProperty = Property(
                            id = event.propertyId,
                            title = _uiState.value.title,
                            planId = _uiState.value.planId!!,
                            deleteFlag = _uiState.value.deleteFlag
                        )
                        propertyRepository.updateProperty(newProperty)
                    }
                }

                is Event.OnSoftDeletePropertyClicked -> {
                    viewModelScope.launch {
                        var newProperty: Property = if (event.property.deleteFlag == "1") {
                            Property(
                                id = event.property.id,
                                title = event.property.title,
                                planId = event.property.planId!!,
                                deleteFlag = "0",
                            )
                        } else {
                            Property(
                                id = event.property.id,
                                title = event.property.title,
                                planId = event.property.planId!!,
                                deleteFlag = "1",
                            )
                        }
                        propertyRepository.softDeleteProperty(newProperty)
                    }
                }

                is Event.OnDeletePropertyClicked -> {
                    viewModelScope.launch {
                        propertyRepository.deleteProperty(event.property)
                    }
                }
            }
        }
    }
}

