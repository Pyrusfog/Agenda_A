    package com.example.agenda_b

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resume_contato.*
import kotlinx.android.synthetic.main.list_view_contact.view.*
import java.io.ByteArrayOutputStream

open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_meu_cartao.setOnClickListener(){
            val intent = Intent(this, CadastroMainActivity::class.java)
            startActivity(intent)
        }

        btn_add_contact.setOnClickListener(){
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        val contatoAdapter = ContatoAdapter(this)
        contatoAdapter.addAll(contatosGlobal)
        list_contacts.adapter = contatoAdapter

        list_contacts.setOnItemClickListener{ parent, view, position, id ->
            var conta = contatosGlobal[position]
            val intent = Intent(this, ResumeContato::class.java)
            val nome = conta.Nome.toString()
            val sobrenome = conta.Sobrenome.toString()
            val empresa = conta.Empresa.toString()
            val telefone = conta.Number.toString()
            val email = conta.Email.toString()

            //Tranformar Bitmap em ByteArray
            val imagem  = conta.Imagem
            if(imagem != null){
                val stream = ByteArrayOutputStream()
                imagem.compress(JPEG, 90, stream)
                val image = stream.toByteArray()
                intent.putExtra("Imagem", image)
            }

            intent.putExtra("Nome", nome)
            intent.putExtra("Sobrenome", sobrenome)
            intent.putExtra("Empresa", empresa)
            intent.putExtra("Number", telefone)
            intent.putExtra("Email", email)
            intent.putExtra("Posicao", position)
                startActivity(intent)

        }

    }

    override fun onResume() {
        super.onResume()
        Name_dono_cell.text = contatoMain.Nome
        Sobrenome_dono_cell.text = contatoMain.Sobrenome
        if (contatoMain.Imagem != null){
            val largura = contatoMain.Imagem
            photo_dono.setImageBitmap(Bitmap.createScaledBitmap(contatoMain.Imagem!!, 150, 150, false))
        }
        val contatoAdapter = ContatoAdapter(this)
        contatoAdapter.addAll(contatosGlobal)
        list_contacts.adapter = contatoAdapter
    }

}