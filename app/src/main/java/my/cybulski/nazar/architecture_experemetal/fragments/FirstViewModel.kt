package my.cybulski.nazar.architecture_experemetal.fragments

import android.arch.lifecycle.ViewModel
import my.cybulski.nazar.architecture_experemetal.model.Store
import my.cybulski.nazar.architecture_experemetal.model.Task
import my.cybulski.nazar.architecture_experemetal.usecase.ChangedTaskStatusUseCase

class FirstViewModel(val store: Store,val changedStatus:ChangedTaskStatusUseCase):ViewModel(){

    fun onChangedStatus(task: Task, status:Boolean){
        val useCaseTask = changedStatus.onChangedStatus(task,status)
        store.changedTask(useCaseTask)
    }


}