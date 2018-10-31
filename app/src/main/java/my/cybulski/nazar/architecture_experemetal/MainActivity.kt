package my.cybulski.nazar.architecture_experemetal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import my.cybulski.nazar.architecture_experemetal.fragments.FirstFragment
import my.cybulski.nazar.architecture_experemetal.fragments.SecondFragment
import my.cybulski.nazar.architecture_experemetal.model.Task
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Replace

class MainActivity : AppCompatActivity() {

    private val cicerone: Cicerone<Router>  by inject()

    private  val  navigator = object : SupportFragmentNavigator(supportFragmentManager,R.id.container){

        override fun exit() {
            finish()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return  when(screenKey){
                FIRST ->  FirstFragment.newInstance()
                SECOND -> SecondFragment.newInstance(data as Task)
                else -> Fragment()

            }
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            run {
                navigator.applyCommands(arrayOf(Replace(FIRST,null)));
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

}
