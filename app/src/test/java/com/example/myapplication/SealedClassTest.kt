package com.example.myapplication

class SealedClassTest {
    sealed class Result {
        data class SuccessString(val value: String) : Result()
        data class SuccessInt(val value: Int) : Result()
        data class Error(val message: String) : Result()
    }

    fun fetchData(input: String?): Result = input
        ?.takeIf { it.isNotBlank() }
        ?.let { str ->
            str.toIntOrNull()
                ?.let { Result.SuccessInt(it) }
                ?: Result.SuccessString("Processed: $str")
        }
        ?: Result.Error(if (input == null) "Input is null" else "Input is blank")

    fun handleResult(result: Result) {
        when (result) {
            is Result.SuccessString -> println("Success (String): ${result.value}")
            is Result.SuccessInt -> println("Success (Int): ${result.value}")
            is Result.Error -> println("Error: ${result.message}")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val test = SealedClassTest()
            test.handleResult(test.fetchData("Kotlin"))  // Success (String): Processed: Kotlin
            test.handleResult(test.fetchData("123"))    // Success (Int): 123
            test.handleResult(test.fetchData(null))     // Error: Input is null
            test.handleResult(test.fetchData(""))       // Error: Input is blank
        }
    }
}