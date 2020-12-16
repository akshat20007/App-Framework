package com.example.trello.activities.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.trello.activities.MyProfileActivity
import com.example.trello.activities.*
import com.example.trello.activities.models.Tourni
import com.example.trello.activities.models.User
import com.example.trello.activities.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userinfo: com.example.trello.activities.models.User){
    mFireStore.collection(Constants.USERS)
        .document(getCurrentUserId())
        .set(userinfo, SetOptions.merge())
        .addOnSuccessListener {
            activity.userRegisteredSuccess()
        }
    }

    fun createBoard(activity: CreateBoardActivity, board: Tourni){
        mFireStore.collection(Constants.TORNI)
            .document()
            .set(board, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Board created successfully")
                Toast.makeText(activity,
                "Board created successfully.", Toast.LENGTH_SHORT).show()
                activity.boardCreatedSuccessfully()
            }.addOnFailureListener{
                exception ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName,
                "Error while creating a board.",
                exception)
            }
    }

    fun getBoardsList(activity: MainActivity){
        mFireStore.collection(Constants.TORNI)
            .whereArrayContains(Constants.SLOTS, getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document->
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                val boardlist: ArrayList<Tourni> = ArrayList()
                for(i in document.documents){
                    val board = i.toObject(Tourni::class.java)!!
                    board.documentId = i.id
                    boardlist.add(board)
                }

                activity.populateBoardListUI(boardlist)
            }
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName, "Profile Data Updated Successfully!")
                Toast.makeText(activity, "Profile updated successfully!", Toast.LENGTH_LONG).show()
                activity.profileUpdateSuccess()
            }.addOnFailureListener{
                e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating a board.",
                    e
                )
                Toast.makeText(activity, "Error when updating the profile!", Toast.LENGTH_SHORT).show()
            }
    }

    fun loadUserData(activity: Activity, readBoardsList: Boolean = false){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)!!
                when(activity) {
                    is IntroActivity -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser,readBoardsList)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDataInUI(loggedInUser)
                    }
                }
            }.addOnFailureListener{
                e->
                when(activity) {
                    is IntroActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e("SignInUser", "Error writing document", e)
            }
    }

    fun getCurrentUserId(): String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}