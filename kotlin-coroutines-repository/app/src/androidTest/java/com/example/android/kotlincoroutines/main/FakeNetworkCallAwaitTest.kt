package com.example.android.kotlincoroutines.main

import com.example.android.kotlincoroutines.main.fakes.makeFailureCall
import com.example.android.kotlincoroutines.main.fakes.makeSuccessCall
import com.example.android.kotlincoroutines.util.FakeNetworkException
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FakeNetworkCallAwaitTest {

    @Test
    fun whenFakeNetworkCallSuccess_resumeWithResult() {
        runBlocking {
            val subject = makeSuccessCall("the title")

            val result = subject.await()

            Truth.assertThat(result).isEqualTo("the title")
        }
    }

    @Test(expected = FakeNetworkException::class)
    fun whenFakeNetworkCallFailure_throws() {
        runBlocking {
            val subject = makeFailureCall(FakeNetworkException("the error"))

            try {
                subject.await()
                Truth.assert_().fail()
            } catch (t: Throwable) {
                Truth.assertThat(t.message == "the error")
                throw t
            }
        }
    }
}
