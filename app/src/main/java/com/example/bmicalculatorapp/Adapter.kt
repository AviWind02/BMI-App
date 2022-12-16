package com.example.bmicalculatorapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Adapter(private val dataSet: MutableList<listData>):
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val infoBoxItems: TextView
        init {
            // Define click listener for the ViewHolder's View.
            infoBoxItems = view.findViewById(R.id.infobox)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.textrowitems, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.infoBoxItems.text =
            "Name: " + dataSet[position].Name +
            "\nWeight: " + dataSet[position].Weight +
            " | BMI: " + dataSet[position].BMI +
            "\nAge: " + dataSet[position].Age +
            "\nDate: " + dataSet[position].Date;

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}