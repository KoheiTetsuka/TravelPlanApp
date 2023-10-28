package com.android.exemple.planapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.dao.DetailDao
import com.android.exemple.planapp.db.entities.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class DetailEditViewModel @Inject constructor(private val detailDao: DetailDao) : ViewModel() {

    data class UiState(
        val title: String = "",
        val cost: String = "",
        val url: String = "",
        val memo: String = "",
        val startTime: LocalTime? = null,
        val endTime: LocalTime? = null,
        val date: LocalDate? = null,
        val planId: Int? = null,
        val titleErrorMessage: String = "",
        val timeErrorMessage: String = "",
        val details: List<Detail>? = null
    )

    sealed class Event {
        data class Init(val detailId: Int) : Event()
        data class TitleChanged(val title: String) : Event()
        data class CostChanged(val cost: String) : Event()
        data class UrlChanged(val url: String) : Event()
        data class MemoChanged(val memo: String) : Event()
        data class StartTimeChanged(val startTime: LocalTime) : Event()
        data class EndTimeChanged(val endTime: LocalTime) : Event()
        data class DateChanged(val date: LocalDate) : Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.Init -> {
                    val detail = detailDao.getById(event.detailId).first()
                    _uiState.update {
                        it.copy(
                            title = detail.title,
                            cost = detail.cost,
                            url = detail.url,
                            memo = detail.memo,
                            startTime = detail.startTime,
                            endTime = detail.endTime,
                            date = detail.date,
                            planId = detail.planId
                        )
                    }
                }

                is Event.TitleChanged -> {
                    _uiState.update {
                        it.copy(title = event.title)
                    }
                }

                is Event.CostChanged -> {
                    _uiState.update {
                        it.copy(cost = event.cost)
                    }
                }

                is Event.UrlChanged -> {
                    _uiState.update {
                        it.copy(url = event.url)
                    }
                }

                is Event.MemoChanged -> {
                    _uiState.update {
                        it.copy(memo = event.memo)
                    }
                }

                is Event.StartTimeChanged -> {
                    _uiState.update {
                        it.copy(startTime = event.startTime)
                    }
                }

                is Event.EndTimeChanged -> {
                    _uiState.update {
                        it.copy(endTime = event.endTime)
                    }
                }

                is Event.DateChanged -> {
                    _uiState.update {
                        it.copy(date = event.date)
                    }
                }
            }
        }
    }
    fun updateDetail(detailId: Int) {
        viewModelScope.launch {

            val newDetail = Detail(
                id = detailId,
                title = _uiState.value.title,
                cost = _uiState.value.cost,
                url = _uiState.value.url,
                memo = _uiState.value.memo,
                startTime = _uiState.value.startTime,
                endTime = _uiState.value.endTime,
                date = _uiState.value.date,
                planId = _uiState.value.planId!!
            )
            detailDao.updateDetail(newDetail)
        }
    }
}