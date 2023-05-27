package com.example.h4_dbfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import java.util.UUID

class MainActivity : AppCompatActivity() ***REMOVED***

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
    var UsuarioSelected: String? = null
     fun OnItemClick(usuario: Usuario) ***REMOVED***
        UsuarioSelected = usuario.getUid()
        usuarioNombres!!.setText(usuario.getFirstName())
        usuarioApellidos!!.setText(usuario.getSurName())
        usuarioEmail!!.setText(usuario.getEmail())
        usuarioTelefono!!.setText(usuario.getPhone())
***REMOVED***

    override fun onCreate(savedInstanceState: Bundle?) ***REMOVED***
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
***REMOVED***
    //Creación del Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean ***REMOVED***
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
***REMOVED***

    private fun listaUsuariosData()***REMOVED***
        databaseReference!!.child("Usuario").addValueEventListener(object: ValueEventListener***REMOVED***
            override fun onDataChange(snapshot: DataSnapshot) ***REMOVED***
                usuarios.clear()
                for (data in snapshot.children)***REMOVED***
                    val usuario = data.getValue(Usuario::class.java)
                    if(usuario != null)***REMOVED***
                        usuarios.add(usuario!!)
            ***REMOVED***
        ***REMOVED***
                rvUsuarios!!.adapter = UserAdapter(this@MainActivity, usuarios)
    ***REMOVED***
            override fun onCancelled(error: DatabaseError) ***REMOVED***
                Toast.makeText(applicationContext, "OnCancelled", Toast.LENGTH_SHORT).show()
    ***REMOVED***
***REMOVED***)
***REMOVED***
    private fun initFirebase()***REMOVED***
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.reference
***REMOVED***
    //Implementación del menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean ***REMOVED***
        val nombres = usuarioNombres!!.text.toString()
        val apellidos = usuarioApellidos!!.text.toString()
        val email = usuarioEmail!!.text.toString()
        val telefono = usuarioTelefono!!.text.toString()

        when (item.itemId)***REMOVED***
            R.id.icon_add -> ***REMOVED***
                if (nombres.isNullOrEmpty()||apellidos.isNullOrEmpty() || email.isNullOrEmpty() || telefono.isNullOrEmpty() ) ***REMOVED***
                    validateData()
        ***REMOVED*** else ***REMOVED***
                    val usuario = Usuario()
                    usuario.setUid(UUID.randomUUID().toString())
                    usuario.setFirstName(nombres)
                    usuario.setSurName(apellidos)
                    usuario.setEmail(email)
                    usuario.setPhone(telefono)

                    //grabar a Firebase linea a completar
                    var res = databaseReference!!.child("Usuario").child(usuario.getUid()!!).setValue(usuario)
                    println(res)
                    Toast.makeText(this, "Usuario Añadido", Toast.LENGTH_SHORT).show()
                    cleanData()
        ***REMOVED***
    ***REMOVED***

            R.id.icon_edit -> ***REMOVED***
                val usuarioEdited = Usuario()
                usuarioEdited.setUid(UserSelected!!)

                usuarioEdited.setFirstName(usuarioNombres!!.text.toString())
                usuarioEdited.setSurName(usuarioApellidos!!.text.toString())
                usuarioEdited.setEmail(usuarioEmail!!.text.toString())
                usuarioEdited.setPhone(usuarioTelefono!!.text.toString())
                //Grabar a firebase
                databaseReference!!.child("Usuario").child(usuarioEdited.getUid()!!).setValue(usuarioEdited)

                Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
    ***REMOVED***

            R.id.icon_delete -> ***REMOVED***
                //1259
                var usuarioDelete = Usuario()
                usuarioDelete.setUid(UserSelected!!)
                databaseReference!!.child("Usuario").child(usuarioDelete.getUid()!!).removeValue()
                cleanData()
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show()
    ***REMOVED***
***REMOVED***
        return true
***REMOVED***
    //Borrar campos
    private fun cleanData() ***REMOVED***
        usuarioNombres!!.setText("")
        usuarioApellidos!!.setText("")
        usuarioEmail!!.setText("")
        usuarioTelefono!!.setText("")

        usuarioNombres!!.requestFocus()
***REMOVED***

    //Validar datos
    private fun validateData()***REMOVED***
        val nombres = usuarioNombres!!.text.toString()
        val apellidos = usuarioApellidos!!.text.toString()
        val email = usuarioEmail!!.text.toString()
        val telefono = usuarioTelefono!!.text.toString()

        if (nombres.isNullOrEmpty()) ***REMOVED***
            usuarioNombres!!.error = "Obligatorio"
***REMOVED***else if (apellidos.isNullOrEmpty()) ***REMOVED***
            usuarioApellidos!!.error = "Obligatorio"
***REMOVED***else if (email.isNullOrEmpty()) ***REMOVED***
            usuarioEmail!!.error = "Obligatorio"
***REMOVED***else if (telefono.isNullOrEmpty()) ***REMOVED***
            usuarioTelefono!!.error = "Obligatorio"
***REMOVED***

***REMOVED***

***REMOVED***