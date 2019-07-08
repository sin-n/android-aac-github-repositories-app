package s.sin.githubrepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import s.sin.githubrepositories.R
import s.sin.githubrepositories.databinding.InputKeywordBinding
import android.content.Context
import android.view.inputmethod.InputMethodManager


class InputKeywordFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var _viewModel: InputKeywordViewModel
    private lateinit var _binding: InputKeywordBinding

    private lateinit var _rlViewModel: ReposListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewModel = getViewModel()

        _rlViewModel = getSharedViewModel(ReposListViewModel::class)

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.input_keyword,
            container,
            false
        )
        _binding.viewModel = _viewModel
        _binding.lifecycleOwner = this

        _binding.searchView.setOnQueryTextListener(this)
        _binding.searchView.setQuery(_rlViewModel.inputText.value, false)

        // 入力用にフォーカスを当ててキーボード起動
        _binding.searchView.requestFocus()
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)

        return _binding.root
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d("IKF", "onQueryTextSubmit $query")

        _rlViewModel.inputText.postValue(query)

        (activity as MainActivity).back()
        return false
    }
}