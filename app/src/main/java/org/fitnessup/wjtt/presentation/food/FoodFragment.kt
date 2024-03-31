package org.fitnessup.wjtt.presentation.food

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.databinding.FragmentFoodBinding
import org.fitnessup.wjtt.presentation.food.kcal_counter.rv.FoodRVAdapter
import org.fitnessup.wjtt.presentation.food.rv.FoodDBRVAdapter

class FoodFragment : Fragment() {

    private val binding by lazy { FragmentFoodBinding.inflate(layoutInflater) }
    private val viewModel: FoodViewModel by viewModels()
    private val rvAdapter by lazy { FoodDBRVAdapter() }

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
        setupFabClickListener()
        setupBtnBackClickListener()
    }

    private fun observeViewModel(){
        viewModel.listOfFood.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.rvRecipeList.visibility = View.VISIBLE
                binding.tvEmptyFoodList.visibility = View.GONE
            }else{
                binding.rvRecipeList.visibility = View.GONE
                binding.tvEmptyFoodList.visibility = View.VISIBLE
            }
            rvAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvRecipeList) {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun setupFabClickListener(){
        binding.fabAddFood.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_chooseFoodFragment)
        }
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_foodFragment_to_homeFragment)
        }
    }

}