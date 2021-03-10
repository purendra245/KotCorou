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
import kotlin.system.measureTimeMillis

class MainActivity6 : AppCompatActivity() {


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
        CoroutineScope(Main).launch{
            println("Thread name ${Thread.currentThread().name}")
            //delay(3000)
            //Thread.sleep(3000)

//            for ( i in 1..100000){
//                launch {
//                    println("This is network request === ")
//                    delay(3000)
//                    println("This is network request ++ ")
//                }
//            }

          //  delay(3000)
        }
    }



}