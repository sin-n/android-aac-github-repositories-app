package s.sin.githubrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import s.sin.githubrepositories.R

class StarTopFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.star_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (childFragmentManager.findFragmentById(R.id.star_content) == null) {
            childFragmentManager.beginTransaction().apply {
                val fragment = StarListFragment()
                replace(R.id.star_content, fragment)
            }.commit()
        }
    }

    fun showDetail(id: Int) {
        val fraMng = requireFragmentManager()
        fraMng
            .beginTransaction()
            .add(R.id.star_content, ReposDetailFragment.create(id))
            .addToBackStack(null)
            .commit()

    }

}