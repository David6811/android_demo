package com.example.myapplication.demo

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Flowable

@SuppressLint("CheckResult")
fun main() {
    Flowable.just("Hello world").subscribe(::println)
}
