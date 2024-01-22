package com.android.exemple.planapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.exemple.planapp.db.entities.Detail
import com.android.exemple.planapp.ui.repository.DetailRepository
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
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    val details = detailRepository.getAll()

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
        val popBackStackFlag: Boolean = false,
        val details: List<Detail>? = null
    )

    sealed class Event {
        data class Init(val planId: Int) : Event()
        data class CreateInit(val planId: Int) : Event()
        data class EditInit(val detailId: Int) : Event()
        data class TitleChanged(val title: String) : Event()
        data class CostChanged(val cost: String) : Event()
        data class UrlChanged(val url: String) : Event()
        data class MemoChanged(val memo: String) : Event()
        data class StartTimeChanged(val startTime: LocalTime) : Event()
        data class EndTimeChanged(val endTime: LocalTime) : Event()
        data class DateChanged(val date: LocalDate) : Event()
        data class OnCreateDetailClicked(val uiState: UiState) : Event()
        data class OnUpdateDetailClicked(val uiState: UiState, val detailId: Int) : Event()
        data class OnDeleteDetailClicked(val detail: Detail) : Event()
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun event(event: Event) {
        viewModelScope.launch {
            when (event) {
                is Event.Init -> {
                    val details = detailRepository.getAllById(event.planId).first()
                    _uiState.update {
                        it.copy(
                            details = details,
                            planId = event.planId
                        )
                    }
                }

                is Event.CreateInit -> {
                    _uiState.update {
                        it.copy(planId = event.planId)
                    }
                }

                is Event.EditInit -> {
                    val detail = detailRepository.getById(event.detailId).first()
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
                        it.copy(title = event.title, titleErrorMessage = "")
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
                        it.copy(startTime = event.startTime, timeErrorMessage = "")
                    }
                }

                is Event.EndTimeChanged -> {
                    _uiState.update {
                        it.copy(endTime = event.endTime, timeErrorMessage = "")
                    }
                }

                is Event.DateChanged -> {
                    _uiState.update {
                        it.copy(date = event.date)
                    }
                }

                is Event.OnCreateDetailClicked -> {
                    viewModelScope.launch {
                        if (_uiState.value.title.isEmpty()) {
                            _uiState.update {
                                it.copy(titleErrorMessage = "タイトルは必須です。")
                            }
                            return@launch
                        }

                        if (!checkTimeValidate()) {
                            _uiState.update {
                                it.copy(timeErrorMessage = "終了時間は開始時間より後の時刻を入力してください。")
                            }
                            return@launch
                        }

                        val newDetail = Detail(
                            title = _uiState.value.title,
                            cost = _uiState.value.cost,
                            url = _uiState.value.url,
                            memo = _uiState.value.memo,
                            startTime = _uiState.value.startTime,
                            endTime = _uiState.value.endTime,
                            date = _uiState.value.date,
                            planId = _uiState.value.planId!!
                        )

                        detailRepository.insertDetail(newDetail)
                    }
                }

                is Event.OnUpdateDetailClicked -> {
                    viewModelScope.launch {
                        if (_uiState.value.title.isEmpty()) {
                            _uiState.update {
                                it.copy(titleErrorMessage = "タイトルは必須です。")
                            }
                            return@launch
                        }

                        if (!checkTimeValidate()) {
                            _uiState.update {
                                it.copy(timeErrorMessage = "終了時間は開始時間より後の時刻を入力してください。")
                            }
                            return@launch
                        }

                        val newDetail = Detail(
                            id = event.detailId,
                            title = _uiState.value.title,
                            cost = _uiState.value.cost,
                            url = _uiState.value.url,
                            memo = _uiState.value.memo,
                            startTime = _uiState.value.startTime,
                            endTime = _uiState.value.endTime,
                            date = _uiState.value.date,
                            planId = _uiState.value.planId!!
                        )
                        detailRepository.updateDetail(newDetail)
                    }
                }

                is Event.OnDeleteDetailClicked -> {
                    viewModelScope.launch {
                        detailRepository.deleteDetail(event.detail)
                    }
                }
            }
        }
    }

//    fun createDetail() {
//
//    }
//
//    fun updateDetail(detailId: Int) {
//
//    }
//
//    fun deleteDetail(detail: Detail) {
//        viewModelScope.launch {
//            detailRepository.deleteDetail(detail)
//        }
//    }

    /**
     * 開始時間と終了時間を比較する。
     * 終了時間より開始時間が遅かったらエラー
     */
    private fun checkTimeValidate(): Boolean {
        val startTime = _uiState.value.startTime
        val endTime = _uiState.value.endTime

        // 開始時間と終了時間が同時刻でないかつ開始時間が終了時間より遅かった場合エラー
        return !(startTime?.equals(endTime) == false && !startTime.isBefore(endTime))
    }
    
    /**
     * popBackStackFlagを初期化する
     */
    fun initializePopBackStackFlag() {
        _uiState.update {
            it.copy(popBackStackFlag = false)
        }
    }
}