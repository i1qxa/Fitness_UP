package org.fitnessup.wjtt.presentation.sport

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitlife.atfsd.ui.rv_training.TrainingRVAdapter
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.databinding.FragmentSportBinding

const val TRAINING_ID = "trainingId"

class SportFragment : Fragment() {

    private val binding by lazy { FragmentSportBinding.inflate(layoutInflater) }
    private val viewModel: SportViewModel by viewModels()
    private val rvAdapter by lazy { TrainingRVAdapter() }
    private val rv by lazy { binding.rvTrainingsList }

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
        setupBtnBackClickListener()
    }

    private fun observeViewModel(){
        viewModel.listOfTrainings.observe(viewLifecycleOwner){
            rvAdapter.submitList(it)
        }
    }

    private fun setupRvAdapter(){
        rvAdapter.onTrainingItemClickListener = { trainingId ->
            val args = Bundle()
            args.putInt(TRAINING_ID, trainingId)
            findNavController().navigate(R.id.action_sportFragment_to_trainingFragment, args)
        }
    }

    private fun setupRecyclerView() {
        setupRvAdapter()
        with(rv) {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_sportFragment_to_homeFragment)
        }
    }

}