package com.ejercicios.auth_pf.viewModels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerifyEmailViewModel : ViewModel() {

    val auth: FirebaseAuth = Firebase.auth
    val user = auth.currentUser

    fun enviarEmailDeVerificacion (): Boolean{

        var emailEnviado = true
        user!!.sendEmailVerification().addOnCompleteListener(){
            if (it.isSuccessful){

            }else{
                emailEnviado = false
            }
        }
        return emailEnviado
    }

    fun emailconfirmado(): Boolean{
       var emailFueVerificado: Boolean
       emailFueVerificado = user!!.isEmailVerified
        return emailFueVerificado
    }
}