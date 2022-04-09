package com.test.wadiz.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.test.wadiz.adapter.ItemListAdapter
import com.test.wadiz.databinding.ActivityMainBinding
import com.test.wadiz.repo.RequestRepository
import com.test.wadiz.util.toVisibility
import com.test.wadiz.viewmodel.MainViewModel
import com.test.wadiz.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, MainViewModelFactory(RequestRepository()))[MainViewModel::class.java]
        setContentView(binding.root)
        initUI()
        subscribeUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun subscribeUI() {
        viewModel.list.observe(this) { list ->
            if (list.isNotEmpty()) {
                (binding.recyclerView.adapter as? ItemListAdapter)?.submitList(list)
            }
        }
        viewModel.isProgressBarGone.observe(this) { isGone ->
            binding.progressBar.visibility = (!isGone).toVisibility()
        }
    }

    private fun initUI() {
        val tabNames = arrayOf("전체", "펀딩", "스토어")
        binding.recyclerView.adapter = ItemListAdapter().apply {
            onItemClickListener = { _, i -> openWebView(currentList[i].landingUrl) }
            onSearchClickListener = { v, _ -> (v as? TextView)?.text.toString().also {
                viewModel.currentQuery = it

                viewModel.requestSearch()
            } }
            onKeywordClickListener = { _, i -> openWebView(currentList[i].makerPage) }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.currentQuery = query ?: ""

                viewModel.requestSearch()
                binding.tabLayout.removeAllTabs()
                for (s in tabNames) {
                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText(s))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    tabNames[0] -> viewModel.requestSearch()
                    tabNames[1] -> viewModel.requestSearch("funding")
                    tabNames[2] -> viewModel.requestSearch("store")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    /**
     * URL을 넘겨 웹브라우져 실행.
     */
    private fun openWebView(url: String) = startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
}