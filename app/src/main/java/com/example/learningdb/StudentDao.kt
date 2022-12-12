package com.example.learningdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*

@Dao
interface StudentDao {

    //get all the students
    @Query("SELECT * FROM student_table ")
    fun getAll(): List<Student>

    //find student by roll number --limited to "1" even if more than one student has the same roll number
    @Query("SELECT * FROM student_table WHERE roll_no LIKE :roll LIMIT 1")
    fun findByRoll(roll: Int): Student


    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(student: Student)

    //delete specific student
    @Delete
     fun delete(student:Student)

     //delete all
     @Query("DELETE FROM student_table")
      fun deleteAll()


      @Query("UPDATE student_table SET first_name=:firstName,last_name=:lastName WHERE roll_no LIKE :roll")
       fun update(firstName : String, lastName : String, roll : Int)

}