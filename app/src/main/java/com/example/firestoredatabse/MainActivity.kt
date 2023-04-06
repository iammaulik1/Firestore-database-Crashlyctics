package com.example.firestoredatabse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.firestoredatabse.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.saveButton.setOnClickListener {

            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val age = binding.Age.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()


            saveFireStore(firstName,lastName,age,phoneNumber)
        }

        readFireStoreData()
    }

    private fun saveFireStore(firstName:String, lastName:String, age:String, phoneNumber:String){

        val db = FirebaseFirestore.getInstance()
        val user:MutableMap<String,Any> = HashMap()

        user["firstName"] = firstName
        user["lastName"] = lastName
        user["age"] = age
        user["phoneNumber"] = phoneNumber


        db.collection("users")

            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity , "Record Added Successfully" ,Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity , "Record Failed To Add " , Toast.LENGTH_SHORT).show()
            }

        readFireStoreData()

    }

    private fun readFireStoreData(){

        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .get()
            .addOnCompleteListener{
                val result :StringBuffer = StringBuffer()

                if (it.isSuccessful){
                    for (document in it.result!!){
                        result.append(document.data.getValue("firstName")).append(" , ")
                            .append(document.data.getValue("lastName")).append(" , ")
                            .append(document.data.getValue("age")).append(" , ")
                            .append(document.data.getValue("phoneNumber")).append("\n\n")
                            .append(document.data.getValue("email")).append("\n\n")
                    }

                    binding.textResult.setText(result)

                }
            }

    }

}

// if you want to run these app without getting crash than remove line 77(which appends email)
// ,i had used that error for testing crashlyctics in my project.