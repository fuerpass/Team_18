package com.example.saveup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun login(view: View) {
        val email = findViewById<EditText>(R.id.login_email).text.toString()
        val password = findViewById<EditText>(R.id.login_password).text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = "https://saveup.weisl.cc/auth"

        val requestBody = "email=" + email + "&pw=" + password
        val stringReq : StringRequest =
                object : StringRequest(Method.POST, url,
                        Response.Listener { response ->
                            // response
                            val strResp = response.toString()
                            //Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                            Log.d("token", strResp)

                            val sharedPref = getSharedPreferences("User", Context.MODE_PRIVATE).edit()
                            sharedPref.putString("user_token", strResp)
                            sharedPref.apply()
                            val intent = Intent (this, ProfileActivity::class.java)
                            startActivity(intent)
                        },
                        Response.ErrorListener { error ->
                            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                            Log.d("token", "error => $error")
                        }
                ){
                    override fun getBody(): ByteArray {
                        return requestBody.toByteArray(Charset.defaultCharset())
                    }
                }
        queue.add(stringReq)

    }
}