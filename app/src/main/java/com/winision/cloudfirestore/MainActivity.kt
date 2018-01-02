package com.winision.cloudfirestore

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var db: DocumentReference
    val gender = arrayOf("Male", "Female")
    val job = arrayOf("Developer", "Designer", "Digital Marketing", "KeyNote Speaking", "UX")
    val place = arrayOf("Los Angeles", "Los Vegas", "San Fransisco", "Chicago")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val updateBtn = findViewById<View>(R.id.updateBtn) as Button
        val queryBtn = findViewById<View>(R.id.queryBtn) as Button
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
        db = FirebaseFirestore.getInstance().document("Database/People")
        updateBtn.setOnClickListener {
            view: View? -> update()
        }
        queryBtn.setOnClickListener {
            view: View? -> startActivity(Intent(this, query::class.java))
        }
    }
    private fun update () {
        val nameTxt = findViewById<View>(R.id.nameTxt) as EditText
        val favFoodTxt = findViewById<View>(R.id.favFood) as EditText
        val favColorTxt = findViewById<View>(R.id.favColor) as EditText
        val ageTxt = findViewById<View>(R.id.ageTxt) as EditText

        val name = nameTxt.text.toString().trim()
        val favFood = favFoodTxt.text.toString().trim()
        val favColor = favColorTxt.text.toString().trim()
        val dreamJob = jobSpin.selectedItem.toString().trim()
        val dreamPlace = placeSpin.selectedItem.toString().trim()
        val age = ageTxt.text.toString().trim()
        val gender = genderSpin.selectedItem.toString().trim()

        if (!name.isEmpty() && !favColor.isEmpty() && !age.isEmpty() && !favFood.isEmpty() && !dreamJob.isEmpty() && !dreamPlace.isEmpty()) {
            val items = HashMap<String, Any>()
            items.put("food", favFood)
            items.put("color", favColor)
            items.put("job", dreamJob)
            items.put("place", dreamPlace)
            items.put("age", age)
            items.put("gender", gender)
            try {
                db.collection("Users").document(name).set(items).addOnCompleteListener {
                    task: Task<Void> -> if (task.isSuccessful) {
                    Toast.makeText(this, "Updated Sucessfully :)", Toast.LENGTH_LONG).show()
                }
                }.addOnFailureListener {
                    exception: Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "Fill all the fields :|", Toast.LENGTH_LONG).show()
        }
    }
}
