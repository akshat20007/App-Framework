package com.example.trello.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.trello.R
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.btn_r1
import kotlinx.android.synthetic.main.activity_registration2.*

class RegistrationActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration2)
        setUpActionBar()

        btn_r2.setOnClickListener {
            if (btn_r2.text == "Register") {

                    btn_r2.text = "Unregister"

            } else if (btn_r2.text == "Unregister") {

                    btn_r2.text = "Register"

            }
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
    }
    private fun setUpActionBar(){
        setSupportActionBar(toolbar_registration_activity_2)

        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        toolbar_registration_activity_2.setNavigationOnClickListener { onBackPressed() }
    }







}