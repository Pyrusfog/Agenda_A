package com.example.agenda_b

import android.graphics.Bitmap

data class Contato (
        val Nome: String,
        val Sobrenome: String,
        val Empresa: String? = null,
        val Number: String? = null,
        val Imagem: Bitmap? = null,
        var Email: String? =null
        )