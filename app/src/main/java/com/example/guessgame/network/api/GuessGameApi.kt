package com.example.guessgame.network.api

import com.example.guessgame.data.TopPlayerRequest
import com.example.guessgame.data.GameResultResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface GuessGameApi {

    interface Game {

        companion object {
            const val SUFFIX_URL = "test/games"
        }

        /**
         * Get the game msg result
         *
         * @return [Observable][GameResultResponse]
         */
        @POST
        fun getGameMsgResult(
            @Url url: String = SUFFIX_URL,
            @Body topPlayerRequest: TopPlayerRequest?
        ): Single<GameResultResponse>


    }

}