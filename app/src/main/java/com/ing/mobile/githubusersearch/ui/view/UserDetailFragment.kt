package com.ing.mobile.githubusersearch.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ing.mobile.githubusersearch.R
import com.ing.mobile.githubusersearch.base.BaseFragment
import com.ing.mobile.githubusersearch.databinding.FragmentUserDetailBinding
import com.ing.mobile.githubusersearch.model.search.GitHubUser
import com.ing.mobile.githubusersearch.model.search.SearchConstant.Bundle.GITHUB_SER
import com.ing.mobile.githubusersearch.ui.viewmodel.PostsViewModel
import com.ing.mobile.githubusersearch.ui.viewmodel.UserDetailViewModel
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel
class UserDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private var gitHubUser: GitHubUser? = null
    private val viewModels: UserDetailViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleObject()
        setToolbarProperty()
        loadAvatar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("", "")
    }

    private fun getBundleObject() {
        if (arguments != null) {
            gitHubUser = arguments?.get(GITHUB_SER) as GitHubUser?
            setUI()
        }
    }

    private fun loadAvatar() {
        Glide.with(this).load(gitHubUser?.owner?.avatarUrl).into(binding.imageAvatar)
    }
    private fun setUI() {
        binding.textUserName.text = gitHubUser?.owner?.userName
        binding.textOpenIssues.text = getString(R.string.repo_open_issue_count,gitHubUser?.openIssuesCount)
        binding.textStartsCount.text = getString(R.string.repo_start_count, gitHubUser?.starCount)
    }
    private fun setToolbarProperty() {
        binding.toolbar.textHeader.text = gitHubUser?.repoName
        binding.toolbar.textHeader.visibility = VISIBLE
        if (gitHubUser?.isFavorite == true) {
            binding.toolbar.imageFavorite.setImageResource(R.drawable.ic_favorite_selected)
        } else {
            binding.toolbar.imageFavorite.setImageResource(R.drawable.ic_favorite)
        }
        binding.toolbar.imageFavorite.visibility = VISIBLE
    }

}