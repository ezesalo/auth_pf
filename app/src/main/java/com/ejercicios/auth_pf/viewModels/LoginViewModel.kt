package com.ejercicios.auth_pf.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel(), FirebaseAuth.AuthStateListener {

 //   val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
 //   val emailConfirmado = MutableLiveData<Boolean>()

    fun ingresar (email: String, password: String): Boolean {

        var loginSatisfactorio = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){
                if (it.isSuccessful){

                }else{
                    loginSatisfactorio = false
                }
            }
        return loginSatisfactorio
    }

    fun camposCompletos (email: String, password: String): Boolean {
        var loginCompleto = false

        if (email.isNotEmpty() && password.isNotEmpty()){
            loginCompleto = true
        }
        return loginCompleto
    }

    fun emailVerificado(): Boolean? {

        val auth: FirebaseAuth = Firebase.auth
        val user = auth.currentUser
        return user!!.isEmailVerified
      // emailConfirmado.postValue(emailVerificado!!)

    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        TODO("Not yet implemented")

    }

//    fun emailVerificado() {
//
//        val profileUpdates = userProfileChangeRequest {  }
//        val user = auth.currentUser
//        user!!.updateProfile(profileUpdates).addOnCompleteListener(){
//            if (it.isSuccessful){
//                if (user.isEmailVerified){
//
//                }
//            }
//        }
//    }
}