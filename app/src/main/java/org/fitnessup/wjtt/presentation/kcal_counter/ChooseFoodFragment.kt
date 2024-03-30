package org.fitnessup.wjtt.presentation.kcal_counter

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.data.remote.RecipeItemShort
import org.fitnessup.wjtt.databinding.FragmentChooseFoodBinding
import org.fitnessup.wjtt.databinding.InputWeightDialogBinding
import org.fitnessup.wjtt.presentation.kcal_counter.rv.FoodRVAdapter


class ChooseFoodFragment : Fragment() {


    private val binding by lazy { FragmentChooseFoodBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ChooseFoodViewModel>()
    private val rvAdapter by lazy { FoodRVAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupBtnSearchClickListener()
    }


    private fun setupBtnSearchClickListener() {
        binding.btnSearch.setOnClickListener {
            if (binding.etRecipeName.text.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.hint_et_food_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.translateQuery(
                    binding.etRecipeName.text.toString()
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.listRecipes.observe(viewLifecycleOwner) { recipeItemShorts ->
            if (recipeItemShorts != null) {
                binding.pbDietsLoading.visibility = View.GONE
                binding.rvRecipeList.visibility = View.VISIBLE
                rvAdapter.submitList(recipeItemShorts)
            } else {
                binding.tvEmptyFoodList.visibility = View.GONE
                binding.pbDietsLoading.visibility = View.VISIBLE
                binding.rvRecipeList.visibility = View.GONE
            }
        }
        viewModel.errorRequest.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    tvEmptyFoodList.visibility = View.VISIBLE
                    tvEmptyFoodList.text = getString(R.string.tv_request_empty)
                    rvRecipeList.visibility = View.GONE
                }
            } else {
                with(binding) {
                    tvEmptyFoodList.visibility = View.GONE
                    rvRecipeList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRVAdapter() {
        with(rvAdapter) {
            onItemClickListener = {
                showInputWeightDialog(it)
            }
        }
    }

    private fun setupRecyclerView() {
        setupRVAdapter()
        with(binding.rvRecipeList) {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun showInputWeightDialog(foodItem: RecipeItemShort) {
//        val layout = LinearLayout(requireContext()).apply {
//            orientation = LinearLayout.VERTICAL
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
//        }
//
//        val weightInGrams = EditText(requireContext()).apply {
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            ).apply {
//                setMargins(50, 50, 50, 50)
//            }
//            inputType = EditText.TY
//        }
//
//        layout.addView(imageView)
        val dialogBinding = InputWeightDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        dialog.show()
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSubmit.setOnClickListener {
            viewModel.addFoodToDB(foodItem, dialogBinding.etWeight.text.toString().toInt())
            dialog.dismiss()
        }
    }


}