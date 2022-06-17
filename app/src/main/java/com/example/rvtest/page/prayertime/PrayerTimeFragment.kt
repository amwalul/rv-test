package com.example.rvtest.page.prayertime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rvtest.R
import com.example.rvtest.databinding.FragmentPrayerTimeBinding
import com.example.rvtest.domain.Failure
import com.example.rvtest.domain.Loading
import com.example.rvtest.domain.Success
import com.example.rvtest.domain.model.PrayerTime
import com.example.rvtest.extension.onAnimationEnd
import com.example.rvtest.extension.visibleOrInvisible
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
            override fun onSwipeRight() {
                if (!isFirstItem()) showPreviousPrayerTime()
            }

            override fun onSwipeLeft() {
                if (!isLastItem()) showNextPrayerTime()
            }
        })

        ivPrevious.setOnClickListener {
            if (!isFirstItem()) showPreviousPrayerTime()
        }

        ivNext.setOnClickListener {
            if (!isLastItem()) showNextPrayerTime()
        }
    }

    private fun isFirstItem() = (viewModel.selectedIndexLiveData.value ?: 0) == 0

    private fun isLastItem() =
        (viewModel.selectedIndexLiveData.value ?: 0) == viewModel.prayerTimeList.lastIndex

    private fun showPreviousPrayerTime() {
        viewModel.decrementIndex()
        startPrayerTimeRightAnimation()
    }

    private fun showNextPrayerTime() {
        viewModel.incrementIndex()
        startPrayerTimeLeftAnimation()
    }

    private fun startPrayerTimeLeftAnimation() = binding.apply {
        val pushLeftOutAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.push_left_out)
        val pushLeftInAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.push_left_in)

        pushLeftOutAnimation.onAnimationEnd { llPrayerTime.startAnimation(pushLeftInAnimation) }
        llPrayerTime.startAnimation(pushLeftOutAnimation)
    }

    private fun startPrayerTimeRightAnimation() = binding.apply {
        val pushRightOutAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.push_right_out)
        val pushRightInAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.push_right_in)

        pushRightOutAnimation.onAnimationEnd { llPrayerTime.startAnimation(pushRightInAnimation) }
        llPrayerTime.startAnimation(pushRightOutAnimation)
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
                    if (resource.data == null) Toast.makeText(
                        requireContext(),
                        getString(R.string.get_data_error),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

        selectedIndexLiveData.observe(viewLifecycleOwner) { index ->
            val prayerTime =
                if (index >= 0) prayerTimeList[index]
                else prayerTimeList.first()

            setPrayerTime(prayerTime)
            handleArrowVisibility(index)
        }
    }

    private fun handleArrowVisibility(index: Int) = binding.apply {
        ivPrevious.visibleOrInvisible(index > 0)
        ivNext.visibleOrInvisible(index < viewModel.prayerTimeList.lastIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}