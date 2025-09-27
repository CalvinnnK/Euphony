package com.example.euphony.app.presentation.ui.dashboard

import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.media3.common.Player
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.euphony.R
import com.example.euphony.app.presentation.ui.adapter.MusicListAdapter
import com.example.euphony.app.presentation.ui.base.BaseFragment
import com.example.euphony.core.utils.EventObserver
import com.example.euphony.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    companion object {
        val TAG get() = "TAG.${DashboardFragment::class.java.simpleName}"

        fun newInstance() = DashboardFragment()
    }

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var musicAdapter: MusicListAdapter
    private var updateSeekBarHandler = Handler(Looper.getMainLooper())
    private var updateSeekBarRunnable: Runnable? = null

    override fun initView() {
        setupRecyclerView()
        setupPlayer()
        setupPlayControls()
        setupSeekBar()

        viewModel.searchMusic("johnson")
    }

    override fun obverseLiveData() {
        with(viewModel) {
            musicItems.observe(viewLifecycleOwner, EventObserver {
                musicAdapter.submitList(it)

                viewModel.setListMusic(it)
            })
        }
    }

    private fun setupRecyclerView() {
        musicAdapter = MusicListAdapter { musicItem ->
            // TODO: Handle item click
        }

        binding.rvSongList.apply {
            adapter = musicAdapter
            layoutManager = LinearLayoutManager(requireContext())

            // Add item decoration for spacing
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setupPlayer() {
        viewModel.player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        val duration = viewModel.player.duration
                        if (duration > 0) {
                            binding.incMusicController.seekBar.max = duration.toInt()
                            binding.incMusicController.tvSongTimer.text =
                                viewModel.formatTime(duration)
                        }
                        startSeekBarUpdate()
                    }

                    Player.STATE_ENDED -> {
                        binding.incMusicController.btnPlay.setImageResource(R.drawable.ic_control_play)
                        stopSeekBarUpdate()
                        binding.incMusicController.seekBar.progress = 0
                        binding.incMusicController.tvSongTimer.text = "0:00"
                    }

                    Player.STATE_BUFFERING -> {
                        // Show loading indicator if needed
                    }

                    Player.STATE_IDLE -> {
                        // Show loading indicator if needed
                    }
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updatePlayButtonIcon(isPlaying)
                if (isPlaying) {
                    startSeekBarUpdate()
                } else {
                    stopSeekBarUpdate()
                }
            }

            override fun onMediaMetadataChanged(mediaMetadata: androidx.media3.common.MediaMetadata) {
                // Update UI when track changes
                updateTrackInfo(mediaMetadata)
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                Toast.makeText(
                    requireContext(),
                    "Playback error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupSeekBar() {
        binding.incMusicController.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                viewModel.isUserSeeking = true
                stopSeekBarUpdate()
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.incMusicController.tvSongTimer.text =
                        viewModel.formatTime(progress.toLong())
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val seekPosition = seekBar.progress.toLong()
                viewModel.setPositionPlayer(seekPosition)
                viewModel.isUserSeeking = false

                // Resume updates if playing
                if (viewModel.isPlayerPlaying()) {
                    startSeekBarUpdate()
                }
            }
        })
    }

    private fun setupPlayControls() {
        binding.incMusicController.apply {
            btnPlay.setOnClickListener {
                if (viewModel.isPlayerPlaying()) {
                    viewModel.pausePlayer()
                } else {
                    viewModel.playPlayer()
                }
            }

            btnNext.setOnClickListener {
                viewModel.skipToNextPlayer()
            }

            btnPrevious.setOnClickListener {
                viewModel.skipToPreviousPlayer()
            }
        }
    }

    private fun startSeekBarUpdate() {
        stopSeekBarUpdate() // Ensure no duplicate runnables

        updateSeekBarRunnable = object : Runnable {
            override fun run() {
                if (!viewModel.isUserSeeking && viewModel.getPlayerState() != Player.STATE_IDLE) {
                    val currentPosition = viewModel.getPlayerPosition()
                    val duration = viewModel.getPlayerDuration()

                    if (duration > 0) {
                        binding.incMusicController.seekBar.progress = currentPosition.toInt()
                        binding.incMusicController.tvSongTimer.text =
                            viewModel.formatTime(currentPosition)

                        // Update every 100ms for smooth progress
                        updateSeekBarHandler.postDelayed(this, 100)
                    }
                }
            }
        }
        updateSeekBarHandler.post(updateSeekBarRunnable!!)
    }

    private fun stopSeekBarUpdate() {
        updateSeekBarRunnable?.let {
            updateSeekBarHandler.removeCallbacks(it)
        }
    }

    private fun updatePlayButtonIcon(isPlaying: Boolean) {
        binding.incMusicController.apply {
            if (isPlaying) {
                btnPlay.setImageResource(R.drawable.ic_control_pause)
            } else {
                btnPlay.setImageResource(R.drawable.ic_control_play)
            }
        }
    }

    private fun updateTrackInfo(mediaMetadata: androidx.media3.common.MediaMetadata) {
        binding.incMusicController.apply {
            tvSongTitle.text = mediaMetadata.title?.toString() ?: "Unknown Title"
            tvSongArtistName.text = mediaMetadata.artist?.toString() ?: "Unknown Artist"

            mediaMetadata.artworkUri?.let { artworkUri ->
                Glide.with(requireContext()).load(artworkUri).into(ivAlbum)
            }
        }
    }

}