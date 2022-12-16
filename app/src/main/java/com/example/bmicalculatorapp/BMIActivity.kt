package com.example.bmicalculatorapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import java.io.File
import java.io.FileWriter
import com.google.gson.Gson

class BMIActivity : AppCompatActivity() {

    //All var/val
    lateinit var unitToggle: ToggleButton

    lateinit var saveBtn: Button
    lateinit var resetBtn: Button
    lateinit var switchPage: Button


    lateinit var unitLabel1: TextView
    lateinit var unitLabel2: TextView
    lateinit var BMILabel: TextView
    lateinit var rangeLabel: TextView

    lateinit var barRange: ProgressBar



    lateinit var heightInput: EditText
    lateinit var weightInput: EditText
    lateinit var ageInput: EditText
    lateinit var fullnameInput: EditText

    private val values = mutableListOf<listData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmiview)

        //All the views that are needed and found by ID
        unitToggle = findViewById(R.id.toggleButton);
        unitLabel1 = findViewById(R.id.unit1);
        unitLabel2 = findViewById(R.id.unit2);
        saveBtn = findViewById(R.id.savebtn);
        resetBtn = findViewById(R.id.resetbtn);
        heightInput = findViewById(R.id.hightInputBox);
        weightInput = findViewById(R.id.wightInputBox);
        ageInput = findViewById(R.id.ageInputBox);
        fullnameInput = findViewById(R.id.nameInputBox);
        BMILabel = findViewById(R.id.BMIlabel2);
        barRange = findViewById(R.id.progressBar);
        rangeLabel = findViewById(R.id.rangeLabel);
        switchPage = findViewById(R.id.switchPageButton);

        //Start up to set text
        StartUp();

        //the click listener
        unitToggle.setOnClickListener{ ChangeLabelsForUnit(); }
        saveBtn.setOnClickListener{ FillAndSaveBMIInfo(); }
        resetBtn.setOnClickListener{ ResetInput(); }
        switchPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            setContentView(R.layout.activity_main)
        }

    }

    private fun StartUp()
    {
        //On start up it'll pre set these.
        unitLabel1.text = "Foot"
        unitLabel2.text = "Pounds"
    }


    private fun ChangeLabelsForUnit()
    {
        //Sets the unit value on click and checks what i true
        //Also logging for testing
        if(unitToggle.isChecked)
        {
            Log.i("Toggle", "On");
            unitLabel1.text = "Meters"
            unitLabel2.text = "Kilograms"
        }
        else
        {
            Log.i("Toggle", "Off");
            unitLabel1.text = "Foot"
            unitLabel2.text = "Pounds"
        }

    }

    private fun GetBMI() : Float
    {

        //input value
        val wight: Float = weightInput.text.toString().toFloat();
        val Hight: Float = heightInput.text.toString().toFloat();

        var result : Float = 420.69f;
        if(unitToggle.isChecked)
        {// if toggled it'll used the normal metric stuff
            val square : Float  = (Hight * Hight);
            result = wight / square;

            Log.i("Hight", Hight.toString());
            Log.i("Wight", wight.toString());
            Log.i("result", result.toString());
        }
        else
        {// if not toggled it'll turn the imperial to metric using something called math woah
            var Hight_changed = (Hight * 0.3048f);
            var Wight_changed = (wight * 0.454f);
            val square : Float  = (Hight_changed * Hight_changed);
            result = Wight_changed / square;

            //Debug stuff
            Log.i("Hight_changed", Hight_changed.toString());
            Log.i("Wight_changed", Wight_changed.toString());
            Log.i("result", result.toString());

        }
        return result;
    }

    private fun FillAndSaveBMIInfo() {
        //Get the BMI float
        val BMI : Float = GetBMI();
        //Get the input data
        val weight: Float = weightInput.text.toString().toFloat();
        val name: String = fullnameInput.text.toString();
        val age: Int = ageInput.text.toString().toInt();
        //Switch data to what i need
        BMILabel.text = BMI.toString();
        rangeLabel.text = getCategory();
        barRange.progress = BMI.toInt();

        //declear the array
        val info = listData(weight.toString(), BMI.toString(), "12-16-2022", name, age)
        values.add(info)//Add the values
        val file = File(getExternalFilesDir(null), "BMIdata.json")//Get file or make file is needed
        val gson = Gson()
        FileWriter(file).use {
            gson.toJson(values, it)// write to file to save
        }
    }

    //This functions returns a string based on the level of the BMI value
    private fun getCategory() : String
    {
        var result : String = "Meh";
        val BMI : Float = GetBMI();
        if(BMI < 16)
            result = "Sever Thinness"
        else if(BMI > 16 && BMI < 17)
            result = "Moderate Thinness"
        else if(BMI > 17 && BMI < 18.5f)
            result = "Mild Thinness"
        else if(BMI > 18.5f && BMI < 25)
            result = "Normal"
        else if(BMI > 25 && BMI < 30)
            result = "Overwight"
        else if(BMI > 30 && BMI < 35)
            result = "Obese Class 1"
        else if(BMI > 35 && BMI < 40)
            result = "Obese Class 2"
        else if(BMI > 40)
            result = "Obese Class 3"
        else
            result = "Not known"

        return result;
    }

    //This just resets all the data back to normal
    private fun ResetInput()
    {
        weightInput.setText("");
        heightInput.setText("");
        ageInput.setText("");
        fullnameInput.setText("");
        BMILabel.text ="0.0";
        rangeLabel.text ="";
        barRange.progress = 0;
    }


}