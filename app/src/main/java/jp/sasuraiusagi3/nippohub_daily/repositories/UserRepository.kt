package jp.sasuraiusagi3.nippohub_daily.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import jp.sasuraiusagi3.nippohub_daily.models.User

/**
 * Created by sasurai-usagi3 on 2019/04/24.
 */

object UserRepository {
    private val auth = FirebaseAuth.getInstance()
    val currentUser: User?
        get() {
            val user = auth.currentUser

            return user?.let { User(it.uid, it.email ?: "") }
        }

    fun signIn(email: String, password: String, callbackComplete: (Task<AuthResult>) -> Unit, callbackFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(callbackComplete).addOnFailureListener(callbackFailure)
    }

    fun signUp(email: String, password: String, callbackComplete: (Task<AuthResult>) -> Unit, callbackFailure: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(callbackComplete).addOnFailureListener(callbackFailure)
    }

    fun signOut() {
        auth.signOut()
    }

    fun didSignIn() = currentUser != null
}