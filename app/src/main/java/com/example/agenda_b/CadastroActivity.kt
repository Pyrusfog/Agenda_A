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
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_cadastro_main.*

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 100
    var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        ins_numero.addTextChangedListener(Mask.insert("(##)#####-####",ins_numero))

        btn_ins_contato.setOnClickListener(){
            val nome_contato = ins_nome.text.toString()
            val sobrenome_contato = ins_sobrenome.text.toString()
            val empresa_contato = ins_empresa.text.toString()
            val telefone = ins_numero.text.toString()
            val email = ins_email.text.toString()

            if(nome_contato.isNotEmpty() && sobrenome_contato.isNotEmpty() && telefone.isNotEmpty() && email.isNotEmpty()){
                if(!isEmailValid(email)){
                    ins_email.error = "Forneca um email valido"
                }else{
                    val contato = Contato(nome_contato,sobrenome_contato,empresa_contato,telefone,imageBitmap,email)
                    contatosGlobal.add(contato)
                    finish()
                }
            }else{
                if(nome_contato.isEmpty()){
                    ins_nome.error = "Preencha um Nome"
            }else{
                ins_nome.error = null
                }
                if (sobrenome_contato.isEmpty()){
                    ins_sobrenome.error = "Preencha um sobrenome"
                }else {
                    ins_sobrenome.error = null
                }
                if (telefone.isEmpty()){
                    ins_numero.error = "Forneca um telefone"
                }else {
                    ins_numero.error = null
                }
                if (email.isEmpty()){
                    ins_email.error = "Forneca um email"
                }else {
                    ins_email.error = null
                }
        }

    }
        img_cadastro.setOnClickListener(){
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
                    img_cadastro.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap!!,150,150,false))
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
