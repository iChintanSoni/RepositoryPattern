package com.chintansoni.android.repositorypattern.util.smartrecyclerview

object Preconditions {
    fun checkNotNull(`object`: Any?, message: String) {
        if (`object` == null) {
            throw IllegalArgumentException(message)
        }
    }

    fun checkIfPositive(number: Int, message: String) {
        if (number <= 0) {
            throw IllegalArgumentException(message)
        }
    }
}