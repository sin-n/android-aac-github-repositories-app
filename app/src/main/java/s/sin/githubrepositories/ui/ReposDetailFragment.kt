package s.sin.githubrepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import s.sin.githubrepositories.R
import s.sin.githubrepositories.databinding.ReposDetailBinding

class ReposDetailFragment : Fragment(), View.OnClickListener {

    private lateinit var _viewModel: ReposDetailViewModel
    private lateinit var _binding: ReposDetailBinding

    private lateinit var _starListViewModel: StarListViewModel

    companion object {
        const val GITHUB_REPOS_ID_KEY = "repos_id"

        fun create(id: Int): ReposDetailFragment {
            var fragment = ReposDetailFragment()
            val bundle = Bundle()
            bundle.putInt(GITHUB_REPOS_ID_KEY, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val reposId = arguments!!.get(GITHUB_REPOS_ID_KEY)
        _viewModel = getViewModel { parametersOf(reposId) }

        _starListViewModel = getSharedViewModel()

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.repos_detail,
            container,
            false)
        _binding.viewModel = _viewModel
        _binding.eventListener = this
        _binding.lifecycleOwner = this
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        _viewModel.star.observe(this, Observer {
            log("observe: star $it")
            if (it) {
                _viewModel.addStar()
            } else {
                _viewModel.removeStar()
            }

            _starListViewModel.updateList()
        })
        _starListViewModel.eventDelete.observe(this, Observer {
            _viewModel.update()
        })
    }

    override fun onClick(v: View?) {
        (activity as MainActivity).back()
    }

    private fun log(msg: String) {
        Log.d("RDF", msg)
    }
}