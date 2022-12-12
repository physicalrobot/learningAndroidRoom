package com.example.learningdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.learningdb.databinding.ActivityMainBinding
import com.example.roomdb.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private lateinit var appDb : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        binding.btnWriteData.setOnClickListener{

            writeData()

        }

        binding.btnReadData.setOnClickListener{

            readData()

        }

        binding.btnDeleteAll.setOnClickListener{
            GlobalScope.launch{
                appDb.studentDao().deleteAll()
            }
        }

        binding.btnUpdate.setOnClickListener{
            updateData()

        }
    }
private fun updateData(){
    val firstName = binding.etFirstName.text.toString()
    val lastName = binding.etLastName.text.toString()
    val rollNumber = binding.etRollNo.text.toString()

    if(firstName.isNotEmpty() &&  lastName.isNotEmpty() && rollNumber.isNotEmpty()){
        GlobalScope.launch(Dispatchers.IO){
            appDb.studentDao().update(firstName,lastName,rollNumber.toInt())
        }


        binding.etFirstName.text.clear()
        binding.etLastName.text.clear()
        binding.etRollNo.text.clear()

        Toast.makeText(this@MainActivity,"Successfully Updated",Toast.LENGTH_SHORT).show()



    }else{
        Toast.makeText(this@MainActivity,"Please enter data!!",Toast.LENGTH_SHORT).show()

    }

}

    private fun writeData(){
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNumber = binding.etRollNo.text.toString()

        if(firstName.isNotEmpty() &&  lastName.isNotEmpty() && rollNumber.isNotEmpty()){
            val student = Student(null, firstName, lastName, rollNumber.toInt())
            GlobalScope.launch(Dispatchers.IO){
                appDb.studentDao().insert(student)
            }


            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()

            Toast.makeText(this@MainActivity,"Successfully Written",Toast.LENGTH_SHORT).show()



        }else{
            Toast.makeText(this@MainActivity,"Please enter data!!",Toast.LENGTH_SHORT).show()

        }

    }


    private fun readData(){

        val rollNo = binding.etRollNoRead.text.toString()

        if (rollNo.isNotEmpty()){

            lateinit var student : Student

            GlobalScope.launch {

                student = appDb.studentDao().findByRoll(rollNo.toInt())
                Log.d("Robin Data",student.toString())
                displayData(student)

            }

        }else Toast.makeText(this@MainActivity,"Please enter the data", Toast.LENGTH_SHORT).show()

    }

    private suspend fun displayData(student: Student){

        withContext(Dispatchers.Main){

            binding.tvFirstName.text = student.firstName
            binding.tvLastName.text = student.lastName
            binding.tvRollNo.text = student.rollNo.toString()

        }

    }

    }


