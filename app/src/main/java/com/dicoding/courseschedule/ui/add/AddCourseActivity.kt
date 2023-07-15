package com.dicoding.courseschedule.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.title = getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        findViewById<ImageButton>(R.id.ib_start_time).setOnClickListener { showStartTimePicker() }
        findViewById<ImageButton>(R.id.ib_end_time).setOnClickListener { showEndTimePicker() }
    }

    private fun showStartTimePicker() {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "startTimePicker")
    }

    private fun showEndTimePicker() {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "endTimePicker")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == "startTimePicker") {
            findViewById<TextView>(R.id.tv_start_time).text = dateFormat.format(calendar.time)
        } else if (tag == "endTimePicker") {
            findViewById<TextView>(R.id.tv_end_time).text = dateFormat.format(calendar.time)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_insert -> {
                 // Do insert
                val courseName = findViewById<EditText>(R.id.ed_course_name).text.toString()
                val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val lecturer = findViewById<EditText>(R.id.ed_lecturer).text.toString()
                val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString()
                val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString()
                val note = findViewById<EditText>(R.id.ed_note).text.toString()

                viewModel.insertCourse(
                    courseName, day, startTime, endTime, lecturer, note
                )

                viewModel.saved.observe(this) {
                    val success = it.getContentIfNotHandled() ?: return@observe
                    showToast(success)
                }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(success: Boolean?) {
        val message = if (success == true) "Successfully added" else "Failed to add!"
        Toast.makeText(this@AddCourseActivity, message, Toast.LENGTH_SHORT).show()
    }
}