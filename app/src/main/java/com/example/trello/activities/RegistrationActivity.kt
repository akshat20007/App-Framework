package com.example.trello.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.trello.R
import com.example.trello.activities.models.Tourni
import com.example.trello.activities.models.User
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.app_bar_main.*

class RegistrationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setUpActionBar()
        btn_r1.setOnClickListener {
            if (btn_r1.text == "Register") {

                    btn_r1.text = "Unregister"

            } else if (btn_r1.text == "Unregister") {

                    btn_r1.text = "Register"

            }
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
    }
    private fun setUpActionBar(){
        setSupportActionBar(toolbar_registration_activity)

        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        toolbar_registration_activity.setNavigationOnClickListener { onBackPressed() }
    }







    }

























