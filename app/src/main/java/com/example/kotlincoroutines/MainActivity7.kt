package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main1.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
import kotlin.jvm.internal.MagicApiIntrinsics
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MainActivity7 : AppCompatActivity() {


    private var count = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
        button.setOnClickListener{
           textView.text = (++count).toString()
        }

    }


    private  fun main(){

        // # Job 1
        CoroutineScope(Main).launch {

            println("Starting job in thread ${Thread.currentThread().name}")

            val result1 = getResult()
            println("Result1 ${result1}")
            val result2 = getResult()
            println("Result2 ${result2}")
            val result3 = getResult()
            println("Result3 ${result3}")
            val result4 = getResult()
            println("Result4 ${result4}")
            val result5 = getResult()
            println("Result5 ${result5}")
            val result6 = getResult()
            println("Result6 ${result6}")

        }

        // # Job 1
        CoroutineScope(Main).launch {
            delay(1000)
            runBlocking {
                println("Blocking job in thread ${Thread.currentThread().name}")
                delay(3000)
                println("Done job in thread ${Thread.currentThread().name}")
//                val result7 = getResult()
//                println("============== ++++ ===============")
//                println("Result7 ${result7}")
//                println("=============== ++++ ==============")
            }
        }


    }

    private suspend fun getResult():Int{
        delay(1000)
        return Random.nextInt(0,100)
    }



}