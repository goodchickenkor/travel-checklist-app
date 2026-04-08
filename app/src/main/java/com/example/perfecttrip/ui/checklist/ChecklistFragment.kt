package com.example.perfecttrip.ui.checklist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perfecttrip.R
import com.example.perfecttrip.databinding.FragmentChecklistBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.perfecttrip.data.local.entity.AppDatabase
import com.example.perfecttrip.data.local.entity.ChecklistItemEntity
import com.example.perfecttrip.data.local.entity.TripEntity
import com.example.perfecttrip.data.local.entity.TripRepository
import com.example.perfecttrip.model.Category
import com.example.perfecttrip.model.displayName
import com.example.perfecttrip.ui.create.TripCreateViewModel
import com.example.perfecttrip.ui.home.Trip
import com.example.perfecttrip.ui.home.TripListViewModel
import kotlinx.coroutines.launch

class ChecklistFragment : Fragment(R.layout.fragment_checklist) {

    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TripCreateViewModel by activityViewModels()

    private lateinit var adapter: ChecklistAdapter

    private lateinit var db: AppDatabase
    private lateinit var repo: TripRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentChecklistBinding.bind(view)

        db = AppDatabase.getDatabase(requireContext())
        repo = TripRepository(db)

        adapter = ChecklistAdapter(mutableListOf()) { item ->

            val tripId = arguments?.getInt("tripId", -1) ?: -1

            if (tripId != -1) {
                lifecycleScope.launch {
                    db.checklistDao().updateItem(
                        ChecklistItemEntity(
                            id = item.id,
                            tripId = tripId,
                            name = item.name,
                            isChecked = item.isChecked,
                            category = item.category.name   // 🔥 추가
                        )
                    )
                }
            }
        }

        binding.checklistRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.checklistRecycler.adapter = adapter

        val tripId = arguments?.getInt("tripId", -1) ?: -1

        Log.d("CHECK", "tripId = $tripId")

        if (tripId != -1) {
            loadExistingTrip(tripId)
        } else {
            loadNewChecklist()
        }

        setupCompleteButton(tripId)
    }

    // 🔥 기존 여행 로드
    private fun loadExistingTrip(tripId: Int) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                db.checklistDao().getItems(tripId).collect { items ->

                    val groupedList = mutableListOf<ChecklistItem>()

                    // 🔥 category 기준 그룹핑
                    val grouped = items.groupBy { it.category }

                    // 기본 준비물 먼저
                    grouped[Category.BASE.name]?.let { baseItems ->
                        groupedList.add(ChecklistItem.Header(Category.BASE.displayName()))

                        baseItems.forEach {
                            groupedList.add(
                                ChecklistItem.Item(
                                    id = it.id,
                                    name = it.name,
                                    isChecked = it.isChecked,
                                    category = Category.BASE
                                )
                            )
                        }
                    }

                    // 나머지 카테고리
                    grouped.filterKeys { it != Category.BASE.name }.forEach { (categoryName, list) ->
                        val category = Category.valueOf(categoryName)

                        groupedList.add(ChecklistItem.Header(category.displayName()))

                        list.forEach {
                            groupedList.add(
                                ChecklistItem.Item(
                                    id = it.id,
                                    name = it.name,
                                    isChecked = it.isChecked,
                                    category = category
                                )
                            )
                        }
                    }

                    adapter.submitList(groupedList)
                }
            }
        }
    }

    // 🔥 새 체크리스트 생성
    private fun loadNewChecklist() {
        val draft = viewModel.getDraft()

        val list = ChecklistGenerator
            .generate(draft.categories.toList())

        adapter.submitList(list)
    }

    // 🔥 완료 버튼
    private fun setupCompleteButton(tripId: Int) {

        binding.completeBtn.setOnClickListener {

            if (tripId != -1) {
                findNavController().popBackStack()
                return@setOnClickListener
            }

            val draft = viewModel.getDraft()

            // 🔥 Header 제외하고 Item만 추출
            val items = adapter.getItems()
                .filterIsInstance<ChecklistItem.Item>()

            lifecycleScope.launch {
                repo.insertTripWithItems(
                    TripEntity(
                        title = draft.title,
                        startDate = draft.startDate!!,
                        endDate = draft.endDate!!
                    ),
                    items.map {
                        ChecklistItemEntity(
                            name = it.name,
                            isChecked = it.isChecked,
                            category = it.category.name,
                            tripId = 0 // repo에서 채움
                        )
                    }
                )

                findNavController().popBackStack(R.id.homeFragment, false)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}