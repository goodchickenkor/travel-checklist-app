package com.example.perfecttrip.ui.create

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.perfecttrip.R
import com.example.perfecttrip.databinding.FragmentCreateStep1Binding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.fragment.app.activityViewModels

class CreateStep1Fragment : Fragment(R.layout.fragment_create_step1) {

    private var _binding: FragmentCreateStep1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: TripCreateViewModel by activityViewModels()

    private var startDateMillis: Long? = null
    private var endDateMillis: Long? = null

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateStep1Binding.bind(view)

        restoreDraft()

        binding.nameEdit.doAfterTextChanged {
            updateNextButtonState()
        }

        binding.dateRangeBtn.setOnClickListener {
            showDateRangePicker()
        }

        binding.nextBtn.setOnClickListener {
            val name = binding.nameEdit.text.toString().trim()
            val start = startDateMillis
            val end = endDateMillis

            if (name.isBlank() || start == null || end == null) {
                Toast.makeText(requireContext(), "여행 이름과 기간을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.setStep1Data(name, start, end)
            findNavController().navigate(R.id.action_step1_to_step2)
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        updateNextButtonState()
    }

    private fun restoreDraft() {
        val draft = viewModel.getDraft()

        if (binding.nameEdit.text.isNullOrBlank() && draft.title.isNotBlank()) {
            binding.nameEdit.setText(draft.title)
        }

        startDateMillis = draft.startDate
        endDateMillis = draft.endDate

        if (startDateMillis != null && endDateMillis != null) {
            val startText = dateFormat.format(Date(startDateMillis!!))
            val endText = dateFormat.format(Date(endDateMillis!!))

            binding.dateRangeBtn.text = "$startText ~ $endText"

        } else {
            binding.dateRangeBtn.text = "날짜 선택"

        }
    }

    private fun showDateRangePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val defaultEnd = today + (3L * 24 * 60 * 60 * 1000)

        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        val selectionPair = if (startDateMillis != null && endDateMillis != null) {
            androidx.core.util.Pair(startDateMillis, endDateMillis)
        } else {
            androidx.core.util.Pair(today, defaultEnd)
        }

        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("여행 기간 선택")
            .setCalendarConstraints(constraints)
            .setSelection(selectionPair)
            .build()

        picker.addOnPositiveButtonClickListener { selection ->
            val start = selection.first
            val end = selection.second

            if (start != null && end != null) {
                startDateMillis = start
                endDateMillis = end

                val startText = dateFormat.format(Date(start))
                val endText = dateFormat.format(Date(end))

                binding.dateRangeBtn.text = "$startText ~ $endText"

                updateNextButtonState()
            }
        }

        picker.show(parentFragmentManager, "date_range_picker")
    }

    private fun updateNextButtonState() {
        val nameOk = !binding.nameEdit.text.isNullOrBlank()
        val dateOk = startDateMillis != null && endDateMillis != null
        binding.nextBtn.isEnabled = nameOk && dateOk
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}