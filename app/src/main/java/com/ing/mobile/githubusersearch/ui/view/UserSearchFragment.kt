package com.ing.mobile.githubusersearch.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.ing.mobile.githubusersearch.R
import com.ing.mobile.githubusersearch.databinding.FragmentSearchUserBinding
import com.ing.mobile.githubusersearch.extension.isFavoriteRepo
import com.ing.mobile.githubusersearch.model.search.GitHubUser
import com.ing.mobile.githubusersearch.model.search.LocalUserData
import com.ing.mobile.githubusersearch.model.search.SearchConstant.Bundle.GITHUB_SER
import com.ing.mobile.githubusersearch.ui.adapter.UserAdapter
import com.ing.mobile.githubusersearch.ui.adapter.UserSearchHandler
import com.ing.mobile.githubusersearch.ui.viewmodel.PostsViewModel
import com.ing.mobile.githubusersearch.utils.DataBaseHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

class UserSearchFragment : Fragment(),UserSearchHandler {

    private lateinit var binding: FragmentSearchUserBinding
    private val viewModels: PostsViewModel by viewModel()
    private var userListAdapter: UserAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_user, container, false)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialView()
        setObservers()
        getLocalFavoriteList()
    }
    private fun initialView() {
        binding.viewModel = viewModels
        binding.lifecycleOwner = this
        userListAdapter = activity?.let { UserAdapter(this, it) }
        binding.resultRecycler.adapter = userListAdapter
        favoriteClickListener()
    }
    private fun clickListener(bundle: Bundle) {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_userSearchFragment_to_userDetailFragment, bundle)
    }

    private fun favoriteClickListener() {
        userListAdapter?.favoriteUserItem = { user ->
            user.isFavorite = user.isFavorite.not()
            activity?.let {activity->
                val hasFavorite = user.id?.let { it1 -> isFavoriteRepo(activity, it1) } ?: false
                    if (hasFavorite) {
                        DataBaseHelper.shared.deleteFavoriteRepo(activity,user.id!!)
                    } else {
                        DataBaseHelper.shared.insertFavoriteRepo(
                            activity, viewModels.createLocalUserData(user)
                        )
                    }
            }
        }
        userListAdapter?.notifyDataSetChanged()
    }

    @ExperimentalCoroutinesApi
    private fun setObservers() {
        viewModels.searchClickListener.observe(viewLifecycleOwner, Observer {
            binding.linearResultNotFound.visibility = GONE
            if (!viewModels.keyword.value.isNullOrEmpty()) {
                viewModels.getPosts(viewModels.keyword.value!!)
            } else {
                showValidateMessage(getString(R.string.search_keyword_validation_message))
            }
        })
        viewModels.postsData.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                userListAdapter?.setUserList(it)
                userListAdapter?.notifyDataSetChanged()
            } else {
                binding.linearResultNotFound.visibility = VISIBLE
            }
        })
        viewModels.showProgressbar.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) VISIBLE else GONE
        })

    }
    private fun setBundleObject(gitHubUser: GitHubUser):Bundle {
        val bundle = Bundle()
        bundle.putParcelable(GITHUB_SER, gitHubUser)
        return bundle
    }

    private fun showValidateMessage(message: String) {
        Snackbar.make(binding.constRootView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun selectedItem(gitHubUser: GitHubUser) {
        gitHubUser.isFavorite = gitHubUser.id?.let { it1 -> activity?.let { isFavoriteRepo(it, it1) } } ?: false
        clickListener(setBundleObject(gitHubUser))
    }

    private fun getLocalFavoriteList(): List<LocalUserData>? {
       return  activity?.let { DataBaseHelper.shared.getFavoriteUserList(it) }
    }

}