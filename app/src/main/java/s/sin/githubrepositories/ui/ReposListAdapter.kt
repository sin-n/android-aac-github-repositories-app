package s.sin.githubrepositories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import s.sin.githubrepositories.data.GithubRepository
import s.sin.githubrepositories.databinding.ReposItemBinding

class ReposListAdapter(private val listener: ItemEventListener, private val deletable: Boolean) : RecyclerView.Adapter<ReposListAdapter.ItemViewHolder>() {

    private var _list: List<GithubRepository> = emptyList() // インスタンスが差し替わるためemptyList利用（add等しない）


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ReposItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val viewModel = ReposItemViewModel(_list[position], deletable) // Activity/FragmentのViewModelとはタイプが違う
        holder.binding.viewModel = viewModel
        holder.binding.eventListener = listener
    }

    override fun getItemCount(): Int = _list.size

    fun update(list: List<GithubRepository>) {
        if (_list.isEmpty()) {
            _list = list
            notifyDataSetChanged()
        } else {
            // 差分
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return _list.size
                }

                override fun getNewListSize(): Int {
                    return list.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = list[newItemPosition]
                    val oldItem = _list[oldItemPosition]
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = list[newItemPosition]
                    val oldItem = _list[oldItemPosition]
                    return oldItem.fullName == newItem.fullName
                            && oldItem.owner.avatarUrl == newItem.owner.avatarUrl
                }
            })
            _list = list
            result.dispatchUpdatesTo(this)
        }
    }

    class ItemViewHolder(val binding: ReposItemBinding): RecyclerView.ViewHolder(binding.root)

    interface ItemEventListener {
        fun onClickItem(id: Int)

        fun onClickDeleteStar(id: Int)
    }
}