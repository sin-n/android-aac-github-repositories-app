package s.sin.githubrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import s.sin.githubrepositories.R
import s.sin.githubrepositories.databinding.ReposTopBinding

class ReposTopFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ReposTopBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.repos_top,
            container,
            false
        )
        binding.lifecycleOwner = this
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (childFragmentManager.findFragmentById(R.id.repos_content) == null) {
            childFragmentManager.beginTransaction().apply {
                val fragment = ReposListFragment()
                replace(R.id.repos_content, fragment)
            }.commit()
        }
    }

    fun showDetail(id: Int) {
        val fraMng = childFragmentManager
        fraMng
            .beginTransaction()
            .add(R.id.repos_content, ReposDetailFragment.create(id))
            .addToBackStack(null)
            .commit()

    }
}