package s.sin.githubrepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import s.sin.githubrepositories.R
import s.sin.githubrepositories.data.GithubRepository
import s.sin.githubrepositories.databinding.ReposListBinding

class ReposListFragment : Fragment(), ReposListAdapter.ItemEventListener {

    private lateinit var _viewModel: ReposListViewModel
    private lateinit var _binding: ReposListBinding
    private lateinit var _adapter: ReposListAdapter

    private var _tempKeyword: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewModel = getSharedViewModel() // DI: Koin

        _adapter = ReposListAdapter(this, false)

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.repos_list,
            container,
            false
        )
        _binding.viewModel = _viewModel
        _binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        _binding.recyclerView.adapter = _adapter
        _binding.lifecycleOwner = this
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        _viewModel.inputText.observeChanged(this, Observer {
            log("input text $it")
            if (it.isEmpty()) {
                _viewModel.clearList()
            } else {
                // Get Github Repositories
                _viewModel.search(it)
            }
        })
        _viewModel.items.observe(this, Observer {
            log("items num = ${it.size}");
            _adapter.update(it)
        })
        _viewModel.eventInputKeyword.observe(this, Observer {
            log("eventInputKeyword ${it.getContent()}");

//            findNavController().navigate(R.id.action_reposListFragment_to_inputKeywordFragment)
            if (_tempKeyword != it.getContent()) {
                _tempKeyword = it.getContent()
                (activity as MainActivity).showInputKeyword()
            }
        })
    }

    private fun log(msg: String) {
        Log.d("RLF", msg)
    }

    override fun onClickItem(id: Int) {
        log("onClickItem $id")
        (activity as MainActivity).showReposDetail(id)
    }

    override fun onClickDeleteStar(id: Int) {
        // 何もしない
    }
}