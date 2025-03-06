package com.example.myapplication

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.functions.Function
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

internal class RxjavaOtherOperatorTest {
    @Test
    fun testCount() {
        val count = Observable.just("A", "B", "C").count()
        val testObserver = count.test()
        testObserver.assertValue(3L)
    }

    @Test
    fun testRepeat() {
        val testObserver = Observable.just("A")
            .repeat(3)
            .test()
        testObserver.assertValues("A", "A", "A")
    }

//    @Test
//    fun testRepeatWhen() {
//        val testObserver = Observable.just("A")
//            .repeatWhen(Function<Observable<Any?>, ObservableSource<*>> { completed: Observable<Any?> ->
//                completed.delay(
//                    100,
//                    TimeUnit.MILLISECONDS
//                )
//            })
//            .take(3)
//            .test()
//        testObserver.awaitDone(500, TimeUnit.MILLISECONDS)
//            .assertValueCount(3)
//            .assertValues("A", "A", "A")
//    }

    @Test
    fun testRetry() {
        val counter = AtomicInteger(0)
        val source = Observable.fromCallable<Int> {
            if (counter.incrementAndGet() < 3) {
                throw RuntimeException("Error")
            }
            1
        }.retry(2)

        val testObserver = source.test()
        testObserver.assertValue(1)
    }

    @Test
    fun testRetryUntil() {
        val counter = AtomicInteger(0)
        val source = Observable.fromCallable {
            if (counter.incrementAndGet() < 3) {
                throw RuntimeException("Error")
            }
            1
        }.retryUntil { counter.get() >= 3 }

        val testObserver = source.test()
        testObserver.assertValue(1)
    }

//    @Test
//    fun testRetryWhen() {
//        val counter = AtomicInteger(0)
//        val source = Observable.fromCallable<Int> {
//            if (counter.incrementAndGet() < 3) {
//                throw RuntimeException("Error")
//            }
//            1
//        }
//            .retryWhen(Function<Observable<Throwable?>, ObservableSource<*>> { errors: Observable<Throwable?> ->
//                errors.take(
//                    2
//                )
//            })
//
//        val testObserver = source.test()
//        testObserver.assertValue(1)
//    }
}
