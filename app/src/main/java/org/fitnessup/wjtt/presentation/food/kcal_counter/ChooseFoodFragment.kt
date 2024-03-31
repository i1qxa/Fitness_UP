package org.fitnessup.wjtt.presentation.food.kcal_counter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.data.remote.RecipeItemShort
import org.fitnessup.wjtt.databinding.AddNewFoodItemBinding
import org.fitnessup.wjtt.databinding.FragmentChooseFoodBinding
import org.fitnessup.wjtt.databinding.InputWeightDialogBinding
import org.fitnessup.wjtt.domain.getBitmapByName
import org.fitnessup.wjtt.presentation.food.kcal_counter.rv.FoodRVAdapter


class ChooseFoodFragment : Fragment() {


    private val binding by lazy { FragmentChooseFoodBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ChooseFoodViewModel>()
    private val rvAdapter by lazy { FoodRVAdapter() }
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.setupTeamImg(uri)
        }
    }

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
        setupBtnBackClickListener()
        setupBtnAddClickListener()
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

    private fun setupBtnAddClickListener(){
        binding.fabAddFood.setOnClickListener {
            setupNewFoodDialog()
        }
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_chooseFoodFragment_to_foodFragment)
        }
    }

    private fun showInputWeightDialog(foodItem: RecipeItemShort) {

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

    private fun setupNewFoodDialog(){
        viewModel.resetSelectedImg()
        val dialogBinding = AddNewFoodItemBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setView(dialogBinding.root)
        }.create()
        dialog.show()
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        viewModel.selectedImgLD.observe(viewLifecycleOwner){
            if (it==null){
                dialogBinding.ivRecipeLogo.setImageResource(R.drawable.food_img_placeholder)
            }else{
                val bitmap = requireContext().getBitmapByName(it)
                dialogBinding.ivRecipeLogo.setImageBitmap(bitmap)
            }
        }
        dialogBinding.ivRecipeLogo.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        dialogBinding.btnSubmit.setOnClickListener {
            viewModel.addFoodToDB(
                dialogBinding.tvRecipeName.text.toString().ifEmpty { "" },
                dialogBinding.etKcalValue.text.toString().ifEmpty { "0" },
                dialogBinding.etProteinValue.text.toString().ifEmpty { "0" },
                dialogBinding.etFatValue.text.toString().ifEmpty { "0" },
                dialogBinding.etCarbValue.text.toString().ifEmpty { "0" },
                dialogBinding.etWeightValue.text.toString().ifEmpty { "0" }
            )
            dialog.dismiss()
        }

    }


}