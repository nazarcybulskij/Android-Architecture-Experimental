package my.cybulski.nazar.architecture_experemetal.model

import android.arch.lifecycle.MutableLiveData
import android.os.Parcel
import android.os.Parcelable


data  class Task(val id:Int,val complete:Boolean = false,val name:String,val note:String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (complete) 1 else 0)
        parcel.writeString(name)
        parcel.writeString(note)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}


class Store{


    val tasks:MutableLiveData<List<Task>>


    init{
        tasks = MutableLiveData<List<Task>>()
        val  list= generateTask()
        tasks.value =list
    }

    fun changedTask(task:Task){
        val list = tasks.value?.map{ if (task.id == it.id) task else it }
        tasks.value = list
    }

    private fun generateTask():List<Task>{
        val list = mutableListOf<Task>()
        for (i in 1..20)
            list.add(Task(id = i,name = "Name ${i}",note = "note  = ${i}"))
        return list
    }


}

