package com.wu.demo

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar.setMaxProgress(8000)
        progressBar.setCurProgress(4000)

//       var value= ValueAnimator.ofFloat(0f,1000f)
//        value.duration=1000
//        value.setInterpolator (DecelerateInterpolator())
//        value.start()

        tv.setOnClickListener {
            var value= ValueAnimator.ofInt(4000,7000)
            value.duration=1000
            value.setInterpolator (DecelerateInterpolator())
            value.addUpdateListener {
                var va=it.getAnimatedValue()
                progressBar.setCurProgress(va as Int)
            }
            value.start()
        }


    }
}
