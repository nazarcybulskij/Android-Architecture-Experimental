package my.cybulski.nazar.architecture_experemetal.fragments

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_task.view.*
import my.cybulski.nazar.architecture_experemetal.R
import my.cybulski.nazar.architecture_experemetal.model.Task
import kotlin.properties.Delegates

class TaskAdapter(list : List<Task>,val taskListener: TaskListener):RecyclerView.Adapter<TaskAdapter.ViewHolder>() {


    var tasks:List<Task> = list
    set(value) {
        field = value
        val dataNew = mutableListOf<ListItem>()
        for (task in value) {
            dataNew.add(ListItem.TaskItem(task))
        }
        this.data = dataNew
    }


    private var data : MutableList<ListItem> by Delegates.observable(mutableListOf<TaskAdapter.ListItem>()) { _, old, new ->
        calculateDiff(old, new).dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return ViewHolder(itemView);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, posution: Int) {
        viewHolder.bind(data.get(posution),taskListener)
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(item:ListItem,taskListener: TaskListener){
            when(item){
                is ListItem.TaskItem -> with(item){
                    itemView.nameTask.text  = task.name
                    itemView.nameTask.setOnClickListener(null)
                    itemView.nameTask.setOnClickListener {
                        taskListener.showDetail(item.task)
                    }
                    itemView.noteTask.text = task.note
                    itemView.noteTask.setOnClickListener(null)
                    itemView.noteTask.setOnClickListener {
                        taskListener.showDetail(item.task)
                    }
                    itemView.statusTask.setOnCheckedChangeListener(null)
                    itemView.statusTask.isChecked = task.complete
                    itemView.statusTask.setOnCheckedChangeListener { _, isChecked ->
                        taskListener.checkTask(task,isChecked)
                    }

                }
            }
        }
    }

    sealed class ListItem {
        class TaskItem(val task:Task):ListItem()
    }

    interface TaskListener{
        fun showDetail(task:Task)
        fun checkTask(task:Task,status:Boolean)
    }



    class DiffTaskCallback(val old: List<ListItem> , val new:List<ListItem>):DiffUtil.Callback(){

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val  oldItem =  old[oldItemPosition] as ListItem.TaskItem
            val  newItem =  new[newItemPosition] as ListItem.TaskItem
            return oldItem.task.id == newItem.task.id
        }

        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val  oldItem =  old[oldItemPosition] as ListItem.TaskItem
            val  newItem =  new[newItemPosition] as ListItem.TaskItem
            return oldItem.task.complete == newItem.task.complete
        }

    }

    fun calculateDiff(old: List<ListItem> , new:List<ListItem>):DiffUtil.DiffResult{
        return DiffUtil.calculateDiff(DiffTaskCallback(old, new));
    }

}