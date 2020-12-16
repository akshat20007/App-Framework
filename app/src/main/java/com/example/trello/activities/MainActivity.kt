package com.example.trello.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.trello.R
import com.example.trello.activities.adapters.BoardItemAdapter
import com.example.trello.activities.firebase.FirestoreClass
import com.example.trello.activities.models.Tourni
import com.example.trello.activities.models.User
import com.example.trello.activities.utils.Constants
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    PostAdapter.OnItemClickListener {

        companion object{
        const val MY_PROFILE_REQUEST_CODE : Int = 11
    }

    private lateinit var mUserName: String

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post : ArrayList<String> = ArrayList()
        for(i in 1..20){
            post.add ("Tournament # $i")
        }

        rv_boards_list.layoutManager = LinearLayoutManager(this)
        rv_boards_list.adapter = PostAdapter(post, this)

        setupActionBar()

        nav_view.setNavigationItemSelectedListener(this)

        FirestoreClass().loadUserData(this)

        fab_create_board.setOnClickListener{
            val intent = Intent(this, CreateBoardActivity::class.java)
            intent.putExtra(Constants.NAME, mUserName)
            startActivity(intent)
        }

    }

    fun populateBoardListUI(boardsList: ArrayList<Tourni>){
        hideProgressDialog()

        if (boardsList.size > 0){
            rv_boards_list.visibility = View.VISIBLE


            rv_boards_list.layoutManager = LinearLayoutManager(this@MainActivity)
            rv_boards_list.setHasFixedSize(true)

            val adapter = BoardItemAdapter(this, boardsList)
            rv_boards_list.adapter = adapter
        }else{
            rv_boards_list.visibility = View.GONE

        }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_main_activity)
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_action_navigation_menu)

        toolbar_main_activity.setNavigationOnClickListener {
            toggleDrawer()
        }
    }

    private fun toggleDrawer() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else{
         doubleBackToExit()
        }
    }

    fun updateNavigationUserDetails(user: User, readBoardsList: Boolean){

        mUserName = user.name
    Glide
        .with(this)
        .load(user.image)
        .centerCrop()
        .placeholder(R.drawable.ic_user_place_holder)
        .into(nav_user_image)

        tv_username.text = user.name

        if (readBoardsList){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().getBoardsList(this )
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                startActivity(Intent(this, MyProfileActivity::class.java))
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }



    override fun onItemClick(position: Int) {
        if (position == 0) {
            Toast.makeText(this, "Item $position clicked", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        else if (position == 1){
            Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, RegistrationActivity2::class.java))
        }
        else if (position == 2){
            Toast.makeText(this, "Item 2 clicked", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, RegistrationActivity3::class.java))
        }
    }

}