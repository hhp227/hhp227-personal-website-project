package com.test.wadiz.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.test.wadiz.databinding.ActivityMainBinding
import com.test.wadiz.repo.RequestRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

        /**
         * 검색 API 예제 코드.
         * 샘플의 모든 코드는 원하는데로 변경, 수정 가능합니다.
         */
        lifecycleScope.launch { RequestRepository().requestSearch(keyword = "고양이") }
    }

    private fun initUI() {
        //TODO init UI
    }

    /**
     * URL을 넘겨 웹브라우져 실행.
     */
    fun openWebView(url: String) = startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
}