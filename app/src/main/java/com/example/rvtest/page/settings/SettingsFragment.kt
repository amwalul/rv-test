package com.example.rvtest.page.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rvtest.R
import com.example.rvtest.databinding.FragmentSettingsBinding
import com.example.rvtest.domain.model.Zone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel by viewModels<SettingsViewModel>()

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    private lateinit var locationAdapter: LocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() = binding.apply {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        locationAdapter = LocationAdapter(requireContext(), R.layout.item_dropdown_input)
        locationAdapter.submitList(Zone.getList())

        etLocation.setAdapter(locationAdapter)
        etLocation.setOnItemClickListener { _, _, position, _ ->
            locationAdapter.getItem(position)?.let { zone ->
                viewModel.setSelectedZone(zone)
            }
        }

        btnSave.setOnClickListener {
            viewModel.updateZoneId()
            findNavController().navigateUp()
        }
    }

    private fun initObservers() = with(viewModel) {
        currentZoneLiveData.observe(viewLifecycleOwner) { zone ->
            binding.etLocation.setText(zone.name, false)
            setSelectedZone(zone)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}