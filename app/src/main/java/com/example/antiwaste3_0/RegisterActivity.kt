package com.example.antiwaste3_0

import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener{
            onBackPressed()
        }

        btn_register.setOnClickListener{
            when{
                TextUtils.isEmpty(et_register_email.text.toString().trim{it<= ' '})->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_register_password.text.toString().trim{ it <= ' '})-> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_phone_number.text.toString().trim{ it <= ' '})->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter phone number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_username.text.toString().trim{ it <= ' '})-> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter username.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else->{
                    val email: String = et_register_email.text.toString().trim{ it<= ' '}
                    val password: String = et_register_password.text.toString().trim{ it<= ' '}
                    val phoneNumber: String = et_register_phone_number.text.toString().trim{ it<= ' '}
                    val username: String = et_register_username.text.toString().trim{ it<= ' '}

                    //Create instance and create a register a user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                        //if registration is successfully done
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            saveFireStore(email, phoneNumber, username)
                            Toast.makeText(
                                this@RegisterActivity,
                                "You are registered successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            /**
                             * Here new user is registered is automatically signed in so just sign out
                             * and send him to main screen with user id and email that user have used for registration
                             */

                            val intent =
                                Intent(this@RegisterActivity, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                            finish()
                        } else {
                            //If registering not successful then show error message
                            Toast.makeText(
                                this@RegisterActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    fun saveFireStore(email:String, phoneNumber: String, username: String){
        val database = FirebaseFirestore.getInstance()
        val user:MutableMap<String, Any>  = HashMap()
        user["email"] = email
        user["phoneNumber"] = phoneNumber
        user["username"] = username
        user["rewardPts"] = 0

        database.collection("users").document(email).set(user)

    }
}