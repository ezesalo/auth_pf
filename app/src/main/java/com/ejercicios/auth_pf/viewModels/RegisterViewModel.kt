package com.ejercicios.auth_pf.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ejercicios.auth_pf.entities.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {

    val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val emailConfirmado = MutableLiveData<Boolean>()

     fun registrar (nombre: String, apellido: String, telefono: String, email: String, password: String): Boolean{

         var usuarioCreado: Boolean = true

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() {
                if (it.isSuccessful){
                    Log.d("Auth", "Se pudo registrar")

                    if (auth.currentUser != null){
                        val idUsuario: String = auth.currentUser!!.uid
                        var usuarioNuevo: Usuario = Usuario(idUsuario, nombre, apellido, email, telefono, true, null, null)

                        db.collection("usuarios").document(usuarioNuevo.id!!).set(usuarioNuevo).addOnCompleteListener(){
                            if (it.isSuccessful){

                                Log.d("Auth", "Se ha creado el usuario")
                            }else{
                                usuarioCreado = false
                            }
                        }
                    }


                }


            }
                .addOnFailureListener(){
                    usuarioCreado = false
                    Log.d("Auth", "Fallo Auth")

                }


         return usuarioCreado
    }

//     fun crearUsuario (idUsuario: String, nombre: String, apellido: String, telefono: String, email: String):Boolean{
//
//        var usuarioCreado: Boolean = false
//        var usuarioNuevo: Usuario = Usuario(idUsuario, nombre, apellido, email, telefono, true, null, null)
//
//        try {
//          //  db.collection("usuarios").document(usuarioNuevo.id!!).set(usuarioNuevo)
//            usuarioCreado = true
//            Log.d("Auth", "Se ha creado el usuario")
//        }catch (e: Exception){
//            Log.d("Auth", "No se ha podido crear el usuario ${e}")
//        }
//
//        return usuarioCreado
//    }

     fun camposCompletos (nombre: String, apellido: String, telefono: String,email: String, password: String ): Boolean {
        var registroCompleto = false

        if(nombre.isNotEmpty() && apellido.isNotEmpty() && telefono.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
            registroCompleto = true
        }
        return registroCompleto
    }
}