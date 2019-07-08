package s.sin.githubrepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import s.sin.githubrepositories.R
import s.sin.githubrepositories.databinding.StarListBinding

class StarListFragment: Fragment(), ReposListAdapter.ItemEventListener {

    private lateinit var _viewModel: StarListViewModel
    private lateinit var _binding: StarListBinding
    private lateinit var _adapter: ReposListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewModel = getSharedViewModel()

        _adapter = ReposListAdapter(this, true)

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.star_list,
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
        _viewModel.items.observe(this, Observer {
            log("observe: star items num = ${it.size}")
            _adapter.update(it)
        })
    }

    private fun log(msg: String) {
        Log.d("SLF", msg)
    }

    override fun onClickItem(id: Int) {
        // Starのリスト押下では特に何もしない
    }

    override fun onClickDeleteStar(id: Int) {
        _viewModel.deleteStar(id)
    }
}