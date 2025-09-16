package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.databinding.FragmentShowhideSearchBinding

class ShowHideSearchFragment : Fragment() {
    private var _binding: FragmentShowhideSearchBinding? = null
    private val binding get() = _binding!!

    private val sampleData = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowhideSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = "검색 화면"
        binding.descriptionText.text = "Show/Hide 방식으로 관리되는 검색 Fragment입니다.\n검색 기능을 시뮬레이션할 수 있습니다."

        initSampleData()
        updateResultsList()

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString().trim()
            performSearch(query)
        }

        binding.clearButton.setOnClickListener {
            binding.searchEditText.text?.clear()
            sampleData.clear()
            initSampleData()
            updateResultsList()
            updateLifecycleStatus("검색 결과 초기화")
        }
    }

    private fun initSampleData() {
        sampleData.addAll(
            listOf(
                "Android 개발",
                "Kotlin 프로그래밍",
                "Jetpack Compose",
                "Material Design",
                "Fragment 관리",
                "ViewPager2 활용"
            )
        )
    }

    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            updateLifecycleStatus("검색어를 입력해주세요")
            return
        }

        val filteredResults = sampleData.filter {
            it.contains(query, ignoreCase = true)
        }

        if (filteredResults.isNotEmpty()) {
            binding.resultsText.text = filteredResults.joinToString("\n• ", "• ")
            updateLifecycleStatus("'$query' 검색 완료 - ${filteredResults.size}개 결과")
        } else {
            binding.resultsText.text = "검색 결과가 없습니다"
            updateLifecycleStatus("'$query' 검색 결과 없음")
        }
    }

    private fun updateResultsList() {
        binding.resultsText.text = sampleData.joinToString("\n• ", "• ")
    }

    override fun onResume() {
        super.onResume()
        updateLifecycleStatus("검색 화면 onResume 호출됨")
    }

    override fun onPause() {
        super.onPause()
        updateLifecycleStatus("검색 화면 onPause 호출됨")
    }

    private fun updateLifecycleStatus(message: String) {
        if (_binding != null) {
            binding.lifecycleStatusText.text = "상태: $message"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}