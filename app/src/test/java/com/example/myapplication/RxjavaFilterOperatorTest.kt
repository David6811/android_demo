package com.example.myapplication

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxjavaFilterOperatorTest {
    @Test
    fun testSkip() {
        val observable = Observable.range(1, 5).skip(2)
        val testObserver = observable.test()
        testObserver.assertValues(3, 4, 5).assertComplete()
    }

    @Test
    fun testSkipLast() {
        val observable = Observable.range(1, 5).skipLast(2)
        val testObserver = observable.test()
        testObserver.assertValues(1, 2, 3).assertComplete()
    }

    @Test
    fun testTake() {
        val observable = Observable.range(1, 5).take(3)
        val testObserver = observable.test()
        testObserver.assertValues(1, 2, 3).assertComplete()
    }

    @Test
    fun testTakeLast() {
        val observable = Observable.range(1, 5).takeLast(2)
        val testObserver = observable.test()
        testObserver.assertValues(4, 5).assertComplete()
    }

    @Test
    fun testFirst() {
        val observable = Observable.range(1, 5).first(0).toObservable()
        val testObserver = observable.test()
        testObserver.assertValue(1).assertComplete()
    }

    @Test
    fun testLast() {
        val observable = Observable.range(1, 5).last(0).toObservable()
        val testObserver = observable.test()
        testObserver.assertValue(5).assertComplete()
    }

    @Test
    fun testDistinct() {
        val observable = Observable.just(1, 2, 2, 3, 3, 3, 4).distinct()
        val testObserver = observable.test()
        testObserver.assertValues(1, 2, 3, 4).assertComplete()
    }

    @Test
    fun testDistinctUntilChanged() {
        val observable = Observable.just(1, 2, 2, 3, 3, 4, 4, 4).distinctUntilChanged()
        val testObserver = observable.test()
        testObserver.assertValues(1, 2, 3, 4).assertComplete()
    }

    @Test
    fun testFilter() {
        val observable = Observable.range(1, 5).filter { x: Int -> x % 2 == 0 }
        val testObserver = observable.test()
        testObserver.assertValues(2, 4).assertComplete()
    }

//    @Test
//    fun testOfType() {
//        val observable: Observable<Number> = Observable.just(1, 2.5, 3, 4.7, 5).ofType<Any>(
//            Int::class.java
//        )
//        val testObserver = observable.test()
//        testObserver.assertValues(1, 3, 5).assertComplete()
//    }

    @Test
    fun testFirstElement() {
        val observable = Observable.range(1, 5).firstElement().toObservable()
        val testObserver = observable.test()
        testObserver.assertValue(1).assertComplete()
    }

    @Test
    fun testLastElement() {
        val observable = Observable.range(1, 5).lastElement().toObservable()
        val testObserver = observable.test()
        testObserver.assertValue(5).assertComplete()
    }

    @Test
    fun testTimeout() {
        val observable = Observable.just(1, 2, 3)
            .delay(2, TimeUnit.SECONDS, Schedulers.io()) // Ensure delay happens on a different thread
            .timeout(1, TimeUnit.SECONDS)

        val testObserver = observable.test()

        testObserver.awaitDone(3, TimeUnit.SECONDS) // Ensure we wait long enough for timeout to occur
        testObserver.assertError(java.util.concurrent.TimeoutException::class.java)
    }

    @Test
    fun testDebounce() {
        // 创建一个 Observable，发射 1, 2, 3，每个事件间隔 100ms
        val observable = Observable.create<Int> { emitter ->
            emitter.onNext(1) // 发射 1
            Thread.sleep(100) // 等待 100ms
            emitter.onNext(2) // 发射 2
            Thread.sleep(100) // 等待 100ms
            emitter.onNext(3) // 发射 3
            Thread.sleep(300) // 等待 300ms，确保 debounce 时间足够
            emitter.onComplete() // 完成
        }
            .debounce(100, TimeUnit.MILLISECONDS) // 设置 debounce 时间为 200ms

        // 创建 TestObserver 并订阅
        val testObserver = observable.test()

        // 等待 Observable 完成
        testObserver.awaitDone(1, TimeUnit.SECONDS)

        // 验证结果
        testObserver.assertValues(3) // 只期望最后一个值 3
        testObserver.assertComplete() // 验证 Observable 完成
    }

    @Test
    fun testDebounce2() {
        // 创建一个 Observable，发射 1, 2, 3，每个事件间隔 100ms
        val observable = Observable.create<Int> { emitter ->
            emitter.onNext(1) // 发射 1
            Thread.sleep(100) // 等待 100ms
            emitter.onNext(2) // 发射 2
            Thread.sleep(100) // 等待 100ms
            emitter.onNext(3) // 发射 3
            Thread.sleep(300) // 等待 300ms，确保 debounce 时间足够
            emitter.onComplete() // 完成
        }
            .debounce(90, TimeUnit.MILLISECONDS) // 设置 debounce 时间为 200ms

        // 创建 TestObserver 并订阅
        val testObserver = observable.test()

        // 等待 Observable 完成
        testObserver.awaitDone(1, TimeUnit.SECONDS)

        // 验证结果
        testObserver.assertValues(1,2, 3) // 只期望最后一个值 3
        testObserver.assertComplete() // 验证 Observable 完成
    }


    @Test
    fun testThrottleWithTimeout() {
        // 创建一个 Observable，发射 1, 2, 3，每个事件间隔 100ms
        val observable = Observable.create<Int> { emitter ->
            emitter.onNext(1) // 发射 1
            Thread.sleep(100) // 等待 100ms
            emitter.onNext(2) // 发射 2
            Thread.sleep(100) // 等待 100ms
            emitter.onNext(3) // 发射 3
            Thread.sleep(300) // 等待 300ms，确保 throttleWithTimeout 时间足够
            emitter.onComplete() // 完成
        }
            .throttleWithTimeout(200, TimeUnit.MILLISECONDS) // 设置 throttleWithTimeout 时间为 200ms

        // 创建 TestObserver 并订阅
        val testObserver = observable.test()

        // 等待 Observable 完成
        testObserver.awaitDone(1, TimeUnit.SECONDS)

        // 验证结果
        testObserver.assertValues(3) // 只期望最后一个值 3
        testObserver.assertComplete() // 验证 Observable 完成
    }



}