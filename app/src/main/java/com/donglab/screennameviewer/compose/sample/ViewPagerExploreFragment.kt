package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.databinding.FragmentViewpagerExploreBinding

class ViewPagerExploreFragment : Fragment() {
    private var _binding: FragmentViewpagerExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = "탐색 화면"
        binding.descriptionText.text = "ViewPager를 사용한 탐색 Fragment입니다.\n스와이프로 화면을 전환할 수 있습니다."

        val items = listOf("아이템 1", "아이템 2", "아이템 3", "아이템 4", "아이템 5")
        binding.itemsText.text = items.joinToString("\n• ", "• ")

        binding.refreshButton.setOnClickListener {
            binding.statusText.text = "탐색 데이터를 새로고침했습니다!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}