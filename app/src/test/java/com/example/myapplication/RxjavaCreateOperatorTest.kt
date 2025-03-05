package com.example.myapplication

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Action
import org.junit.Test
import java.util.Optional
import java.util.concurrent.CompletableFuture

class RxjavaCreateOperatorTest {
    // Test just
    @Test
    fun testObservableJust() {
        val observable = Observable.just("A", "B", "C")
        val testObserver = observable.test()

        testObserver.assertValues("A", "B", "C")
        testObserver.assertComplete()
    }

    @Test
    fun testSingleJust() {
        val single = Single.just("A")
        val testObserver = single.test()

        testObserver.assertValues("A")
        testObserver.assertComplete()
    }

    // Test Observable.fromArray
    /**
     * fromArray is used to create an observable from a fixed array,
     * while fromIterable can create an observable from any type of iterable collection.
     */
    @Test
    fun testObservableFromArray() {
        val numbers = arrayOf(10, 20, 30)
        val observable = Observable.fromArray(*numbers)
        val testObserver = observable.test()

        testObserver.assertValues(10, 20, 30)
        testObserver.assertComplete()
    }

    // Test Observable.fromIterable
    @Test
    fun testObservableFromIterable() {
        val numbers: Iterable<Int> = mutableListOf(1, 2, 3, 4, 5)
        val observable = Observable.fromIterable(numbers)
        val testObserver = observable.test()

        testObserver.assertValues(1, 2, 3, 4, 5)
        testObserver.assertComplete()
    }

    // Test Observable.fromCallable
    /**
     * Callable returns a value when executed,
     * while Action is used for side-effects and does not return a value.
     */
    @Test
    fun testObservableFromCallable() {
        val observable = Observable.fromCallable { "Hello from Callable" }
        val testObserver = observable.test()

        testObserver.assertValue("Hello from Callable")
        testObserver.assertComplete()
    }

    // Test Observable.fromAction
    @Test
    fun testObservableFromAction() {
        val action: Action = Action { println("Action executed") }
        val completable: Completable = Completable.fromAction(action)
        completable.test().assertComplete()
    }

    // Test Observable.fromRunnable
    /**
     * Runnable is similar to Action in that it does not return a value and is used for side-effects,
     * but unlike Action, it can be run multiple times. It's typically used for tasks that need to be executed more than once,
     * such as performing a repetitive action or task in a background thread.
     */
    @Test
    fun testObservableFromRunnable() {
        val runnable = Runnable { println("Runnable executed") }
        val completable: Completable = Completable.fromRunnable(runnable)
        completable.test().assertComplete()
    }

    // Test Observable.fromMaybe
    @Test
    fun testObservableFromMaybe() {
        val maybe: Maybe<String> = Maybe.just("Maybe Value")
        val observable: Observable<String> = maybe.toObservable()

        // Assert that the value is emitted correctly
        observable.test()
            .assertValue("Maybe Value")
            .assertComplete()
            .assertNoErrors()

        // Test with an empty Maybe
        val emptyMaybe: Maybe<String> = Maybe.empty()
        val emptyObservable: Observable<String> = emptyMaybe.toObservable()

        // Assert that no value is emitted and it completes immediately
        emptyObservable.test()
            .assertNoValues()
            .assertComplete()
            .assertNoErrors()
    }

    // Test Observable.fromCompletable
    @Test
    fun testObservableFromCompletable() {
        val completable: Completable = Completable.complete()
        val observable: Observable<Any> = completable.toObservable()
        observable.test().assertComplete()
    }

    // Test Observable.fromFuture
    /**
     * Observable.fromFuture() is a method in RxJava
     * that converts a Future (like CompletableFuture) into an Observable, emitting the result once the future completes.
     */
    @Test
    fun testObservableFromFuture() {
        // Test with a completed future
        val future = CompletableFuture.completedFuture("Future Value")
        val observable = Observable.fromFuture(future)
        observable.test()
            .assertValue("Future Value")
            .assertComplete()
            .assertNoErrors()

        // Test with a future that completes with an exception
        val failedFuture = CompletableFuture<String>()
        failedFuture.completeExceptionally(RuntimeException("Future failed"))
        val failedObservable = Observable.fromFuture(failedFuture)
        // Assert that the observable correctly propagates the exception
        failedObservable.test()
            .assertNotComplete()
    }


    // Test Observable.fromOptional
    @Test
    fun testObservableFromOptional() {
        val optional = Optional.of("Optional Value")
        val observable = Observable.fromOptional(optional)
        observable.test().assertValue("Optional Value").assertComplete()
    }

    @Test
    fun testObservableFromPublisher() {
        // todo
    }


    // Test Observable.fromSingle
    @Test
    fun testObservableFromSingle() {
        val single: Single<String> = Single.just("Single Value")
        val observable: Observable<String> = single.toObservable()
        observable.test().assertValue("Single Value").assertComplete()
    }

    // Test Observable.fromStream
    @Test
    fun testObservableFromStream() {
        val observable = Observable.fromStream(mutableListOf(1, 2, 3, 4).stream())
        observable.test().assertValues(1, 2, 3, 4).assertComplete()
    }

    // Test Observable.fromSupplier
    @Test
    fun testObservableFromSupplier() {
        // todo
    }


}