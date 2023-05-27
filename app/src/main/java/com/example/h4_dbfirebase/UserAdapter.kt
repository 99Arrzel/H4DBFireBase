package com.example.h4_dbfirebase
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.database.core.Context

class UserAdapter(private val context: Context,
                  private val usuarios: List<Usuario>,
                  //private val itemClickListener: OnUsuarioClickListener
                  ): RecyclerView.Adapter<UserAdapter.UserHolder>()***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder ***REMOVED***
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
        return UserHolder(view)
***REMOVED***

    override fun onBindViewHolder(holder: UserHolder, position: Int) ***REMOVED***
        val usuario = usuarios[position]
        holder.bind(usuario)
***REMOVED***

    override fun getItemCount(): Int ***REMOVED***
        return  usuarios.size
***REMOVED***


    class UserHolder(itemView: View): RecyclerView.ViewHolder(itemView) ***REMOVED***
        private val foto = itemView.findViewById<ImageView>(R.id.ilvFoto)
        private val name = itemView.findViewById<TextView>(R.id.ilvNombre)
        private val email = itemView.findViewById<TextView>(R.id.ilvEmail)
        private val phone = itemView.findViewById<TextView>(R.id.ilvPhone)

        fun bind(usuario: Usuario) ***REMOVED***
            foto.setImageResource(R.drawable.ic_user)
            name.text = usuario.toString()
            email.text = usuario.getEmail()
            phone.text = usuario.getPhone()
***REMOVED***
***REMOVED***
***REMOVED***