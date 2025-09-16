package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.databinding.FragmentViewpagerHomeBinding

class ViewPagerHomeFragment : Fragment() {
    private var _binding: FragmentViewpagerHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = "홈 화면"
        binding.descriptionText.text = "ViewPager를 사용한 홈 Fragment입니다.\nMaterial3 디자인을 적용했습니다."

        binding.actionButton.setOnClickListener {
            binding.statusText.text = "홈 버튼이 클릭되었습니다!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}