package com.example.myapplication.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

// Create a class that extends Worker
class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        uploadImages()

        // Indicate whether the work finished successfully with the Result
        return Result.success()
        /**
         * The Result returned from doWork() informs the WorkManager service whether the work succeeded and, in the case of failure, whether or not the work should be retried.
         *
         * Result.success(): The work finished successfully.
         * Result.failure(): The work failed.
         * Result.retry(): The work failed and should be tried at another time according to its retry policy.
         */
    }

    private fun uploadImages() {
        println("Uploading images...")
    }
}

