package s.sin.githubrepositories.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import s.sin.githubrepositories.R

class MainActivity : AppCompatActivity() {

    private lateinit var _topFragment: TopFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            _topFragment = TopFragment()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, _topFragment, null)
                .commit()
        }
    }

    fun showInputKeyword() {
        val fragment = InputKeywordFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment, null)
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        supportFragmentManager.popBackStack()
    }

    fun showReposDetail(id: Int) {
        _topFragment.showReposDetail(id)
    }

    fun showStarDetail(id: Int) {
        _topFragment.showStarDetail(id)
    }
}
