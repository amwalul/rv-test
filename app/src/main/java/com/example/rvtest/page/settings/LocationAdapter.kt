package com.example.rvtest.page.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.rvtest.databinding.ItemDropdownInputBinding
import com.example.rvtest.domain.model.Zone

class LocationAdapter(
    context: Context,
    resource: Int
) : ArrayAdapter<Zone>(context, resource) {

    private val locationList = mutableListOf<Zone>()
    private val filteredList = mutableListOf<Zone>()

    fun submitList(data: List<Zone>) {
        locationList.apply {
            clear()
            addAll(data)
        }
        filteredList.apply {
            clear()
            addAll(locationList)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = convertView
            ?.let { ItemDropdownInputBinding.bind(it) }
            ?: ItemDropdownInputBinding.inflate(LayoutInflater.from(context), parent, false)

        val location = getItem(position)
        binding.root.text = location?.name

        return binding.root
    }

    override fun getCount(): Int {
        return filteredList.size
    }

    override fun getItem(position: Int): Zone? {
        return filteredList.getOrNull(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    private val nameFilter = object : Filter() {
        override fun convertResultToString(resultValue: Any?): CharSequence {
            return resultValue?.let {
                (resultValue as Zone).name
            }.orEmpty()
        }

        override fun performFiltering(p0: CharSequence?): FilterResults {
            val result = FilterResults()

            p0?.let { query ->
                val filteredList = locationList.filter { category ->
                    category.name.contains(query, true)
                }

                result.values = filteredList
                result.count = filteredList.size
            }

            return result
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            filteredList.clear()

            if ((p1?.count ?: 0) > 0) {
                val resultValue = (p1?.values as List<Zone>?).orEmpty()
                filteredList.addAll(resultValue)
                notifyDataSetChanged()

                return
            }

            filteredList.addAll(locationList)
            notifyDataSetInvalidated()
        }
    }
}