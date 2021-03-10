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

class MainActivity3 : AppCompatActivity() {

    private val TOTAL_PROGRESS = 100
    private val PROGRESS_START = 0
    private val JOB_TIME = 4000
    private lateinit var job: CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        btnNext.setOnClickListener{

            if (!:: job.isInitialized){
                initJob()
            }
            progressBar.startJobOrCancelled(job)
        }

    }

    private fun initJob(){
        btnNext.text =  "Start Job#1"
        tvStatus.text = ""
        job = Job()
        job.invokeOnCompletion { it ->
            it?.message.let {
                var msg = it
                if(msg.isNullOrBlank()){
                    msg = "unknown cancellation error!"
                }
                print("$job was cancelled with reason $msg")
                showToast(msg)
            }
        }

        progressBar.max = TOTAL_PROGRESS
        progressBar.progress = PROGRESS_START
    }


    private fun setjobText(textStr:String){
        GlobalScope.launch(Main) {
            tvStatus.text = textStr
        }

    }


    fun showToast(text:String){
        GlobalScope.launch(Main) {
            Toast.makeText(this@MainActivity3,text,Toast.LENGTH_LONG).show()
        }

    }


    fun ProgressBar.startJobOrCancelled(job: Job){
            if(this.progress >0){
                resetJob()
            }else{
                btnNext.text =  "Cancel job #1"
                CoroutineScope(IO+job).launch {

                    for (i in PROGRESS_START.. TOTAL_PROGRESS){
                        delay((JOB_TIME/TOTAL_PROGRESS).toLong())
                        this@startJobOrCancelled.progress = i
                    }
                    setjobText("Job is complete")
                }
            }
    }

    private fun resetJob() {
        if(job.isActive || job.isCompleted){
            job.cancel(CancellationException("reseting Job"))
        }
        initJob()
    }


    // println("debug${method}: ${Thread.currentThread().name}")
}