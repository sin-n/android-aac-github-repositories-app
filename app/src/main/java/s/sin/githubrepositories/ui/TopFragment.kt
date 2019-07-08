package s.sin.githubrepositories.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import s.sin.githubrepositories.R
import s.sin.githubrepositories.databinding.TopBinding

class TopFragment : Fragment() {

    lateinit var _viewModel: TopViewModel
    lateinit var _binding: TopBinding

    companion object {
        const val FRAGMENT_TAG_LIST = "list"
        const val FRAGMENT_TAG_STAR = "star"
    }

    private val _navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_top -> {
                changeFragment(FRAGMENT_TAG_LIST)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_star -> {
                changeFragment(FRAGMENT_TAG_STAR)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewModel = getViewModel() // DI

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.top,
            container,
            false
        )
        _binding.navView.setOnNavigationItemSelectedListener(_navListener)
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            changeFragment(FRAGMENT_TAG_LIST)
        }

        return _binding.root
    }

    private fun changeFragment(tag: String) {
        val fraMng = fragmentManager!!

        val primaryFragment = fraMng.primaryNavigationFragment
        var nextFragment = fraMng.findFragmentByTag(tag)

        val transaction = fraMng.beginTransaction()
        if (primaryFragment != null) {
            transaction.hide(primaryFragment)
        }
        if (nextFragment == null) {
            nextFragment = when (tag) {
                FRAGMENT_TAG_LIST -> ReposTopFragment()
                else -> StarTopFragment()
            }
            transaction.add(R.id.top_container, nextFragment, tag)
        } else {
            transaction.show(nextFragment)
        }
        transaction.setPrimaryNavigationFragment(nextFragment)
        transaction.commit()
    }

    fun showReposDetail(id: Int) {
        val fraMng = requireFragmentManager()
        val fragment = fraMng.findFragmentByTag(FRAGMENT_TAG_LIST) as ReposTopFragment
        fragment.showDetail(id)
    }

    fun showStarDetail(id: Int) {
        val fraMng = requireFragmentManager()
        val fragment = fraMng.findFragmentByTag(FRAGMENT_TAG_STAR) as StarTopFragment
        fragment.showDetail(id)
    }
}