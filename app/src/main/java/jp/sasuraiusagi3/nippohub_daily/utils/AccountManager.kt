package jp.sasuraiusagi3.nippohub_daily.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by sasurai-usagi3 on 2019/04/24.
 */

object AccountManager {
    private val auth = FirebaseAuth.getInstance()
    val currentUser
        get() = auth.currentUser


    fun signIn(email: String, password: String, callbackComplete: (Task<AuthResult>) -> Unit, callbackFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(callbackComplete).addOnFailureListener(callbackFailure)
    }

    fun signUp(email: String, password: String, callbackComplete: (Task<AuthResult>) -> Unit, callbackFailure: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(callbackComplete).addOnFailureListener(callbackFailure)
    }
}