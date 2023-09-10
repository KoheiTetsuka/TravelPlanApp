package com.android.exemple.planapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.dao.PlanDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val planDao: PlanDao) : ViewModel() {

    data class UiState(
        val title: String = "",
        val description: String = "",
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val titleErrorMessage: String = "",
        val dateErrorMessage: String = "",
        val startDateErrorMessage: String = "",
        val endDateErrorMessage: String = "",
    )

    sealed class Event {
        data class Init(val planId: Int) : Event()

        data class TitleChanged(val title: String) : Event()
        data class DescriptionChanged(val description: String) : Event()
        data class StartDateChanged(val startDate: LocalDate) : Event()
        data class EndDateChanged(val endDate: LocalDate) : Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

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

                is Event.Init -> {
                    Log.d("planId", event.planId.toString())
                    val plan = planDao.getById(event.planId).first()
                    Log.d("plan", plan.toString())
                    _uiState.update {
                        it.copy(
                            title = plan.title,
                            description = plan.description,
                            startDate = plan.startDate,
                            endDate = plan.endDate
                        )
                    }
                }
            }
        }
    }
}