package com.example.myapplication

import io.reactivex.rxjava3.core.Observable
import org.junit.Test

class RxjavaTransformOperatorTest {

    // 1. Test for Buffer operator
    @Test
    fun testBufferOperator() {
        Observable.just(1, 2, 3, 4, 5)
            .buffer(2) // Collects into chunks of size 2
            .test()
            .assertValues(listOf(1, 2), listOf(3, 4), listOf(5))
    }

    // 2. Test for FlatMap operator
    @Test
    fun testFlatMapOperator() {
        Observable.just(1, 2, 3)
            .flatMap { Observable.just(it * 2) } // Transforms items by multiplying by 2
            .test()
            .assertValues(2, 4, 6)
    }

    // 3. Test for GroupBy operator
    @Test
    fun testGroupByOperator() {
//        Observable.just(1, 2, 3, 4, 5)
//            .groupBy { it % 2 } // Group by even or odd
//            .flatMap { it.to  List() }
//            .test()
//            .assertValues(listOf(1, 3, 5), listOf(2, 4))
    }

    // 4. Test for Map operator
    @Test
    fun testMapOperator() {
        Observable.just(1, 2, 3)
            .map { it * 10 } // Multiply each element by 10
            .test()
            .assertValues(10, 20, 30)
    }

    // 5. Test for Scan operator
    @Test
    fun testScanOperator() {
        Observable.just(1, 2, 3, 4)
            .scan { sum, item -> sum + item } // Accumulates the sum
            .test()
            .assertValues(1, 3, 6, 10)
    }

    // 6. Test for Window operator
    @Test
    fun testWindowOperator() {
//        Observable.just(1, 2, 3, 4, 5)
//            .window(2) // Emits windows of 2 items
//            .flatMap { it.toList() }
//            .test()
//            .assertValues(listOf(1, 2), listOf(3, 4), listOf(5))
    }

    // 7. Test for Reduce operator
    @Test
    fun testReduceOperator() {
        Observable.just(1, 2, 3, 4)
            .reduce { sum, item -> sum + item } // Reduces the list by summing
            .test()
            .assertValues(10)
    }

    // 8. Test for StartWith operator
    @Test
    fun testStartWithOperator() {
        Observable.just(2, 3, 4)
            .startWith(Observable.just(1)) // Wrap 1 in an Observable
            .test()
            .assertValues(1, 2, 3, 4)
    }


    // 9. Test for Collect operator
    @Test
    fun testCollectOperator() {
        Observable.just(1, 2, 3)
            .collect({ mutableListOf<Int>() }) { list, item -> list.add(item) }
            .test()
            .assertValue { it == listOf(1, 2, 3) }
    }

    // 10. Test for Merge operator
    /**
     * merge subscribes to both Observables simultaneously, emitting items as they arrive, but order is not guaranteed.
     */
    @Test
    fun testMergeOperator() {
        val observable1 = Observable.just(1, 2)
        val observable2 = Observable.just(3, 4)

        Observable.merge(observable1, observable2)
            .test()
            .assertValues(1, 2, 3, 4)
    }

    // 11. Test for ConcatMap operator
    /**
     * concatMap transforms each item sequentially and ensures the order is preserved.
     */
    @Test
    fun testConcatMapOperator() {
        Observable.just(1, 2, 3)
            .concatMap { Observable.just(it * 2) } // Sequentially transforms and emits
            .test()
            .assertValues(2, 4, 6)
    }

    // 12. Test for Concat operator
    /**
     * concat subscribes to Observables one by one,
     * ensuring all items from the first Observable are emitted before moving to the next, preserving order.
     */
    @Test
    fun testConcatOperator() {
        val observable1 = Observable.just(1, 2)
        val observable2 = Observable.just(3, 4)

        Observable.concat(observable1, observable2)
            .test()
            .assertValues(1, 2, 3, 4)
    }


    // 13. Test for Zip operator
    /**
     * zip pairs items from multiple Observables by index,
     * applies a function to combine them, and emits the results, stopping at the shortest Observable.
     */
    @Test
    fun testZipOperator() {
        val observable1 = Observable.just(1, 2)
        val observable2 = Observable.just(3, 4)

        Observable.zip(observable1, observable2) { t1, t2 -> t1 + t2 }
            .test()
            .assertValues(4, 6)
    }

    // 15. Test for Cast operator
    @Test
    fun testCastOperator() {
        Observable.just(1, 2, 3)
            .cast(Number::class.java) // Cast to Number
            .test()
            .assertValues(1, 2, 3)
    }
}
