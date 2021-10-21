package com.ejercicios.auth_pf.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ejercicios.auth_pf.R
import com.ejercicios.auth_pf.viewModels.LoginViewModel
import com.ejercicios.auth_pf.viewModels.RegisterViewModel
import com.ejercicios.auth_pf.viewModels.VerifyEmailViewModel
import kotlin.properties.Delegates

class LoginFragment : Fragment() {

    lateinit var v: View
    lateinit var emailLogin: EditText
    lateinit var passwordLogin: EditText
    lateinit var loginButton: Button
    lateinit var irARegistro: Button
    var emailConfirmado: Boolean = false
    private val viewModelLogin: LoginViewModel by viewModels()
    private val viewModelRegistro: RegisterViewModel by activityViewModels()
    private val viewModelCheckEmail: VerifyEmailViewModel by activityViewModels()

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.login_fragment, container, false)

        emailLogin = v.findViewById(R.id.emailLoginTxt)
        passwordLogin = v.findViewById(R.id.passwordLoginTxt)
        loginButton = v.findViewById(R.id.loginButton)
        irARegistro = v.findViewById(R.id.segundo_text_registro)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//        // TODO: Use the ViewModel
//
//        viewModelLogin.emailConfirmado.observe(viewLifecycleOwner, Observer {
//            emailConfirmado = it
//            Log.d("Login", "valor de IT ${it}")
//
//        })

    }


    override fun onStart() {
        super.onStart()

        irARegistro.setOnClickListener(){
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            v.findNavController().navigate(action)
        }

        loginButton.setOnClickListener(){
            val email: String = emailLogin.text.toString()
            val password: String = passwordLogin.text.toString()

            if (viewModelLogin.camposCompletos(email, password)){
                Log.d("Login", "Paso campos vacios")

                var emailVerificado: Boolean = viewModelCheckEmail.emailconfirmado()

                if (viewModelLogin.emailVerificado()!!) {

                    Log.d("Login", "Email verificado")

                    if (viewModelLogin.ingresar(email, password)){

                        val action2 = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        v.findNavController().navigate(action2)
                        Log.d("Login", "Ingreso exitoso")

                    }else{
                        Log.d("Login", "Hubo un problema en el ingreso")
                        showAlert("Hubo un problema en el ingreso")
                    }
                }else{
                    Log.d("Login", "Email No verificado")
                    showAlert("Email No verificado")
                }
            }else{
                Log.d("Login", "Campos vacios")
                showAlert("campos vacios")
            }
        }
    }


//
//    override fun onStart() {
//        super.onStart()
//
//        irARegistro.setOnClickListener(){
//            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
//            v.findNavController().navigate(action)
//        }
//
//        loginButton.setOnClickListener(){
//            val email: String = emailLogin.text.toString()
//            val password: String = passwordLogin.text.toString()
//
//            if (viewModelLogin.camposCompletos(email, password)){
//                Log.d("Login", "Paso campos vacios")
//
//                if (emailConfirmado) {
//
//                    Log.d("Login", "Email verificado")
//
//                    if (viewModelLogin.ingresar(email, password)){
//
//                        val action2 = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//                        v.findNavController().navigate(action2)
//                        Log.d("Login", "Ingreso exitoso")
//
//                    }else{
//                        Log.d("Login", "Hubo un problema en el ingreso")
//                        showAlert("Hubo un problema en el ingreso")
//                    }
//                }else{
//                    Log.d("Login", "Email No verificado")
//                    showAlert("Email No verificado")
//                }
//            }else{
//                Log.d("Login", "Campos vacios")
//                showAlert("campos vacios")
//            }
//        }
//    }

    fun showAlert(msg: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}