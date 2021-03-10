package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main1.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.NonCancellable.cancel
import java.lang.Exception
import kotlin.jvm.internal.MagicApiIntrinsics
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MainActivity10 : AppCompatActivity() {


    private var count = 0;
    lateinit var parentJob:Job

    lateinit var  jobB: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
        button.setOnClickListener{
            parentJob.cancel()

        }

    }



    private  fun main(){

        parentJob = CoroutineScope(IO).launch(handler) {

            supervisorScope {

                //  =========   JOB A  ===========

                val jobA = launch {
                    val result =  getResult(1)
                    println("Result $result")
                }

                jobA.invokeOnCompletion {
                    if(it!=null){
                        println("Error getting result A  $it")
                    }
                }


                //  =========   JOB B  ===========

                jobB = launch(handler) {
                    val resultB =  getResult(2)
                    println("Result $resultB")
                }

                jobB.invokeOnCompletion {
                    if(it!=null){
                        println("Error getting result B  $it")
                    }
                }


                //  =========   JOB C  ===========

                val jobc = launch {
                    val resultc =  getResult(3)
                    println("Result $resultc")
                }

                jobc.invokeOnCompletion {
                    if(it!=null){
                        println("Error getting result C  $it")
                    }
                }


            }

            }



        parentJob.invokeOnCompletion {
            if(it!=null){
                println("Parent Error getting result is  $it")
            }else{
                println("Parent Job Successful")
            }
        }


    }


    private suspend fun getResult(number: Int):Int{
        delay(number* 500L)
        if(number ==2 ){
            throw Exception("Job not completed $number ")
            //jobB.cancel(CancellationException("Job not completed $number "))
//            throw CancellationException("Job not completed $number ")
        }
        return number*2
    }

    val handler = CoroutineExceptionHandler{_,exception->
        println("Exception thrown in one of the childer$exception")

    }



}