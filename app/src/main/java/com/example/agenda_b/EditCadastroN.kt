package com.example.agenda_b

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit_cadastro_n.*

class EditCadastroN : AppCompatActivity() {
    val COD_IMAGE = 100
    var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cadastro_n)

        val int_position = getIntent().getIntExtra("posicao", 0)

        ins_edit_numero.addTextChangedListener(Mask.insert("(##)#####-####",ins_edit_numero))

        val conta = contatosGlobal[int_position]

        ins_edit_nome.setText(conta.Nome)
        ins_edit_sobrenome.setText(conta.Sobrenome)
        ins_edit_email.setText(conta.Email)
        ins_edit_empresa.setText(conta.Empresa)
        ins_edit_numero.setText(conta.Number)
        if (conta.Imagem!=null){
            img_edit_cadastro.setImageBitmap(Bitmap.createScaledBitmap(conta.Imagem,150,150,false))
        }


        btn_ins_edit_contato.setOnClickListener(){
            val nome_contato = ins_edit_nome.text.toString()
            val sobrenome_contato = ins_edit_sobrenome.text.toString()
            val empresa_contato = ins_edit_empresa.text.toString()
            val telefone = ins_edit_numero.text.toString()
            val email = ins_edit_email.text.toString()

            if(nome_contato.isNotEmpty() && sobrenome_contato.isNotEmpty() && telefone.isNotEmpty() && email.isNotEmpty()){
                if(!isEmailValid(email)){
                    ins_edit_email.error = "Forneca um email valido"
                }else{
                    val contato = Contato(nome_contato,sobrenome_contato,empresa_contato,telefone,imageBitmap,email)
                    contatosGlobal.set(int_position,contato)
                    val intent = Intent(this,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)

                }
            }else{
                if(nome_contato.isEmpty()){
                    ins_edit_nome.error = "Preencha um Nome"
                }else{
                    ins_edit_nome.error = null
                }
                if (sobrenome_contato.isEmpty()){
                    ins_edit_sobrenome.error = "Preencha um sobrenome"
                }else {
                    ins_edit_sobrenome.error = null
                }
                if (telefone.isEmpty()){
                    ins_edit_numero.error = "Forneca um telefone"
                }else {
                    ins_edit_numero.error = null
                }
                if (email.isEmpty()){
                    ins_edit_email.error = "Forneca um email"
                }else {
                    ins_edit_email.error = null
                }
            }

        }
        img_edit_cadastro.setOnClickListener(){
            abrirGaleria()
        }
    }
    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem de Perfil"), COD_IMAGE)
    }


    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val inputStream = data.getData()?.let { contentResolver.openInputStream(it) };
                imageBitmap = BitmapFactory.decodeStream(inputStream)
                if (imageBitmap != null){
                    img_edit_cadastro.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap!!,150,150,false))
                }
            }
        }
    }


    object Mask {
        fun unmask(s: String): String {
            return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
                .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
                .replace("[)]".toRegex(), "")
        }
        fun insert(mask: String, ediTxt: EditText): TextWatcher {
            return object : TextWatcher {
                var isUpdating = false
                var old = ""
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val str = unmask(s.toString())
                    var mascara = ""
                    if (isUpdating) {
                        old = str
                        isUpdating = false
                        return
                    }
                    var i = 0
                    for (m in mask.toCharArray()) {
                        if (m != '#' && str.length > old.length) {
                            mascara += m
                            continue
                        }
                        mascara += try {
                            str[i]
                        } catch (e: Exception) {
                            break
                        }
                        i++
                    }
                    isUpdating = true
                    ediTxt.setText(mascara)
                    ediTxt.setSelection(mascara.length)
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable) {}
            }
        }
    }
}
