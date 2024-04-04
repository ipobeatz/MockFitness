package com.android.mockfitness.data.datasource

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FitApiIntegration @Inject constructor(private val context: Context) {

    suspend fun readStepCountData(googleSignInAccount: GoogleSignInAccount): DataReadResponse =
        suspendCancellableCoroutine { continuation ->
            val startTime = getStartOfDayInMillis(2024, Calendar.MARCH, 25)
            val endTime = getEndOfDayInMillis(2024, Calendar.APRIL, 2)

            val readRequest = DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()

            Fitness.getHistoryClient(context, googleSignInAccount)
                .readData(readRequest)
                .addOnSuccessListener { response ->
                    val buckets = response.buckets
                    for (bucket in buckets) {
                        val dataSets = bucket.dataSets
                        for (dataSet in dataSets) {
                            for (dataPoint in dataSet.dataPoints) {
                                val steps = dataPoint.getValue(Field.FIELD_STEPS).asInt()
                                //TODO: Handle response and parse and return.
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->

                }
        }


    private fun getStartOfDayInMillis(year: Int, month: Int, day: Int): Long {
        return Calendar.getInstance().apply {
            set(year, month, day, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun getEndOfDayInMillis(year: Int, month: Int, day: Int): Long {
        return Calendar.getInstance().apply {
            set(year, month, day, 23, 59, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    }
}
