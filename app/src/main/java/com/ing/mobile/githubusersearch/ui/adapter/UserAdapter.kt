package com.ing.mobile.githubusersearch.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ing.mobile.githubusersearch.R
import com.ing.mobile.githubusersearch.databinding.ItemSearchViewBinding
import com.ing.mobile.githubusersearch.extension.isFavoriteRepo
import com.ing.mobile.githubusersearch.model.search.GitHubUser

class UserAdapter (val handler: UserSearchHandler,private val context: Context): RecyclerView.Adapter<UserAdapter.UserListViewHolder>() {
    private var userList: List<GitHubUser> = listOf()
    var favoriteUserItem: ((GitHubUser) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserListViewHolder {
        val inf = LayoutInflater.from(parent.context)
        val binding: ItemSearchViewBinding = DataBindingUtil.inflate(inf, R.layout.item_search_view, parent, false)
        return UserListViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    fun setUserList(searchList: List<GitHubUser>) {
        userList = searchList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val userItem = userList[position]
        holder.bindMenuList(userItem, position)
    }

    inner class UserListViewHolder(private val binding: ItemSearchViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindMenuList(gitHubUser: GitHubUser, position: Int) {
            binding.userItem = gitHubUser
            binding.searchHandler = handler
            favoriteImageStatus(isFavorite(gitHubUser))
            binding.imageFavorite.setOnClickListener{
                favoriteUserItem?.invoke(gitHubUser)
                favoriteImageStatus(gitHubUser.isFavorite)
                notifyDataSetChanged()
            }
            binding.executePendingBindings()
        }
        private fun favoriteImageStatus(isSelected:Boolean) {
            if (isSelected) {
                binding.imageFavorite.setImageResource(R.drawable.ic_favorite_selected)
            } else {
                binding.imageFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }

        private fun isFavorite(gitHubUser: GitHubUser): Boolean {
            return gitHubUser.id?.let { it1 -> isFavoriteRepo(context, it1) } ?: false
        }
    }
}