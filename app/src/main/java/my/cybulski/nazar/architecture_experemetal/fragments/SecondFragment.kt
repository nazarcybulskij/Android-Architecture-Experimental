package my.cybulski.nazar.architecture_experemetal.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.item_task.*
import my.cybulski.nazar.architecture_experemetal.R
import my.cybulski.nazar.architecture_experemetal.model.Task
import org.koin.android.viewmodel.ext.android.viewModel

const val TASK = "task"

class SecondFragment:Fragment() {

    private val secondViewModel:SecondViewModel  by viewModel()

    lateinit var task:Task;

    companion object {
        fun newInstance(task: Task) =
                SecondFragment().apply {
                    arguments = bundleOf(TASK to task)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            task = it[TASK] as Task
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTask.text = task.name
        noteTask.text = task.note
        statusTask.isChecked = task.complete
        statusTask.setOnCheckedChangeListener { _, isChecked -> secondViewModel.onChangedStatus(task,isChecked) }
    }
}