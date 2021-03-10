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

class MainActivity5 : AppCompatActivity() {

    private val RESULT1 = "RESULT1"
    private val RESULT2 = "RESULT2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            fakeApiRequest2()
        }

    }

    private fun setTextData(text:String){
        textView.text = textView.text.toString()+text
    }

    private suspend fun setTextDataOnMain(text:String){
        withContext(Main){
            textView.text = textView.text.toString()+text
        }

    }

    private suspend fun getResult1FromApi():String{
        delay(1000)
        return RESULT1
    }

    private suspend fun getResult2FromApi(result1: String):String{
        delay(1700)
        if(result1.equals("RESULT1")){
            return result1+RESULT2
        }
        throw CancellationException("something wrong")
    }

    private fun fakeApiRequest2(){

        val start = System.currentTimeMillis()

        val job = CoroutineScope(IO).launch {


            val executionTime =  measureTimeMillis {

                val result1  =  async {
                    println("debug launching job1 in thread${Thread.currentThread().name}")
                     getResult1FromApi()
                }.await()

                val result2 =  async {
                    println("debug launching job2 in thread${Thread.currentThread().name}")
                    try {
                        getResult2FromApi(result1)
                    }catch (e : Exception){
                        e.message
                    }


                }.await()

                println("RResult 2 is  $result2")


            }

            println("total completed in $executionTime")
        }

        job.invokeOnCompletion {
            println("total completed in ${System.currentTimeMillis()-start}")

        }
    }

}