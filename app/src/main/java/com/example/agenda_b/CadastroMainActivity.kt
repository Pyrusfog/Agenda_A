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
import kotlinx.android.synthetic.main.activity_main.*

class CadastroMainActivity : AppCompatActivity() {
    val COD_IMAGE = 100
    var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_main)

        ins_numero_main.addTextChangedListener(CadastroActivity.Mask.insert("(##)#####-####",ins_numero_main))

        btn_ins_contato_main.setOnClickListener() {
            val nome_contato_main = ins_nome_main.text.toString()
            val sobrenome_contato_main = ins_sobrenome_main.text.toString()

            if (nome_contato_main.isNotEmpty() && sobrenome_contato_main.isNotEmpty()) {
                val contato = Contato(nome_contato_main, sobrenome_contato_main,null,null,imageBitmap)
                contatoMain = contato
                finish()
            } else {
                if (nome_contato_main.isEmpty()) {
                    ins_nome_main.error = "Preencha um Nome"
                } else {
                    ins_nome_main.error = null
                }
                if (sobrenome_contato_main.isEmpty()) {
                    ins_sobrenome_main.error = "Preencha um sobrenome"
                } else {
                    ins_sobrenome_main.error = null
                }
            }
        }
        img_camera_main.setOnClickListener(){
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
                        img_camera_main.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap!!,150,150,false))
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

