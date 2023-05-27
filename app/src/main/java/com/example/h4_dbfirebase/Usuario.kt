package com.example.h4_dbfirebase

class Usuario ***REMOVED***
    private var uid: String? = null
    private var nombres: String? = null
    private var apellidos: String? = null
    private var email: String? = null
    private var telefono: String? = null
    //constructor
    fun Usuario() ***REMOVED******REMOVED***

    fun getUid(): String? ***REMOVED***
        return uid
***REMOVED***

    fun setUid(uid: String) ***REMOVED***
        this.uid = uid
***REMOVED***

    fun getFirstName(): String? ***REMOVED***
        return nombres
***REMOVED***

    fun setFirstName(nombres: String) ***REMOVED***
        this.nombres = nombres
***REMOVED***

    fun getSurName(): String? ***REMOVED***
        return apellidos
***REMOVED***

    fun setSurName(apellidos: String) ***REMOVED***
        this.apellidos = apellidos
***REMOVED***

    fun getEmail(): String? ***REMOVED***
        return email
***REMOVED***

    fun setEmail(email: String) ***REMOVED***
        this.email = email
***REMOVED***

    fun getPhone(): String? ***REMOVED***
        return telefono
***REMOVED***

    fun setPhone(telefono: String) ***REMOVED***
        this.telefono = telefono
***REMOVED***


    override fun toString(): String ***REMOVED***
        return "$nombres" + "$apellidos"

***REMOVED***

***REMOVED***