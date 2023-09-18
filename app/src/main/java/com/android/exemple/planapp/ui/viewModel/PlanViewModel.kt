package com.android.exemple.planapp.ui.viewModel

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
class PlanViewModel @Inject constructor(private val planDao: PlanDao) : ViewModel() {
    val plans = planDao.getAll().distinctUntilChanged()

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

            if (!checkDateValidate()) {
                _uiState.update {
                    it.copy(dateErrorMessage = "終了日は開始日より後の日付を入力してください。")
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
            Log.d(PlanViewModel::class.simpleName, "success create plan")
        }
    }

    fun deletePlan(plan: Plan) {
        viewModelScope.launch {
            planDao.deletePlan(plan)
        }
    }

    private var editingPlan: Plan? = null
    fun setEditingPlan(plan: Plan) {
        editingPlan = plan
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    title = editingPlan!!.title,
                    description = editingPlan!!.description,
                    startDate = editingPlan!!.startDate,
                    endDate = editingPlan!!.endDate,
                )
            }
        }
    }

    /**
     * 開始日と終了日を比較する。
     * 終了日より開始日が遅かったらエラー
     */
    private fun checkDateValidate(): Boolean {
        val startDate = _uiState.value.startDate
        val endDate = _uiState.value.endDate
        val result: Int? = startDate?.compareTo(endDate)
        if (result != null) {
            if (result <= 0) {
                return true
            }
        }

        return false
    }

    fun resetProperties() {
//        editingPlan = null
//        _uiState.value.title = ""
//        description = ""
    }
}