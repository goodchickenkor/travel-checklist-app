package com.example.perfecttrip.ui.create

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.perfecttrip.R
import com.example.perfecttrip.databinding.FragmentCreateStep2Binding
import com.example.perfecttrip.model.Category


class CreateStep2Fragment : Fragment(R.layout.fragment_create_step2) {

    private var _binding: FragmentCreateStep2Binding? = null
    private val binding get() = _binding!!

    // 🔥 String → Category로 변경
    private val selectedCategories = mutableSetOf<Category>()

    private val viewModel: TripCreateViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateStep2Binding.bind(view)

        setupToggleImage(binding.babyBtn, Category.BABY,
            R.drawable.selected_baby, R.drawable.unselected_baby)

        setupToggleImage(binding.petBtn, Category.PET,
            R.drawable.selected_pet, R.drawable.unselected_pet)

        setupToggleImage(binding.patientBtn, Category.PATIENT,
            R.drawable.selected_patient, R.drawable.unselected_patient)

        setupToggleImage(binding.elderBtn, Category.ELDER,
            R.drawable.selected_elder, R.drawable.unselected_elder)

        setupToggleImage(binding.disabledBtn, Category.DISABLED,
            R.drawable.selected_disabled, R.drawable.unselected_disabled)

        // 건너뛰기
        binding.skipBtn.setOnClickListener {
            viewModel.setCategories(emptySet())
            findNavController().navigate(R.id.action_step2_to_step3)
        }

        // 확인
        binding.confirmBtn.setOnClickListener {
            viewModel.setCategories(selectedCategories)
            findNavController().navigate(R.id.action_step2_to_step3)
        }

        // 뒤로가기
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupToggleImage(
        imageView: ImageView,
        category: Category,
        selectedRes: Int,
        unselectedRes: Int
    ) {
        var isSelected = false

        imageView.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                selectedCategories.add(category)
                imageView.setImageResource(selectedRes)
            } else {
                selectedCategories.remove(category)
                imageView.setImageResource(unselectedRes)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}