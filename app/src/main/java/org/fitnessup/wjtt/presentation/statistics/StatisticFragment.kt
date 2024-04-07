package org.fitnessup.wjtt.presentation.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.databinding.FragmentStatisticBinding

@SuppressLint("UseCompatLoadingForDrawables")
class StatisticFragment : Fragment() {

    private val binding by lazy { FragmentStatisticBinding.inflate(layoutInflater) }
    private val viewModel: StatisticViewModel by viewModels()
    private var chartName = ""
    private val activeBtnDrawable by lazy { requireContext().getDrawable(R.drawable.btn_start_background)}
    private val inactiveBtnDrawable by lazy { requireContext().getDrawable(R.drawable.inactive_background)}

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

        viewModel.selectedChart.observe(viewLifecycleOwner){
            when(it){
                1 -> {
                    binding.weight.background = activeBtnDrawable
                    binding.kcal.background = inactiveBtnDrawable
                    chartName = "Изменение веса"

                }
                else -> {
                    binding.weight.background = inactiveBtnDrawable
                    binding.kcal.background = activeBtnDrawable
                    chartName = "Суточное потребление каллорий"
                }
            }
        }

        viewModel.chartLD.observe(viewLifecycleOwner){ chartData ->
            if (chartData.size>1){
                val chartModel = AAChartModel()
                chartModel.apply {
                    chartType(AAChartType.Areaspline)
                    title(chartName)
                    backgroundColor("#FFFFFF")
                    dataLabelsEnabled(true)
                    series(
                        arrayOf(
                            AASeriesElement()
                                .name("Дата")
                                .data(chartData.map { it.value.toInt() }.toList().toTypedArray())
                        )
                    )
                }
                binding.chart.aa_drawChartWithChartModel(chartModel)
                binding.chart.visibility = View.VISIBLE
                binding.emptyChart.visibility = View.GONE
            }else{
                binding.chart.visibility = View.GONE
                binding.emptyChart.visibility = View.VISIBLE
            }

        }
    }

    private fun setupBtnClickListeners(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_statisticFragment_to_homeFragment)
        }
        binding.weight.setOnClickListener {
            viewModel.setSelectedChart(1)
        }
        binding.kcal.setOnClickListener {
            viewModel.setSelectedChart(2)
        }
    }

}