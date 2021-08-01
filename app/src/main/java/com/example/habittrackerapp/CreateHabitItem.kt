package com.example.habittrackerapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.database.model.Habit
import com.example.habittrackerapp.utils.Calculations
import com.example.habittrackerapp.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.fragment_create_habit_item.*
import java.util.*

class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    var title = ""
    var description = ""
    var drawableSelected = 0
    var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    lateinit var habitViewModel: HabitViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        btn_confirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()
    }

    private fun addHabitToDB() {

        // Get text(title and description) from editTexts
        title = et_habitTitle.text.toString()
        description = et_habitDescription.text.toString()

        // Save time and Date in room DB
        timeStamp = "$cleanDate $cleanTime"

        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected ==0)) {
            val habit = Habit(0, title, description,timeStamp, drawableSelected)

            // if all the fields are filled -> add the habit in DB
            habitViewModel.addHabit(habit)
            Toast.makeText(context,"Habit created successfully!",Toast.LENGTH_SHORT).show()

            // navigate component back to HabitList Fragment (Home fragment)
            findNavController().navigate(R.id.action_createHabitItem_to_habitList)
        }
        else {
            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
        }
    }

    //set on click listeners for our data and time pickers
    private fun pickDateAndTime() {
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }

        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context,this,hour,minute,true).show()
        }
    }

    private fun drawableSelected() {
        iv_fastFoodSelected.setOnClickListener {
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_fastfood

            iv_teaSelected.isSelected = false
            iv_smokingSelected.isSelected = false
        }

        iv_teaSelected.setOnClickListener {
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.ic_tea

            iv_fastFoodSelected.isSelected = false
            iv_smokingSelected.isSelected = false
        }

        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.ic_smoking2

            iv_fastFoodSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }
    }

    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    //get the time set
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        cleanTime = Calculations.cleanTime(hourOfDay, minute)

        // show Time in Text of text time
        tv_timeSelected.text = "Time: $cleanTime"
    }

    //get the date set
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        cleanDate = Calculations.cleanDate(dayOfMonth,month,year)

        // show Date in Text of date
        tv_dateSelected.text = "Date: $cleanDate"
    }
}