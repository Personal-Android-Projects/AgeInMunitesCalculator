package com.example.dateofbirthcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DAY_OF_MONTH

class MainActivity : AppCompatActivity() {

    //Set to null since it won't be initialized at this point
    var dateSelected : TextView? = null
    var yourTurn :TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DatePick : Button = findViewById(R.id.buttonDatePicker)
        dateSelected = findViewById(R.id.selectedDate)
        yourTurn = findViewById(R.id.remainder)

        DatePick.setOnClickListener{
            datePickerPressed()
        }
    }

    fun datePickerPressed(){

        //Getting the neccessary variables
        val myCalender = Calendar.getInstance()
        val Year = myCalender.get(Calendar.YEAR)
        val Month = myCalender.get(Calendar.MONTH)
        val Day = myCalender.get(DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{view,selectedYear,selectedMonth,selectedDay ->
                Toast.makeText(this,"$selectedDay/${selectedMonth + 1}/$selectedYear selected",Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateSelected?.text = selectedDate
                val sdf = SimpleDateFormat(
                    "dd/MM/yyyy",Locale.ENGLISH)
                val DateObject = sdf.parse(selectedDate)
                //time in minutes...by default the tie method returns time as milliseconds
                //Type safety...if not null execute
                DateObject?.let{
                    val timeInMinutes = DateObject.time / 60000
                    //Current time since 1970
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    //Diffrerence  btwn the two periods
                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time / 60000
                        val comparison = (currentDateInMinutes - timeInMinutes)
                        yourTurn?.text = comparison.toString()
                    }
                }
            },
            Year,
            Month,
            Day
            )
        //Ensure that future dates aren't processes
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}