package com.example.desafioandroideventos.data.network.events

import org.junit.Before
import org.junit.Test

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*

class CheckinSenderTest {

    // region constants

    // endregion constants

    // region helper fields

    // endregion helper fields

    lateinit var SUT: CheckinSender

    @Before
    fun setup() {
        SUT = CheckinSender()

    }

    // if checkin success - success returned
    @Test
    fun checkin_success_successReturned() {
        val result = SUT.checkin()
        assertThat(result.status, `is`(EventsFetcher.Status.SUCCESS))
    }

    // if checkin fails - failure returned
    @Test
    fun checkin_failure_failureReturned() {
        val result = SUT.checkin()
        assertThat(result.status, `is`(EventsFetcher.Status.FAILURE))
    }
    // if checkin network error - network error returned

    // region helper methods

    // endregion of helper methods

    // region for helper classes 

    // endregion for helper classes

}