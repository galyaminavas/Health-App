package com.example.user.healthcalendar

import android.content.ContentValues
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.user.healthcalendar.Database.DatabaseContract
import com.example.user.healthcalendar.Database.DbHelper


class EditEventActivity : AppCompatActivity() {
    var submit: Button? = null

    var etDoctor: Spinner? = null
    var etDate: EditText? = null
    var etTime: EditText? = null
    var etComment: EditText? = null

    var dbHelper: DbHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        dbHelper = DbHelper(this)

        //loadSpinnerData()
        //val list_of_ids = getAllDoctors()

        etDoctor = findViewById(R.id.event_doctor_spinner)
        etDate = findViewById(R.id.event_date)
        etTime = findViewById(R.id.event_time)
        etComment = findViewById(R.id.event_comment)

        val intent = intent

        val id = intent.getLongExtra("eventId",0)
        Log.i("this id = ", id.toString())

        if (id == 0.toLong()) {
            createEvent()
        }
        else
        {
            Log.i("this id = ", id.toString())
            //editEvent(id)
        }
    }

    //TODO : The code below was attempt to set selection of doctors in database to spinner
    //TODO : it does not work and the whole app restarts after clicking submit event button :)

    /*fun getAllDoctors(): ArrayList<String> {
        var idsList = ArrayList<String>();
        val selectQuery = "SELECT * FROM " + DatabaseContract.DoctorsColumns.TABLE_NAME

        val db = dbHelper!!.readableDatabase
        val cursor : Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                idsList.add(cursor.getString(1))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return idsList
    }

    fun loadSpinnerData() {
        val list_of_ids = getAllDoctors()
        val dataAdapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_of_ids)

        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        etDoctor?.setAdapter(dataAdapter);
    }*/

    /*fun SetSpinnerSelection(spinner: Spinner, array: Array<String>, text: String) {
        for (i in array.indices) {
            if (array[i] == text) {
                spinner.setSelection(i)
            }
        }
    }*/


    //TODO : and below everything is fine, I hope

    fun createEvent(){

        submit = findViewById<Button>(R.id.submit_event)
        submit!!.setOnClickListener(View.OnClickListener {
            submitNewEvent()
        })

        dbHelper = DbHelper(this)

    }

    private fun submitNewEvent() {

        var doctor = etDoctor!!.selectedItem.toString()
        var date = etDate!!.getText().toString()
        var time = etTime!!.getText().toString()
        var comment = etComment!!.getText().toString()

        var database = dbHelper!!.getWritableDatabase()

        var contentValues = ContentValues()

        contentValues.put(DatabaseContract.EventsColumns.DOCTOR_ID, doctor)
        contentValues.put(DatabaseContract.EventsColumns.DATE, date)
        contentValues.put(DatabaseContract.EventsColumns.TIME, time)
        contentValues.put(DatabaseContract.EventsColumns.COMMENT, comment)

        database.insert(DatabaseContract.EventsColumns.TABLE_NAME, null, contentValues)

        dbHelper?.close()

        super.onBackPressed()

    }
}
