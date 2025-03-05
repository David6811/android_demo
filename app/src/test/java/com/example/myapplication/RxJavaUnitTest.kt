package com.example.myapplication

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import org.junit.Test

class RxJavaUnitTest {

    @Test
    fun `test Observable emits correct value`() {
        val observable = Observable.just(10)
        val testObserver = observable.test()

        // Verify that the Observable emits the expected value
        testObserver.assertValue(10)
        testObserver.assertComplete()
    }

    @Test
    fun `test Observable emits multiple values`() {
        val observable = Observable.just(1, 2, 3)

        val testObserver = observable.test()

        testObserver.assertValues(1, 2, 3)
        testObserver.assertComplete()
    }

    /**
     * never creates an observable that never emits values or completes,
     * while empty creates an observable that completes immediately without emitting any values.
     */
    @Test
    fun testObservableNeverEmits() {
        val observable = Observable.never<Int>()
        val testObserver = observable.test()

        testObserver.assertNoValues()
        testObserver.assertNotComplete()
    }

    @Test
    fun testObservableEmpty() {
        val observable = Observable.empty<Int>()
        val testObserver = observable.test()

        testObserver.assertNoValues()
        testObserver.assertComplete()
    }


}
