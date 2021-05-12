package com.example.desafioandroideventos.data.network.events

import com.example.desafioandroideventos.data.models.Event
import com.example.desafioandroideventos.data.network.EventsAPI
import com.example.desafioandroideventos.data.network.Status
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test


import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


class EventsFetcherTest {

    // region constants
    private val eventId: Long = 1
    private val successJsonName: String = "events-success-response.json"
    private val successByIdJsonName: String = "event-success-response.json"

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

    // endregion helper fields

    private lateinit var eventsFetcher: EventsFetcher

    @Before
    fun setup() {
        eventsFetcher = EventsFetcher(api)
    }

    // if fetch events succeeds - success returned
    @Test
    fun fetchEvents_success_successReturned() {
        success()
        val result = eventsFetcher.fetchEvents()
        assertThat(result.status, `is`(Status.SUCCESS))
    }

    // if fetch events succeeds - events returned
    @Test
    fun fetchEvents_success_eventsReturned() {
        success()
        val result = eventsFetcher.fetchEvents()
        assertThat(result.events, isA(List::class.java))
    }

    // if fetch events fails - failure returned
    @Test
    fun fetchEvents_failure_failReturned() {
        failure()
        val result = eventsFetcher.fetchEvents()
        assertThat(result.status, `is`(Status.FAILURE))
    }

    // if fetch events fails - no event returned
    @Test
    fun fetchEvents_failure_noEventReturned() {
        failure()
        val result = eventsFetcher.fetchEvents()
        assertThat(result.events, nullValue())
    }

    // if no network availabe - networtk error returned
    @Test
    fun fetchEvents_networkError_networkErrorReturned() {
        networkError()
        val result = eventsFetcher.fetchEvents()
        assertThat(result.status, `is`(Status.NETWORK_ERROR))
    }

    // if no network available - no event returned
    @Test
    fun fetchEvents_networkError_noEventReturned() {
        networkError()
        val result = eventsFetcher.fetchEvents()
        assertThat(result.events, nullValue())
    }

    // if fetch event by id succeeds - success returned
    @Test
    fun fetchEventById_success_sucessReturned() {
        successById()
        val result = eventsFetcher.fetchEventById(eventId)
        assertThat(result.status, `is`(Status.SUCCESS))
    }

    // if fetch event by id succeeds - event returned
    @Test
    fun fetchEventById_success_eventReturned() {
        successById()
        val result = eventsFetcher.fetchEventById(eventId)
        assertThat(result.event, instanceOf(Event::class.java))
    }

    // if fetch event fails - failure returned
    @Test
    fun fetchEventById_failure_failureReturned() {
        failure()
        val result = eventsFetcher.fetchEventById(eventId)
        assertThat(result.status, `is`(Status.FAILURE))
    }

    // if fetch event fails - no event returned
    @Test
    fun fetchEventById_failure_noEventReturned() {
        failure()
        val result = eventsFetcher.fetchEventById(eventId)
        assertThat(result.event, nullValue())
    }

    // if no network availabe - networtk error returned
    @Test
    fun fetchEventById_networkError_networkErrorReturned() {
        networkError()
        val result = eventsFetcher.fetchEventById(eventId)
        assertThat(result.status, `is`(Status.NETWORK_ERROR))
    }

    // if no network available - no event returned
    @Test
    fun fetchEventById_networkError_noEventReturned() {
        networkError()
        val result = eventsFetcher.fetchEventById(eventId)
        assertThat(result.event, nullValue())
    }

    // region helper methods
    private fun successById() {
        mockWebServer.enqueueResponse(successByIdJsonName, 200)
    }

    private fun success() {
        mockWebServer.enqueueResponse(successJsonName, 200)
    }

    private fun failure() {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        mockWebServer.enqueue(mockResponse)
    }

    private fun networkError() {
        val mockResponse = MockResponse()
        mockResponse.setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        mockWebServer.enqueue(mockResponse)
    }


    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Cache-Control", "no-cache")
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    // endregion of helper methods

    // region for helper classes 

    // endregion for helper classes

}