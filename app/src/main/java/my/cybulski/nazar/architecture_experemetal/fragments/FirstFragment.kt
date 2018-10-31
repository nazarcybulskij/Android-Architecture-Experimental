package my.cybulski.nazar.architecture_experemetal.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.*
import my.cybulski.nazar.architecture_experemetal.R
import my.cybulski.nazar.architecture_experemetal.SECOND
import my.cybulski.nazar.architecture_experemetal.model.Task
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class FirstFragment:Fragment() {


    private val cicerone: Cicerone<Router>  by inject()
    private  val firstViewModel:FirstViewModel by viewModel()

    private lateinit var taskAdapter : TaskAdapter

    companion object {
        fun newInstance() =
                FirstFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        taskRecyclerView.layoutManager = layoutManager
        taskAdapter = TaskAdapter(listOf(),object : TaskAdapter.TaskListener{

            override fun checkTask(task: Task, status: Boolean) {
                firstViewModel.onChangedStatus(task,status)
            }

            override fun showDetail(task: Task) {
                cicerone.router.navigateTo(SECOND,task)
            }
        })
        taskRecyclerView.adapter = taskAdapter

        firstViewModel.store.tasks.observe(this, Observer {
            it?.let{
                taskAdapter.tasks = it
            }
        })
    }



}