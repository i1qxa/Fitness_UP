package org.fitnessup.wjtt.presentation.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()
    }

    private fun observeViewModel(){
        binding.tvBmi.text = requireContext().getString(R.string.bmi_with_params, "0")
        binding.tvHeight.text = requireContext().getString(R.string.height, "0")
        binding.tvWeight.text = requireContext().getString(R.string.current_weight, "0")
        viewModel.personInfoLD.observe(viewLifecycleOwner){ personDB ->
            if (personDB!=null){
                binding.tvBmi.text = requireContext().getString(R.string.bmi_with_params, personDB.bmi.toString())
                binding.tvHeight.text = requireContext().getString(R.string.height, personDB.height.toString())
                binding.tvWeight.text = requireContext().getString(R.string.current_weight, personDB.weight.toString())
            }

        }
    }

    private fun setupClickListeners(){

        binding.foodBackground.setOnClickListener {
            navigateToDirection(R.id.action_homeFragment_to_foodFragment)
        }

        binding.personBackground.setOnClickListener {
            navigateToDirection(R.id.action_homeFragment_to_personFragment)
        }

        binding.sportBackground.setOnClickListener {
            navigateToDirection(R.id.action_homeFragment_to_sportFragment)
        }

        binding.statisticBackground.setOnClickListener {
            navigateToDirection(R.id.action_homeFragment_to_statisticFragment)
        }

    }

    private fun navigateToDirection(direction:Int){
        findNavController().navigate(direction)
    }

}