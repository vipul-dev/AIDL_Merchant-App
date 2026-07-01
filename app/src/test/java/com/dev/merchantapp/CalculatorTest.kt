package com.dev.merchantapp

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculatorTest {

    @Test
    fun addTest() {

        val calculator = Calculator()

        assertEquals(5, calculator.add(2,3))
    }
}