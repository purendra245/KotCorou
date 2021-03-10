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

class MainActivity8 : AppCompatActivity() {


    private var count = 0;
    lateinit var parentJob:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
        button.setOnClickListener{
            parentJob.cancel()

        }

    }

    suspend fun work(i:Int){
        delay(3000)
        println("work $i is done ${Thread.currentThread().name}")
    }


    private  fun main(){
        val startTime = System.currentTimeMillis()
        println("starting job im millis$startTime")
        parentJob = CoroutineScope(Main).launch {

//            launch {
//                work(1)
//                work(2)
//                work(3)
//            }




            GlobalScope.launch {
                work(1)

            }
            GlobalScope.launch {

                work(2)
                work(3)
            }
        }

        parentJob.invokeOnCompletion {
            if(it!=null){
                println("Job is cancelled after "+{System.currentTimeMillis()-startTime}+"ms")
            }else{
                println("Done in "+{System.currentTimeMillis()-startTime}+"ms")
            }
        }

    }

    private suspend fun getResult():Int{
        delay(1000)
        return Random.nextInt(0,100)
    }



}