package com.ejercicios.auth_pf.fragments

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ejercicios.auth_pf.R
import com.ejercicios.auth_pf.activities.AuthActivity
import com.ejercicios.auth_pf.viewModels.RegisterViewModel
import com.ejercicios.auth_pf.viewModels.VerifyEmailViewModel

class VerifyEmailFragment : Fragment() {

    lateinit var v: View
    lateinit var btn: Button
    lateinit var btnLogin: Button
    private val viewModelCheckEmail: VerifyEmailViewModel by viewModels()
    private val viewModelRegistro: RegisterViewModel by activityViewModels()

    companion object {
        fun newInstance() = VerifyEmailFragment()

    }

    private lateinit var viewModel: VerifyEmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.verifiy_email_fragment, container, false)
        btn = v.findViewById(R.id.verificacionButton)
        btnLogin = v.findViewById(R.id.iraloginbtn)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VerifyEmailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btn.setOnClickListener(){
            viewModelCheckEmail.enviarEmailDeVerificacion()

        }

        btnLogin.setOnClickListener(){

            if (viewModelCheckEmail.emailconfirmado()){
                Log.d("Login", "Email verificado")
                val action = VerifyEmailFragmentDirections.actionVerifiyEmailFragmentToLoginFragment2()
                v.findNavController().navigate(action)
            }else{
                Log.d("Login", "Email No verificado")
                showAlert("Email No verificado")
            }


        }
    }

    fun showAlert(msg: String){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}