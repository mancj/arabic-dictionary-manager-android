package io.arabic.dictionary.manager.ui.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.arabic.dictionary.manager.R
import io.arabic.dictionary.manager.ui.screen.dailyarticle.DailyArticleActivity
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)


    }

    fun startDailyArticleActivity(view: View) {
        startActivity(Intent(this, DailyArticleActivity::class.java))
    }

}
