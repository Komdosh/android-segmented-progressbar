package com.example.myapplication

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.view.SegmentedProgressBar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupWindowDecorView()
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.segmentProgressBar.setContexts(
            barContexts = listOf(
                SegmentedProgressBar.BarContext(
                    Color.parseColor("#FF6C5C"),
                    Color.parseColor("#FF8E95"),
                    0.35f
                ),
                SegmentedProgressBar.BarContext(
                    Color.parseColor("#FFBF74"),
                    Color.parseColor("#FFDA58"),
                    0.19f
                ),
                SegmentedProgressBar.BarContext(
                    Color.parseColor("#C4AEFC"),
                    Color.parseColor("#F29DDA"),
                    0.16f
                ),
                SegmentedProgressBar.BarContext(
                    Color.parseColor("#49C6FC"),
                    Color.parseColor("#5AE1FF"),
                    0.30f
                ),
            )
        )
    }


    private fun setupWindowDecorView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(false)

            if (window?.decorView != null) {
                val controller = window?.insetsController
                if (controller != null) {
                    controller.hide(
                        WindowInsets.Type.statusBars()
                                or WindowInsets.Type.navigationBars()
                                or WindowInsets.Type.captionBar()
                                or WindowInsets.Type.systemBars()
                                or WindowInsets.Type.displayCutout()
                    )
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        } else {
            window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.STATUS_BAR_VISIBLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }
}