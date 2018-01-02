package com.winision.cloudfirestore

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class query : AppCompatActivity() {

    lateinit var db: DocumentReference
    lateinit var nameRef: CollectionReference
    val gender = arrayOf("Male", "Female")
    val job = arrayOf("Developer", "Designer", "Digital Marketing", "KeyNote Speaking", "UX")
    val place = arrayOf("Los Angeles", "Los Vegas", "San Fransisco", "Chicago")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)
        db = FirebaseFirestore.getInstance().document("Database/People")
        val searchBtn = findViewById<View>(R.id.searchBtn) as Button
        nameRef = db.collection("Users")
        val genderSpin = findViewById<View>(R.id.genderSpin) as Spinner
        val jobSpin = findViewById<View>(R.id.jobSpin) as Spinner
        val placeSpin = findViewById<View>(R.id.placeSpin) as Spinner
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpin!!.setAdapter (genderAdapter)
        val jobAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, job)
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jobSpin!!.setAdapter (jobAdapter)
        val placeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, place)
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        placeSpin!!.setAdapter (placeAdapter)
        searchBtn.setOnClickListener {
            view: View? -> search()
        }
    }
    private fun search() {
        val ageTxt = findViewById<View>(R.id.ageTxt) as EditText
        val age = ageTxt.text.toString().trim()
        val job = jobSpin.selectedItem.toString().trim()
        val gender = genderSpin.selectedItem.toString().trim()
        val place = placeSpin.selectedItem.toString().trim()
        if (!age.isEmpty() || age.isEmpty()) {
            simpleQuery(age, job, gender, place)
        }else {
            Toast.makeText(this, "Enter your query values :|", Toast.LENGTH_LONG).show()
        }
    }
    private fun simpleQuery (age: String, job: String, gender: String, place: String) {
        val disp = findViewById<View>(R.id.dispTxt) as TextView
        val query: Query = nameRef.whereEqualTo("job", job)
        query.addSnapshotListener {
            querySnapshot, firebaseFirestoreException -> disp.text = querySnapshot.size().toString()
        }
    }
}



