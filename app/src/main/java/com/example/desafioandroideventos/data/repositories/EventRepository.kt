package com.example.desafioandroideventos.data.repositories


import com.example.desafioandroideventos.data.models.Checkin
import com.example.desafioandroideventos.data.models.Event
import com.example.desafioandroideventos.data.network.EventsAPI
import com.example.desafioandroideventos.data.network.RetrofitConfig
import com.example.desafioandroideventos.data.network.Status
import com.example.desafioandroideventos.data.network.events.CheckinSender
import com.example.desafioandroideventos.data.network.events.EventsFetcher
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class EventRepository {
    private val api: EventsAPI = RetrofitConfig.getEventsAPI()
    private val eventsFetcher: EventsFetcher = EventsFetcher(api)
    private val checkingSender: CheckinSender = CheckinSender(api)


    fun getEvents(): Observable<List<Event>> = Observable.create<List<Event>> { emitter ->
        val eventsFetcherResult = eventsFetcher.fetchEvents()
        if (eventsFetcherResult.status == Status.SUCCESS) {
            emitter.onNext(eventsFetcherResult.events!!)
        }
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getEventById(idEvent: Long): Observable<Event> = Observable.create<Event> { emitter ->
        val eventByIdFetcherResult = eventsFetcher.fetchEventById(idEvent)
        if (eventByIdFetcherResult.status == Status.SUCCESS) {
            emitter.onNext(eventByIdFetcherResult.event!!)
        }
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun checkin(checkin: Checkin): Observable<Status> = Observable.create<Status> { emitter ->
        val checkinResult = checkingSender.checkin(checkin)
        emitter.onNext(checkinResult.status)
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


}