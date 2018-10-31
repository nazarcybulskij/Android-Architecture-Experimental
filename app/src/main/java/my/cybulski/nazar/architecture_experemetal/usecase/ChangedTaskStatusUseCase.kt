package my.cybulski.nazar.architecture_experemetal.usecase

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import my.cybulski.nazar.architecture_experemetal.model.Task

class ChangedTaskStatusUseCase {

     fun onChangedStatus(task: Task, status:Boolean):Task{
        return task.copy(complete = status)
    }


    suspend fun onChangedStatus2(task: Task, status:Boolean):Task{
        delay(100)
        return task.copy(complete = status)
    }



}