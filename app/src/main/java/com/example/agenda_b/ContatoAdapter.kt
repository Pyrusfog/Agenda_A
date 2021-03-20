package com.example.agenda_b

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContatoAdapter(context: Context): ArrayAdapter<Contato>(context,0) {
    override fun getView(i: Int, convertView: View?, parent: ViewGroup): View {
        val v: View

        if(convertView != null){
            v = convertView
        }else{
            v = LayoutInflater.from(context).inflate(R.layout.list_view_contact,parent, false)
        }

        val contato = getItem(i)

        val txt_nome = v.findViewById<TextView>(R.id.list_nome_contato)
        val txt_sobrenome = v.findViewById<TextView>(R.id.list_sobrenome_contato)


        txt_nome.text = contato?.Nome.toString()
        txt_sobrenome.text = contato?.Sobrenome.toString()

        return v
    }
}