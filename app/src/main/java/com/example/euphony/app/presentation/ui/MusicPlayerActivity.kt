package com.example.euphony.app.presentation.ui

import com.example.euphony.app.presentation.ui.base.BaseActivity
import com.example.euphony.app.presentation.ui.navigation.MusicPlayerNavigation
import com.example.euphony.databinding.ActivityMusicPlayerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicPlayerActivity : BaseActivity<ActivityMusicPlayerBinding>(
    ActivityMusicPlayerBinding::inflate
) {

    @Inject
    lateinit var navigation: MusicPlayerNavigation

    override fun initView() {
        navigation.openDashboard(
            fm = supportFragmentManager
        )
    }

}
