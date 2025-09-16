package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.databinding.FragmentShowhideProfileBinding

class ShowHideProfileFragment : Fragment() {
    private var _binding: FragmentShowhideProfileBinding? = null
    private val binding get() = _binding!!

    private var visitCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowhideProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = "프로필 화면"
        binding.descriptionText.text = "Show/Hide 방식으로 관리되는 프로필 Fragment입니다.\n방문 횟수를 추적합니다."

        binding.nameText.text = "사용자 이름: Android Developer"
        binding.emailText.text = "이메일: developer@android.com"

        binding.editProfileButton.setOnClickListener {
            binding.nameText.text = "사용자 이름: ${generateRandomName()}"
            updateLifecycleStatus("프로필 정보 업데이트됨")
        }

        binding.shareButton.setOnClickListener {
            updateLifecycleStatus("프로필 공유 기능 실행됨")
        }

        binding.settingsButton.setOnClickListener {
            updateLifecycleStatus("설정 화면으로 이동 요청")
        }
    }

    private fun generateRandomName(): String {
        val names = listOf(
            "Kotlin Developer",
            "Android Expert",
            "UI/UX Designer",
            "Mobile Engineer",
            "Flutter Developer",
            "React Native Dev"
        )
        return names.random()
    }

    override fun onResume() {
        super.onResume()
        visitCount++
        binding.visitCountText.text = "방문 횟수: $visitCount"
        updateLifecycleStatus("프로필 화면 onResume 호출됨 ($visitCount 번째 방문)")
    }

    override fun onPause() {
        super.onPause()
        updateLifecycleStatus("프로필 화면 onPause 호출됨")
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