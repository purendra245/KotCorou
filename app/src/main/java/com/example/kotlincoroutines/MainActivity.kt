package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val result:String = "Result1"
    private val result2:String = "Result2"
    private val  JOB_Interval = 10;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener{
            // IO,Main,Default - long task
            CoroutineScope(Dispatchers.IO).launch {
                getFakeApi()
            }

        }

    }


    private suspend fun getFakeApi(){
        val result = getResultFrom1API()
        print("result1 is ===${result}")

        withContext(Dispatchers.Main){
            setTextOnMainThread(result)
        }

        val result2 = getResultFrom2API()
        print("result2 is ===${result2}")

        CoroutineScope(Dispatchers.Main).launch {
            setTextOnMainThread(result2)
        }


    }




    private fun setTextOnMainThread(rresult: String){
        textView.text = textView.text.toString()+"\n"+rresult
    }


    private suspend fun getResultFrom1API():String{
        logThread("getresult from mehtod 1")
        delay(1000)
        return result
    }
    private suspend fun getResultFrom2API():String{
        logThread("getresult from mehtod 2")
        delay(1000)
        return result2
    }


    private fun logThread(method:String){
        println("debug${method}: ${Thread.currentThread().name}")
    }
}