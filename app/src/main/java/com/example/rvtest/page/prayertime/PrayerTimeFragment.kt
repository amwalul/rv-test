package com.example.rvtest.page.prayertime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.rvtest.R
import com.example.rvtest.databinding.FragmentPrayerTimeBinding
import com.example.rvtest.domain.Loading
import com.example.rvtest.domain.model.PrayerTime
import com.example.rvtest.domain.onFailure
import com.example.rvtest.extension.onAnimationEnd
import com.example.rvtest.extension.visibleOrGone
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
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.menuSettings) {
                findNavController().navigate(R.id.action_prayerTimeFragment_to_settingsFragment)
            }
            true
        }

        llContent.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() {
                val list = viewModel.prayerTimeList
                if (!isFirstItem() && list.isNotEmpty()) showPreviousPrayerTime()
            }

            override fun onSwipeLeft() {
                val list = viewModel.prayerTimeList
                if (!isLastItem() && list.isNotEmpty()) showNextPrayerTime()
            }
        })

        ivPrevious.setOnClickListener {
            val list = viewModel.prayerTimeList
            if (!isFirstItem() && list.isNotEmpty()) showPreviousPrayerTime()
        }

        ivNext.setOnClickListener {
            val list = viewModel.prayerTimeList
            if (!isLastItem() && list.isNotEmpty()) showNextPrayerTime()
        }
    }

    private fun isFirstItem(): Boolean {
        val selectedIndex = (viewModel.selectedIndexLiveData.value ?: 0)
        val list = viewModel.prayerTimeList

        return list[selectedIndex] == list.first()
    }

    private fun isLastItem(): Boolean {
        val selectedIndex = (viewModel.selectedIndexLiveData.value ?: 0)
        val list = viewModel.prayerTimeList

        return list[selectedIndex] == list.last()
    }

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

    private fun setPrayerTime(prayerTime: PrayerTime?) = binding.apply {
        val date = prayerTime?.date?.let { DateUtils.parseApiDate(it) }
        tvDate.text = date?.let { DateUtils.formatDate(it) } ?: "-"

        prayerTime?.run {
            tvSubuh.text = subuh.uppercase()
            tvSyuruk.text = syuruk.uppercase()
            tvZohor.text = zohor.uppercase()
            tvAsar.text = asar.uppercase()
            tvMaghrib.text = maghrib.uppercase()
            tvIsya.text = isyak.uppercase()
        } ?: kotlin.run {
            tvSubuh.text = "-"
            tvSyuruk.text = "-"
            tvZohor.text = "-"
            tvAsar.text = "-"
            tvMaghrib.text = "-"
            tvIsya.text = "-"
        }
    }

    private fun initObservers() = with(viewModel) {
        zoneLiveData.observe(viewLifecycleOwner) { zone ->
            setZoneName(zone.name)
        }

        prayerResourceLiveData.observe(viewLifecycleOwner) { resource ->
            binding.progressLoading.visibleOrGone(resource is Loading && resource.data == null)

            resource
                .onFailure { data, message ->
                    val isResumed =
                        viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED
                    if (data == null && isResumed) Toast.makeText(
                        requireContext(),
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        selectedIndexLiveData.observe(viewLifecycleOwner) { index ->
            val prayerTime = prayerTimeList.getOrNull(index)

            setPrayerTime(prayerTime)
            handleArrowVisibility(index)
        }
    }

    private fun handleArrowVisibility(index: Int) = binding.apply {
        val list = viewModel.prayerTimeList
        val listLastIndex = viewModel.prayerTimeList.lastIndex

        ivPrevious.visibleOrInvisible(index > 0 && list.isNotEmpty())
        ivNext.visibleOrInvisible(index < listLastIndex && list.isNotEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}