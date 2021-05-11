package com.example.desafioandroideventos.data.repositories


import com.example.desafioandroideventos.data.models.Event
import com.example.desafioandroideventos.data.network.EventsAPI
import com.example.desafioandroideventos.data.network.RetrofitConfig
import com.example.desafioandroideventos.data.network.events.EventsFetcher
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventRepository {
    private val api: EventsAPI = RetrofitConfig.getEventsAPI()
    private val eventsFetcher: EventsFetcher = EventsFetcher(api)


    fun getEvents(): Observable<List<Event>> = Observable.create<List<Event>> { emitter ->
        val eventsFetcherResult = eventsFetcher.fetchEvents()
        if (eventsFetcherResult.status == EventsFetcher.Status.SUCCESS) {
            emitter.onNext(eventsFetcherResult.events!!)
        }
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getEventById(idEvent: Long): Observable<Event> = Observable.create<Event> { emitter ->
        val eventByIdFetcherResult = eventsFetcher.fetchEventById(idEvent)
        if (eventByIdFetcherResult.status == EventsFetcher.Status.SUCCESS) {
            emitter.onNext(eventByIdFetcherResult.event!!)
        }
        emitter.onComplete()
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


}