package com.example.rvtest.page.prayertime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rvtest.databinding.FragmentPrayerTimeBinding
import com.example.rvtest.domain.Failure
import com.example.rvtest.domain.Loading
import com.example.rvtest.domain.Success
import com.example.rvtest.widget.OnSwipeTouchListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerTimeFragment : Fragment() {

    private val viewModel by viewModels<PrayerTimeViewModel>()

    private var _binding: FragmentPrayerTimeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayerTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() = binding.apply {
        llContent.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {

        })
    }

    private fun setZoneName(name: String) = binding.apply {
        tvZoneName.text = name
    }

    private fun initObservers() = with(viewModel) {
        zoneLiveData.observe(viewLifecycleOwner) { zone ->
            setZoneName(zone.name)
        }

        prayerResourceLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Success -> {
                    setPrayerTimeList(resource.data.prayerTimeList)
                }
                is Loading -> {}
                is Failure -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}