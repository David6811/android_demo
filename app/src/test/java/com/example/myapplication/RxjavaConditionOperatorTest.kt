package com.example.myapplication

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxjavaConditionOperatorTest {
    @Test
    fun testAll() {
        val source = Observable.just(2, 4, 6, 8)
        val allEven = source.all { x: Int -> x % 2 == 0 }
        allEven.test().assertValue(true)
    }

    @Test
    fun testExists() {
        val source = Observable.just(1, 2, 3, 4)
        val exists = source.any { x: Int -> x == 3 }
        exists.test().assertValue(true)
    }

    @Test
    fun testTakeUntil() {
        val source = Observable.just(1, 2, 3, 4, 5).takeUntil { x: Int -> x > 3 }
        source.test().assertValues(1, 2, 3, 4)
    }

    @Test
    fun testTakeWhile() {
        val source = Observable.just(1, 2, 3, 4, 5).takeWhile { x: Int -> x < 4 }
        source.test().assertValues(1, 2, 3)
    }

    @Test
    /**
     * Do not emit anything until the second observable (Observable.just(1)) emits a value.
     */
    fun testSkipUntil() {
//        val source = Observable.just(1, 2, 3, 4, 5)
//            .skipUntil(Observable.just(1).delay(100, TimeUnit.MILLISECONDS))
//        source.test().assertNotComplete()
    }

    @Test
    fun testSkipWhile() {
        val source = Observable.just(1, 2, 3, 4, 5).skipWhile { x: Int -> x < 3 }
        source.test().assertValues(3, 4, 5)
    }

    @Test
    fun testSingle() {
        val single = Single.just(10)
        single.test().assertValue(10)
    }

//    @Test
//    fun testSingleOrDefault() {
//        val singleMaybe: Maybe<Int> = Maybe.empty<Any>().defaultIfEmpty(100)
//        singleMaybe.test().assertValue(100)
//    }

    @Test
    fun testContains() {
        val source = Observable.just(1, 2, 3, 4)
        val contains = source.contains(3)
        contains.test().assertValue(true)
    }

    @Test
    fun testIsEmpty() {
        val maybe = Maybe.empty<Int>()
        val isEmpty = maybe.isEmpty()
        isEmpty.test().assertValue(true)
    }

    @Test
    fun testDefaultIfEmpty() {
        val source = Observable.empty<Int>().defaultIfEmpty(10)
        source.test().assertValues(10)
    }

    @Test
    fun testSwitchIfEmpty() {
        val source = Observable.empty<Int>().switchIfEmpty(Observable.just(10))
        source.test().assertValues(10)
    }

    @Test
    /**
     * Since source2 starts emitting immediately, it wins the race, and source1 is ignored.
     */
    fun testAmb() {
        val source1 = Observable.just(1, 2, 3).delay(100, TimeUnit.MILLISECONDS)
        val source2 = Observable.just(4, 5, 6)
        val result = Observable.ambArray(source1, source2)
        result.test().assertValues(4, 5, 6)
    }

    @Test
    /**
     * Used when you have exactly two Observable sources.
     */
    fun testAmbWith() {
        val source1 = Observable.just(1, 2, 3).delay(100, TimeUnit.MILLISECONDS)
        val source2 = Observable.just(4, 5, 6)
        val result = source1.ambWith(source2)
        result.test().assertValues(4, 5, 6)
    }

    @Test
    fun testSequenceEqual() {
        val source1 = Observable.just(1, 2, 3)
        val source2 = Observable.just(1, 2, 3)
        val isEqual = Observable.sequenceEqual(source1, source2)
        isEqual.test().assertValue(true)
    }
}