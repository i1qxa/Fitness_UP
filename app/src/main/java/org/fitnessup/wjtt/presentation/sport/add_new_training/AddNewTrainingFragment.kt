package org.fitnessup.wjtt.presentation.sport.add_new_training

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.fitnessup.wjtt.R

class AddNewTrainingFragment : Fragment() {

    companion object {
        fun newInstance() = AddNewTrainingFragment()
    }

    private val viewModel: AddNewTrainingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_new_training, container, false)
    }
}