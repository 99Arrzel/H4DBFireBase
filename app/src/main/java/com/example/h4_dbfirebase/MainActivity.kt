package com.example.h4_dbfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.UUID

class MainActivity : AppCompatActivity(), OnUsuarioClickListener {

    var usuarioNombres: EditText? = null
    var usuarioApellidos: EditText? = null
    var usuarioEmail: EditText? = null
    var usuarioTelefono: EditText? = null
    private lateinit var rvUsuarios: RecyclerView
    //FireBase
    var usuarios: MutableList<Usuario> = ArrayList<Usuario>()
    var UserSelected: String? = null
    var databaseReference: DatabaseReference? = null

    var firebaseDatabase : FirebaseDatabase? = null

    var UserIDSelected: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuarioNombres = findViewById(R.id.txtNombres)
        usuarioApellidos = findViewById(R.id.txtApellidos)
        usuarioEmail = findViewById(R.id.txtEmail)
        usuarioTelefono = findViewById(R.id.txtPhone)
        rvUsuarios = findViewById(R.id.rvDatosUsuarios)
        rvUsuarios.layoutManager = LinearLayoutManager(this)
        initFirebase()
        listaUsuariosData()
    }
    //Creación del Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun listaUsuariosData(){
        databaseReference!!.child("Usuario").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                usuarios.clear()
                for (data in snapshot.children){
                    val usuario = data.getValue(Usuario::class.java)
                    if(usuario != null){
                        usuario.setFirstName(usuario.getFirstName() + " " )
                        usuarios.add(usuario!!)
                    }
                }
                rvUsuarios!!.adapter = UserAdapter(this@MainActivity, usuarios, this@MainActivity)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "OnCancelledFired", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun initFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.reference
    }
    //Implementación del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val nombres = usuarioNombres!!.text.toString()
        val apellidos = usuarioApellidos!!.text.toString()
        val email = usuarioEmail!!.text.toString()
        val telefono = usuarioTelefono!!.text.toString()

        when (item.itemId){
            R.id.icon_add -> {
                if (nombres.isNullOrEmpty()||apellidos.isNullOrEmpty() || (email.isNullOrEmpty() || !correoValido(email) || uniqueEmail(email.trim()) ) || telefono.isNullOrEmpty() ) {
                    validateData()
                } else {
                    val usuario = Usuario()
                    usuario.setUid(UUID.randomUUID().toString())
                    usuario.setFirstName(nombres.trim())
                    usuario.setSurName(apellidos.trim())
                    usuario.setEmail(email.trim())
                    usuario.setPhone(telefono.trim())

                    //grabar a Firebase linea a completar
                    var res = databaseReference!!.child("Usuario").child(usuario.getUid()!!).setValue(usuario)
                    println(res)
                    Toast.makeText(this, "Usuario Añadido", Toast.LENGTH_SHORT).show()
                    cleanData()
                }
            }

            R.id.icon_edit -> {
                if(UserIDSelected == null) {
                    Toast.makeText(this, "Seleccione un usuario", Toast.LENGTH_SHORT).show()
                }else {
                    val usuarioEdited = Usuario()
                    usuarioEdited.setUid(UserIDSelected!!)
                    usuarioEdited.setFirstName(usuarioNombres!!.text.toString())
                    usuarioEdited.setSurName(usuarioApellidos!!.text.toString())
                    usuarioEdited.setEmail(usuarioEmail!!.text.toString())
                    usuarioEdited.setPhone(usuarioTelefono!!.text.toString())
                    //Grabar a firebase
                    databaseReference!!.child("Usuario").child(usuarioEdited.getUid()!!)
                        .setValue(usuarioEdited)
                    //Ahora limpiamos
                    cleanData()
                    Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.icon_delete -> {
                if(UserIDSelected == null) {
                    Toast.makeText(this, "Seleccione un usuario", Toast.LENGTH_SHORT).show()
                }else {
                    var usuarioDelete = Usuario()
                    usuarioDelete.setUid(UserIDSelected!!)
                    databaseReference!!.child("Usuario").child(usuarioDelete.getUid()!!)
                        .removeValue()
                    cleanData()
                    Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return true
    }
    //Borrar campos
    private fun cleanData() {
        usuarioNombres!!.setText("")
        usuarioApellidos!!.setText("")
        usuarioEmail!!.setText("")
        usuarioTelefono!!.setText("")
        UserIDSelected = null
        usuarioNombres!!.requestFocus()
    }

    //Validar datos
    private fun validateData(){
        val nombres = usuarioNombres!!.text.toString()
        val apellidos = usuarioApellidos!!.text.toString()
        val email = usuarioEmail!!.text.toString()
        val telefono = usuarioTelefono!!.text.toString()

        if (nombres.isNullOrEmpty()) {
            usuarioNombres!!.error = "Obligatorio"
        }else if (apellidos.isNullOrEmpty()) {
            usuarioApellidos!!.error = "Obligatorio"
        }else if (email.isNullOrEmpty() || !correoValido(email)) {
            usuarioEmail!!.error = "El correo es obligatorio y debe ser válido"
        }else if (telefono.isNullOrEmpty()) {
            usuarioTelefono!!.error = "Obligatorio"
        }
        if(uniqueEmail(email)){
            usuarioEmail!!.error = "El correo ya existe"
        }
    }
    fun correoValido(email: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun uniqueEmail(email: String): Boolean{
        return usuarios.find { it.getEmail() == email } != null
    }

    override fun OnItemClick(usuario: Usuario) {
        UserIDSelected = usuario.getUid()
        //print
        println(UserIDSelected)
        Toast.makeText(this, "Seleccionado: ${usuario.getFirstName()}", Toast.LENGTH_SHORT).show()
        usuarioNombres!!.setText(usuario.getFirstName())
        usuarioApellidos!!.setText(usuario.getSurName())
        usuarioEmail!!.setText(usuario.getEmail())
        usuarioTelefono!!.setText(usuario.getPhone())
    }


}