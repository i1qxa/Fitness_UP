package org.fitnessup.wjtt.presentation.sport.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.fitnessup.wjtt.R
import org.fitnessup.wjtt.databinding.FragmentVideoInstructionBinding

const val EXERCISE_ID = "exerciseId"
class VideoInstructionFragment : Fragment() {

    private val viewModel:VideoInstructionViewModel by viewModels()
    private val binding by lazy { FragmentVideoInstructionBinding.inflate(layoutInflater) }
    private val exerciseId by lazy { arguments?.getInt(EXERCISE_ID) }
    private val ytPlayer by lazy { binding.wvInstructionsVideo }
    private val width by lazy { binding.wvInstructionsVideo.width }
    private val height by lazy { (width / 1.4).toInt() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseId?.let { exerciseId -> viewModel.setupExerciseId(exerciseId) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupBtnBackClickListener()
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            delay(1000)
            viewModel.exerciseLD.observe(viewLifecycleOwner){ exerciseItems ->
                binding.tvExerciseName.text = exerciseItems.name
                binding.tvExerciseDescription.text = exerciseItems.description
                exerciseItems.video?.let { videoId -> setupPlayer(videoId) }
            }
        }
    }

    private fun setupPlayer(videoId:String) {
        with(ytPlayer) {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webViewClient = YTWebClient()
            loadData(getHTMLData(videoId), "text/html", "utf-8")
        }
    }

    private fun getHTMLData(videoId:String): String {


        return """
        <html>
        
            <body style="margin:0px;padding:0px;">
               <div id="player"></div>
                <script>
                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '${height}',
                            width: '$width',
                            videoId: '$videoId',
                            playerVars: {
                                'playsinline': 1
                            },
                            events: {
                                'onReady': onPlayerReady
                            }
                        });
                    }
                    function onPlayerReady(event) {
                     player.playVideo();
                        // Player is ready
                    }
                    function seekTo(time) {
                        player.seekTo(time, true);
                    }
                      function playVideo() {
                        player.playVideo();
                    }
                    function pauseVideo() {
                        player.pauseVideo();
                    }
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """.trimIndent()
    }

}