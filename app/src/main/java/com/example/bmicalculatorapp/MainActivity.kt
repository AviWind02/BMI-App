package com.example.bmicalculatorapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import java.io.File
import java.io.FileReader


class MainActivity : AppCompatActivity() {


    lateinit var btn: Button

    lateinit var g_Adapter: Adapter

    var itemsListed = mutableListOf<listData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// read the contents of the file into a String
        val file = File(getExternalFilesDir(null), "BMIdata.json")
        val json = FileReader(file).use {
            it.readText()
        }
        // convert the JSON string to a JsonArray
        val gson = Gson()
        val array = gson.fromJson(json, JsonArray::class.java)

        val values = mutableListOf<listData>()
        for (element in array) {

            val Weight = element.asJsonObject["Weight"]?.asString
            val BMI = element.asJsonObject["BMI"]?.asString
            val Date = element.asJsonObject["Date"]?.asString
            val Name = element.asJsonObject["Name"]?.asString
            val age = element.asJsonObject["age"]?.asInt

            val info = listData(Weight, BMI, Date, Name, age)
            values.add(info)
        }


        g_Adapter = Adapter(values)
        val recyclerView: RecyclerView = findViewById(R.id.listedView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = g_Adapter

        btn = findViewById(R.id.testbtn)

        btn.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
            setContentView(R.layout.bmiview)



        }

    }
}