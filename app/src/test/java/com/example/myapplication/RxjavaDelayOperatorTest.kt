package com.example.myapplication

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxjavaDelayOperatorTest {
    @Test
    fun testDelay() {
        val testScheduler = TestScheduler()

        val testObserver = Observable.just(1L)
            .delay(3, TimeUnit.SECONDS, testScheduler)
            .test()

        // Before advancing time, it should not be complete
        testObserver.assertNotComplete()

        // Advance time by 3 seconds
        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)

        // Now it should emit the value and complete
        testObserver.assertValue(1L)
        testObserver.assertComplete()
    }

    @Test
    fun testTimer() {
        val testScheduler = TestScheduler()
        val testObserver: TestObserver<Long> = TestObserver()

        Observable.timer(3, TimeUnit.SECONDS, testScheduler)
            .subscribe(testObserver)

        testObserver.assertNotComplete()
        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)
        testObserver.assertValue(0L)
        testObserver.assertComplete()
    }

    @Test
    fun testInterval() {
        val testScheduler = TestScheduler()
        val testObserver: TestObserver<Long> = TestObserver()

        Observable.interval(1, TimeUnit.SECONDS, testScheduler)
            .take(3)
            .subscribe(testObserver)

        testObserver.assertNoValues()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValueCount(1)
        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        testObserver.assertValueCount(3)
        testObserver.assertComplete()
    }

    @Test
    fun testDefer() {
        val counter = intArrayOf(0)
        val deferredObservable = Observable.defer {
            counter[0]++
            Observable.just(counter[0])
        }

        Assert.assertEquals(0, counter[0].toLong())
        deferredObservable.test().assertValue(1)
        Assert.assertEquals(1, counter[0].toLong())
        deferredObservable.test().assertValue(2)
        Assert.assertEquals(2, counter[0].toLong())
    }

    @Test
    fun testRange() {
        Observable.range(5, 3)
            .test()
            .assertValues(5, 6, 7)
            .assertComplete()
    }
}