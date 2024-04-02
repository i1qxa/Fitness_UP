package org.fitnessup.wjtt.presentation.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.databinding.FragmentPersonBinding

class PersonFragment : Fragment() {

    private val viewModel: PersonViewModel by viewModels()
    private val binding by lazy { FragmentPersonBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupBtnClickListeners()
    }

    private fun observeViewModel(){
        viewModel.personInfoLD.observe(viewLifecycleOwner){ personDB ->
            if (personDB!=null){
                with(binding){
                    tvBMIValue.text = personDB.bmi.toString()
                    etHeightValue.setText(personDB.height.toString())
                    etWeightValue.setText(personDB.weight.toString())
                }
            }

        }
    }

    private fun setupBtnClickListeners(){
        binding.btnSave.setOnClickListener {
            viewModel.updatePersonData(binding.etHeightValue.text.toString(), binding.etWeightValue.text.toString())
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_personFragment_to_homeFragment)
        }
    }

}