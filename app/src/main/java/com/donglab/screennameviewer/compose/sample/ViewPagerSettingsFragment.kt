package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.databinding.FragmentViewpagerSettingsBinding

class ViewPagerSettingsFragment : Fragment() {
    private var _binding: FragmentViewpagerSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = "설정 화면"
        binding.descriptionText.text = "ViewPager를 사용한 설정 Fragment입니다.\nMaterial3 Switch와 Button 컴포넌트를 사용했습니다."

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.statusText.text = if (isChecked) {
                "알림이 활성화되었습니다."
            } else {
                "알림이 비활성화되었습니다."
            }
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.statusText.text = if (isChecked) {
                "다크 모드가 활성화되었습니다."
            } else {
                "라이트 모드가 활성화되었습니다."
            }
        }

        binding.saveButton.setOnClickListener {
            binding.statusText.text = "설정이 저장되었습니다!"
        }

        binding.resetButton.setOnClickListener {
            binding.notificationSwitch.isChecked = false
            binding.themeSwitch.isChecked = false
            binding.statusText.text = "설정이 초기화되었습니다."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}