package my.cybulski.nazar.architecture_experemetal.fragments

import android.arch.lifecycle.ViewModel
import my.cybulski.nazar.architecture_experemetal.model.Store
import my.cybulski.nazar.architecture_experemetal.model.Task
import my.cybulski.nazar.architecture_experemetal.usecase.ChangedTaskStatusUseCase
import my.cybulski.nazar.architecture_experemetal.usecase.ViewState
import my.cybulski.nazar.architecture_experemetal.usecase.ViewStateMachine


enum class Status{
    LOADING,SUCCESS,IDLE,ERROR
}

data class MainState(val status:Status = Status.IDLE, val task:Task? = null) :ViewState()

class SecondViewModel(val store: Store,val changedStatus:ChangedTaskStatusUseCase): ViewModel() {

    val machine = SecondMachine()

    fun onChangedStatus(task: Task, status:Boolean){
        machine.task{
             changedStatus.onChangedStatus2(task,status)
        }.started {
            copy(Status.LOADING,task)
        }.failure {
            copy(Status.ERROR)
        }.success {
            store.changedTask(it)
            copy(Status.SUCCESS,it)
        }.start()

    }

    class SecondMachine(initialState: MainState = MainState()):ViewStateMachine<MainState>(initialState)




}