package com.example.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.jvm.internal.MagicApiIntrinsics

class MainActivity2 : AppCompatActivity() {

    private val result:String = "Result1"
    private val result2:String = "Result2"
    private val  JOB_Interval = 1900L;

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
         withContext(IO){
             val job = withTimeoutOrNull(JOB_Interval){
                 val result = getResultFrom1API()
                 print("result1 is ===${result}")
                 setTextOnMainThread(result)
                 val result2 = getResultFrom2API()
                 print("result2 is ===${result2}")
                 setTextOnMainThread(result2)
             }
             if(job ==null){
                 setTextOnMainThread("job cancelled")
             }

        }
    }


    private suspend fun setTextOnMainThread(rresult: String){
        withContext(Dispatchers.Main){
            textView.text = textView.text.toString()+"\n"+rresult
        }

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