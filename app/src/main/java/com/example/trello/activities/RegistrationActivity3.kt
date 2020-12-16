package com.example.trello.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.trello.R
import kotlinx.android.synthetic.main.activity_registration2.*
import kotlinx.android.synthetic.main.activity_registration2.toolbar_registration_activity_2
import kotlinx.android.synthetic.main.activity_registration3.*

class RegistrationActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration3)
        setUpActionBar()

        btn_r3.setOnClickListener {
            if (btn_r3.text == "Register") {

                btn_r3.text = "Unregister"

            } else if (btn_r3.text == "Unregister") {

                btn_r3.text = "Register"

            }
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
    }
    private fun setUpActionBar(){
        setSupportActionBar(toolbar_registration_activity_3)

        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        toolbar_registration_activity_3.setNavigationOnClickListener { onBackPressed() }
    }
}