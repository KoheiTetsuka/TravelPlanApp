package com.android.exemple.planapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.dao.PlanDao
import com.android.exemple.planapp.db.entities.Plan
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val planDao: PlanDao) : ViewModel() {
    val plans = planDao.getAll().distinctUntilChanged()

    data class UiState(
        val title: String = "",
        val description: String = "",
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val titleErrorMessage: String = "",
        val startDateErrorMessage: String = "",
        val endDateErrorMessage: String = "",
    )

    sealed class Event {
        data class TitleChanged(val title: String) : Event()
        data class DescriptionChanged(val description: String) : Event()
        data class StartDateChanged(val startDate: LocalDate) : Event()
        data class EndDateChanged(val endDate: LocalDate) : Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var editingPlan: Plan? = null
    val isEditing: Boolean
        get() = editingPlan != null

    fun setEditingPlan(plan: Plan) {
        editingPlan = plan
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    title = plan.title,
                    description = plan.description,
                    startDate = plan.startDate,
                    endDate = plan.endDate,
                )
            }
        }
    }

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.TitleChanged -> {
                    _uiState.update {
                        it.copy(title = event.title, titleErrorMessage = "")
                    }
                }

                is Event.DescriptionChanged -> {
                    _uiState.update {
                        it.copy(description = event.description)
                    }
                }

                is Event.StartDateChanged -> {
                    _uiState.update {
                        it.copy(startDate = event.startDate)
                    }
                }

                is Event.EndDateChanged -> {
                    _uiState.update {
                        it.copy(endDate = event.endDate)
                    }
                }
            }
        }
    }

    fun createPlan() {
        viewModelScope.launch {
            if (_uiState.value.title.isEmpty()) {
                _uiState.update {
                    it.copy(titleErrorMessage = "タイトルは必須です。")
                }
                return@launch
            }
            val newPlan = Plan(
                title = _uiState.value.title,
                description = _uiState.value.description,
                startDate = _uiState.value.startDate,
                endDate = _uiState.value.endDate
            )

            planDao.insertPlan(newPlan)
            Log.d(MainViewModel::class.simpleName, "success create plan")
        }
    }

    fun deletePlan(plan: Plan) {
        viewModelScope.launch {
            planDao.deletePlan(plan)
        }
    }

    fun updatePlan() {
        editingPlan?.let { plan ->
            viewModelScope.launch {
                val updatePlan = Plan(
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    startDate = _uiState.value.startDate,
                    endDate = _uiState.value.endDate
                )
                planDao.updatePlan(updatePlan)
            }
        }
    }

    fun resetProperties() {
//        editingPlan = null
//        _uiState.value.title = ""
//        description = ""
    }
}