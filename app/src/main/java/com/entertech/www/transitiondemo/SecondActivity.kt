package com.entertech.www.transitiondemo

import android.os.Bundle
import android.transition.Slide
import android.view.Window
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Fade

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        window.enterTransition = CustomTransition(this).apply { duration = 2000 }
        window.exitTransition = null
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.switchFragment).setOnClickListener {
            val tr = supportFragmentManager.beginTransaction()
            tr.add(R.id.container, SecondFragment.newInstance())
            tr.commitNow()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tr = supportFragmentManager.beginTransaction()
        tr.add(R.id.container, SecondFragment.newInstance())
        tr.commitNow()

//        val tr1 = supportFragmentManager.beginTransaction()
//        tr1.replace(R.id.container, ThirdFragment.newInstance())
//        tr1.commit()
    }
}