package com.example.bmicalculatorapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {


    lateinit var btn: Button

    lateinit var g_Adapter: Adapter

    var itemsListed = mutableListOf<listData>(
        listData("170", "20", "12-11-22", "Avi", 20),
        listData("23", "30", "12-12-22", "Avi1", 21),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        g_Adapter = Adapter(itemsListed)
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