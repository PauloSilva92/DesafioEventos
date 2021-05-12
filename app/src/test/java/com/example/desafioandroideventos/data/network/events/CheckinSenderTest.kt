package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Checkin
import com.example.desafioandroideventos.data.network.EventsAPI
import com.example.desafioandroideventos.data.network.Status
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Before
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CheckinSenderTest {

    // region constants

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val gsonBuilder: GsonBuilder = GsonBuilder().setLenient()
    private val gson = gsonBuilder.create()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(EventsAPI::class.java)

    // endregion constants

    // region helper fields


    val checkin = Checkin(1, "Paulo", "paulo@teste.com")

    // endregion helper fields

    lateinit var SUT: CheckinSender

    @Before
    fun setup() {
        SUT = CheckinSender(api)
    }

    // if checkin success - success returned
    @Test
    fun checkin_success_successReturned() {
        success()
        val result = SUT.checkin(checkin)
        assertThat(result.status, `is`(Status.SUCCESS))
    }

    // if checkin fails - failure returned
    @Test
    fun checkin_failure_failureReturned() {
        failure()
        val result = SUT.checkin(checkin)
        assertThat(result.status, `is`(Status.FAILURE))
    }

    // if checkin network error - network error returned
    @Test
    fun checkin_networkError_networkErrorReturned() {
        networkError()
        val result = SUT.checkin(checkin)
        assertThat(result.status, `is`(Status.NETWORK_ERROR))
    }

    // region helper methods
    fun success() {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(200)
        })
    }

    fun failure() {
        mockWebServer.enqueue(MockResponse().apply {
            setResponseCode(404)
        })
    }

    private fun networkError() {
        mockWebServer.enqueue(MockResponse().apply {
            setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        })
    }
    // endregion of helper methods

    // region for helper classes

    // endregion for helper classes

}