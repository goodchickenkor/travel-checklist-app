package com.example.perfecttrip.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfecttrip.R
import com.example.perfecttrip.databinding.FragmentHomeBinding
import com.example.perfecttrip.ui.create.TripCreateViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!

    private val viewModel: TripListViewModel by activityViewModels()
    private val createViewModel: TripCreateViewModel by activityViewModels()
    private lateinit var adapter: TripAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentHomeBinding.bind(view)

        val adapter = TripAdapter(
            onClick = { trip ->
                val bundle = Bundle().apply {
                    putInt("tripId", trip.id)
                }

                findNavController().navigate(
                    R.id.checklistFragment,
                    bundle
                )
            },
            onDeleteClick = { trip ->
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("여행 삭제")
                    .setMessage("'${trip.title}' 여행을 삭제할까요?")
                    .setNegativeButton("취소", null)
                    .setPositiveButton("삭제") { _, _ ->
                        viewModel.deleteTrip(trip.id)
                    }
                    .show()
            }
        )

        b.tripRecycler.layoutManager = LinearLayoutManager(requireContext())
        b.tripRecycler.adapter = adapter

        // 🔥 DB 데이터 observe
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tripList.collect { tripEntities ->

                    val trips = tripEntities.map {
                        Trip(
                            id = it.id,
                            title = it.title,
                            startDate = it.startDate,
                            endDate = it.endDate
                        )
                    }

                    adapter.submitList(trips)

                    if (trips.isEmpty()) {
                        b.emptyText.visibility = View.VISIBLE
                        b.tripRecycler.visibility = View.GONE
                    } else {
                        b.emptyText.visibility = View.GONE
                        b.tripRecycler.visibility = View.VISIBLE
                    }
                }
            }
        }

        b.addTripFab.setOnClickListener {
            createViewModel.reset()
            findNavController().navigate(R.id.action_home_to_step1)
        }
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}