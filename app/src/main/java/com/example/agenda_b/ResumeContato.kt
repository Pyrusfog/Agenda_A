package com.example.agenda_b

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_resume_contato.*


class ResumeContato : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_contato)


        val txt_nome = getIntent().getStringExtra("Nome")
        val txt_sobrenome = getIntent().getStringExtra("Sobrenome")
        val txt_Empresa = getIntent().getStringExtra("Empresa")
        val txt_Number = getIntent().getStringExtra("Number")
        val int_position = getIntent().getIntExtra("Posicao", 0)
        val txt_email = getIntent().getStringExtra("Email")


        //Transformar ByteArray em Bitmap
        val txt_Imagem = getIntent().getByteArrayExtra("Imagem")
        if (txt_Imagem != null){
            val Bitmap: Bitmap = BitmapFactory.decodeByteArray(txt_Imagem, 0, txt_Imagem.size)
            img_resume.setImageBitmap(android.graphics.Bitmap.createScaledBitmap(Bitmap, 150, 150, false))
        }

        name_resume.text = txt_nome
        sobrenome_resume.text = txt_sobrenome
        resume_empresa.text = txt_Empresa
        number_resume.text = txt_Number
        email_resume.text = txt_email



        btn_message.setOnClickListener {
            composeMmsMessage("", txt_Number)
        }

        btn_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("tel:" + txt_Number) // This ensures only SMS apps respond
            startActivity(intent)
        }

        btn_edit_contato.setOnClickListener(){
            val intent = Intent(this, EditCadastroN::class.java)
            intent.putExtra("posicao", int_position)
            startActivity(intent)
        }
        btn_deletar_cont.setOnClickListener(){
            contatosGlobal.removeAt(int_position)
            finish()
        }

//        notas_resume.addTextChangedListener(textWatcher)


//        notas_resume.addTextChangedListener(object : TextWatcher {
//            var isReflect = false
//            override fun beforeTextChanged(cs: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(cs: CharSequence, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(editable: Editable) {
//                if (isReflect) {
//                    // your code here
//                }else
//                isReflect = !isReflect //Here boolean value is changed after if loop
//            }
//        })
//         notas_resume.addTextChangedListener(textWatcher)

    }

//    val textWatcher = object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//            resumeNotas.add(0,s)
//
//        }
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            if (start == 12) {
//                Toast.makeText(applicationContext, "Maximum Limit Reached", Toast.LENGTH_SHORT).show()
//                teste_resume.text = resumeNotas[0]
//            }
//        }
//    }

        fun composeMmsMessage(message: String, number: String?) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("smsto: $number")  // This ensures only SMS apps respond
            intent.putExtra("sms_body", message)
            startActivity(intent)
        }
        fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


}