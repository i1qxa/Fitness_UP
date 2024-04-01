package org.fitnessup.wjtt.presentation.sport.training

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.data.local.exercise_items.ExerciseItems
import org.fitnessup.wjtt.databinding.FragmentTrainingBinding
import org.fitnessup.wjtt.presentation.sport.TRAINING_ID
import org.fitnessup.wjtt.presentation.sport.instruction.EXERCISE_ID

const val MILS_IN_SECOND: Long = 1000

class TrainingFragment : Fragment() {


    private val viewModel: TrainingViewModel by viewModels()
    private val binding by lazy { FragmentTrainingBinding.inflate(layoutInflater) }
    private val trainingId by lazy { arguments?.getInt(TRAINING_ID) }
    private lateinit var exerciseList: List<ExerciseItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trainingId?.let { trainingId -> viewModel.setupTrainingId(trainingId) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeExerciseList()
        setupBtnBackClickListener()
        setupBtnStartClickListener()
    }

    private fun observeExerciseList() {
        lifecycleScope.launch {
            viewModel.exerciseList.observe(viewLifecycleOwner) { exerciseItemsList ->
                exerciseList = exerciseItemsList
                binding.ivExerciseImg.load(exerciseList[0].logo)
                binding.tvExerciseName.text = exerciseList[0].name
                binding.tvExerciseDescription.text = exerciseList[0].description
                binding.tvRepeatRemaining.text = exerciseList[0].repeat.toString()
            }
        }
    }

    private fun setupBtnInstructionsClickListener(exerciseId:Int){
        binding.btnInstructions.setOnClickListener {
            val args = Bundle().apply {
                putInt(EXERCISE_ID, exerciseId)
            }
            findNavController().navigate(R.id.action_trainingFragment_to_videoInstructionFragment, args)
        }
    }

    private fun startTraining() {
        lifecycleScope.launch {
            exerciseList.forEach { exerciseItem ->
                binding.btnInstructions.visibility = View.VISIBLE
                setupBtnInstructionsClickListener(exerciseItem.id)
                binding.ivExerciseImg.load(exerciseItem.logo)
                binding.tvExerciseName.text = exerciseItem.name
                binding.tvExerciseDescription.text = exerciseItem.description
                var amountOfRepeat = exerciseItem.repeat
                while (amountOfRepeat > 0) {
                    binding.ivExerciseImg.visibility = View.VISIBLE
                    binding.tvRest.visibility = View.GONE
                    binding.tvRepeatRemaining.text = amountOfRepeat.toString()
                    var timer = exerciseItem.duration
                    updateTimer(timer)
                    binding.layoutTimer.timerProgress.max = timer
                    binding.layoutTimer.timerProgress.setProgress(timer, true)
                    while (timer > 0) {
                        delay(MILS_IN_SECOND)
                        timer -= 1
                        updateTimer(timer)
                        binding.layoutTimer.timerProgress.setProgress(timer, true)
                        if (timer == 0) {
                            var timeForRest = 30
                            binding.ivExerciseImg.visibility = View.GONE
                            binding.tvRest.visibility = View.VISIBLE
                            binding.layoutTimer.timerProgress.max = timeForRest
                            binding.layoutTimer.timerProgress.setProgress(timeForRest, true)
                            updateTimer(timeForRest)
                            while (timeForRest > 0) {
                                delay(MILS_IN_SECOND)
                                timeForRest -= 1
                                updateTimer(timeForRest)
                                binding.layoutTimer.timerProgress.setProgress(timeForRest, true)
                            }
                        }
                    }
                    amountOfRepeat -= 1
                }
            }
            binding.btnStart.visibility = View.VISIBLE
            binding.btnInstructions.visibility = View.GONE
        }
    }


    private fun updateTimer(time: Int) {
        binding.layoutTimer.tvTimer.text = getTimeFormatted(time)
    }

    private fun getTimeFormatted(time: Int): String {
        val mils = (time * MILS_IN_SECOND)
        val minutes = (mils % (MILS_IN_SECOND * 60 * 60)) / (MILS_IN_SECOND * 60)
        val seconds = time - minutes * 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun setupBtnStartClickListener() {
        binding.btnStart.setOnClickListener {
            startTraining()
            binding.btnStart.visibility = View.GONE
        }
    }


    private fun setupBtnBackClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}