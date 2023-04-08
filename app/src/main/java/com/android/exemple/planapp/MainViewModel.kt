package com.android.exemple.planapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val planDao: PlanDao) : ViewModel() {
//    var title by mutableStateOf("")
//    var description by mutableStateOf("")
    var isShowDialog by mutableStateOf(false)

    val plans = planDao.getAll().distinctUntilChanged()

    data class UiState(
        val title: String = "",
        val description: String = "",
        val startDate: Date = Date(),
        val endDate: Date = Date(),
    )

    sealed class Event {
        data class TitleChanged(val title: String) : Event()
        data class DescriptionChanged(val description: String) : Event()
        data class StartDateChanged(val startDate: Date) : Event()
        data class EndDateChanged(val endDate: Date) : Event()
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



    /* ダイアログでの更新 */
//    private var editingPlan: Plan? = null
//    val isEditing: Boolean
//        get() = editingPlan != null

//    fun setEditingPlan(plan: Plan) {
//        editingPlan = plan
//        title = plan.title
//        description = plan.description
////        startDate = plan.startDate
////        endDate = plan.endDate
//    }

    fun createPlan() {
        viewModelScope.launch {
            val newPlan = Plan(
                title = _uiState.value.title,
                description = _uiState.value.description,/* startDate = startDate, endDate = endDate */
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

//    fun updateTask() {
//        editingPlan?.let { plan ->
//            viewModelScope.launch {
//                plan.title = title
//                plan.description = description
//                planDao.updatePlan(plan)
//            }
//        }
//    }

    fun resetProperties() {
//        editingPlan = null
//        _uiState.value.title = ""
//        description = ""
    }
}