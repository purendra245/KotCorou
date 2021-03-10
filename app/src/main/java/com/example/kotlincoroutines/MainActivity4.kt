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
import kotlin.jvm.internal.MagicApiIntrinsics
import kotlin.system.measureTimeMillis

class MainActivity4 : AppCompatActivity() {

    private val RESULT1 = "RESULT1"
    private val RESULT2 = "RESULT2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener{
            fakeApiRequest1()
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

    private suspend fun getResult2FromApi():String{
        delay(1700)
        return RESULT2
    }


    private fun fakeApiRequest1(){

        val start = System.currentTimeMillis()

        val job = CoroutineScope(IO).launch {

            val job1 = launch {
                val time1=  measureTimeMillis {
                    println("debug launching job1 in thread${Thread.currentThread().name}")
                    val result1 =  getResult1FromApi()
                    setTextDataOnMain("got$result1")

                }
                println("debug completed in $time1")
            }

            val job2 = launch {
                val time2=  measureTimeMillis {
                    println("debug launching job2 in thread${Thread.currentThread().name}")
                    val result2 =  getResult2FromApi()
                    setTextDataOnMain("got$result2")

                }
                println("debug completed in $time2")
            }


        }

        job.invokeOnCompletion {
            println("total completed in ${System.currentTimeMillis()-start}")

        }
    }



    private fun fakeApiRequest2(){

        val start = System.currentTimeMillis()

        val job = CoroutineScope(IO).launch {


            val executionTime =  measureTimeMillis {

                val result1 : Deferred<String> = async {
                    println("debug launching job1 in thread${Thread.currentThread().name}")
                     getResult1FromApi()

                }

                val result2 : Deferred<String> = async {
                    println("debug launching job2 in thread${Thread.currentThread().name}")
                    getResult2FromApi()

                }


                setTextDataOnMain("got${result1.await()}")
                setTextDataOnMain("got${result2.await()}")


            }

            println("total completed in $executionTime")

        }

        job.invokeOnCompletion {
            println("total completed in ${System.currentTimeMillis()-start}")

        }
    }

}