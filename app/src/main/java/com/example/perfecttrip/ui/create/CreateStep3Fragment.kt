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
import com.example.perfecttrip.databinding.FragmentCreateStep3Binding
import com.example.perfecttrip.model.Category

class CreateStep3Fragment : Fragment(R.layout.fragment_create_step3) {

    private var _binding: FragmentCreateStep3Binding? = null
    private val binding get() = _binding!!

    private val viewModel: TripCreateViewModel by activityViewModels()


    private val selectedCategories = mutableSetOf<Category>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCreateStep3Binding.bind(view)

        setupToggleImage(binding.fishingBtn, Category.FISHING,
            R.drawable.selected_fishing, R.drawable.unselected_fishing)

        setupToggleImage(binding.golfBtn, Category.GOLF,
            R.drawable.selected_golf, R.drawable.unselected_golf)

        setupToggleImage(binding.mountainBtn, Category.MOUNTAIN,
            R.drawable.selected_mountain, R.drawable.unselected_mountain)

        setupToggleImage(binding.photoBtn, Category.PHOTO,
            R.drawable.selected_photo, R.drawable.unselected_photo)

        setupToggleImage(binding.picnicBtn, Category.PICNIC,
            R.drawable.selected_picnic, R.drawable.unselected_picnic)

        setupToggleImage(binding.skiBtn, Category.SKI,
            R.drawable.selected_ski, R.drawable.unselected_ski)

        setupToggleImage(binding.swimBtn, Category.SWIM,
            R.drawable.selected_swim, R.drawable.unselected_swim)

        setupToggleImage(binding.trackingBtn, Category.TRACKING,
            R.drawable.selected_tracking, R.drawable.unselected_tracking)

        setupToggleImage(binding.valleyBtn, Category.VALLEY,
            R.drawable.selected_valley, R.drawable.unselected_valley)


        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.finishBtn.setOnClickListener {
            val current = viewModel.getDraft().categories
            val merged = current + selectedCategories   // 🔥 Step2 + Step3 합치기

            viewModel.setCategories(merged)
            findNavController().navigate(R.id.action_step3_to_checklist)
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