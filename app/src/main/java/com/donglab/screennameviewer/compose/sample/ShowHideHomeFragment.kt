package com.donglab.screennameviewer.compose.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donglab.screennameviewer.compose.databinding.FragmentShowhideHomeBinding

class ShowHideHomeFragment : Fragment() {
    private var _binding: FragmentShowhideHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowhideHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleText.text = "홈 화면"
        binding.descriptionText.text = "Show/Hide 방식으로 관리되는 홈 Fragment입니다.\n하단 탭으로 화면을 전환할 수 있습니다."

        binding.counterButton.setOnClickListener {
            val currentText = binding.counterText.text.toString()
            val currentNumber = currentText.replace("카운터: ", "").toIntOrNull() ?: 0
            binding.counterText.text = "카운터: ${currentNumber + 1}"
            updateLifecycleStatus("버튼 클릭 - 카운터 증가")
        }

        binding.resetButton.setOnClickListener {
            binding.counterText.text = "카운터: 0"
            updateLifecycleStatus("카운터 리셋")
        }
    }

    override fun onResume() {
        super.onResume()
        updateLifecycleStatus("onResume 호출됨")
    }

    override fun onPause() {
        super.onPause()
        updateLifecycleStatus("onPause 호출됨")
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