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
import com.example.rvtest.domain.model.PrayerTime
import com.example.rvtest.utils.DateUtils
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

    private fun setTodayIndex(prayerTimeList: List<PrayerTime>) = binding.apply {
        val todayPrayerTimeIndex = prayerTimeList.indexOfFirst { prayerTime ->
            DateUtils.isApiDateToday(prayerTime.date)
        }

        viewModel.setSelectedIndex(todayPrayerTimeIndex)
    }

    private fun setPrayerTime(prayerTime: PrayerTime) = binding.apply {
        val date = DateUtils.parseApiDate(prayerTime.date)
        tvDate.text = date?.let { DateUtils.formatDate(it) }

        with(prayerTime) {
            tvSubuh.text = subuh.uppercase()
            tvSyuruk.text = syuruk.uppercase()
            tvZohor.text = zohor.uppercase()
            tvAsar.text = asar.uppercase()
            tvMaghrib.text = maghrib.uppercase()
            tvIsya.text = isyak.uppercase()
        }
    }

    private fun initObservers() = with(viewModel) {
        zoneLiveData.observe(viewLifecycleOwner) { zone ->
            setZoneName(zone.name)
        }

        prayerResourceLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Success -> resource.data?.run {
                    setPrayerTimeList(prayerTimeList)
                    setTodayIndex(prayerTimeList)
                }
                is Loading -> resource.data?.run {
                    setPrayerTimeList(prayerTimeList)
                    setTodayIndex(prayerTimeList)
                }
                is Failure ->
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
            }
        }

        selectedIndexLiveData.observe(viewLifecycleOwner) { index ->
            val prayerTime =
                if (index != -1) prayerTimeList[index]
                else prayerTimeList.first()

            setPrayerTime(prayerTime)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}