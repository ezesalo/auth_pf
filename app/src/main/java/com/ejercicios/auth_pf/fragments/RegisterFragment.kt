package com.ejercicios.auth_pf.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.BoringLayout
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
import com.ejercicios.auth_pf.viewModels.RegisterViewModel
import com.ejercicios.auth_pf.viewModels.VerifyEmailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    lateinit var v: View
    lateinit var nombreRegistro: EditText
    lateinit var apellidoRegistro: EditText
    lateinit var telefonoRegistro: EditText
    lateinit var emailRegistro: EditText
    lateinit var passwordRegistro: EditText
    lateinit var registroButton: Button
    var emailConfirmado: Boolean = false
    private val viewModelRegistro: RegisterViewModel by viewModels()
    private val viewModelCheckEmail: VerifyEmailViewModel by activityViewModels()

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.register_fragment, container, false)
        nombreRegistro = v.findViewById(R.id.nombreRegistroTxt)
        apellidoRegistro = v.findViewById(R.id.apellidoRegistroTxt)
        telefonoRegistro = v.findViewById(R.id.telefonoRegistroTxt)
        emailRegistro = v.findViewById(R.id.emailRegistroTxt)
        passwordRegistro = v.findViewById(R.id.passwordRegistroTxt)
        registroButton = v.findViewById(R.id.RegistroButton)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel

//        viewModelRegistro.emailConfirmado.observe(viewLifecycleOwner, Observer {
//
//            if (viewModelCheckEmail.emailconfirmado()){
//
//                emailConfirmado = it
//
//                v.findNavController().popBackStack()
//
//            }
//        })
    }

    override fun onStart()  {
        super.onStart()



        registroButton.setOnClickListener() {
            val nombre: String = nombreRegistro.text.toString()
            val apellido: String = apellidoRegistro.text.toString()
            val telefono: String = telefonoRegistro.text.toString()
            val email: String = emailRegistro.text.toString()
            val password: String = passwordRegistro.text.toString()
            // val scope = CoroutineScope(Dispatchers.Default)

            if (viewModelRegistro.camposCompletos(nombre, apellido, telefono, email, password)) {

                Log.d("Auth", "Paso campos vacios")
                val registrado: Boolean = viewModelRegistro.registrar(nombre, apellido, telefono, email, password)

                if (registrado) {
                    val action = RegisterFragmentDirections.actionRegisterFragmentToVerifiyEmailFragment()
                    v.findNavController().navigate(action)

                } else {
                    showAlert("Ocurrio un error en el registro")
                }

            } else {
                showAlert("Deben estar todos los campos completos para poder registrarse")
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