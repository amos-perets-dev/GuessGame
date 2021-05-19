package com.example.guessgame.managers.top_player

import android.util.Log
import com.example.guessgame.data.TopPlayer
import com.example.guessgame.data.TopPlayerRequest
import com.example.guessgame.data.UserProfile
import com.example.guessgame.guess_app.base.model_text.IText
import com.example.guessgame.guess_app.base.model_text.Text
import com.example.guessgame.repo.top_player.ITopPlayerRepo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm

class TopPlayerManager(
    private val topPlayerRepo: ITopPlayerRepo,
    private val topPlayerTemplate: String
) : ITopPlayerManager {

    private val userProfileAsync = topPlayerRepo
        .getUserProfileAsync()

    override fun getTopPlayerRequest(): Observable<TopPlayerRequest>? {
        return userProfileAsync
            .map { userProfile ->
                val userName = userProfile.userName
                val email = userProfile.email
                val guessesList = userProfile.guessesList

                val topPlayerRequest = TopPlayerRequest(userName, email, guessesList)
                topPlayerRequest
            }

    }

    override fun addTopPlayer(): Completable? {
       return userProfileAsync
            ?.flatMapCompletable { user ->
                val userName = user.userName
                val email = user.email
                val gameResult = user.guessesList.size

                return@flatMapCompletable topPlayerRepo.addTopPlayer(
                    TopPlayer(
                                id = java.util.UUID.randomUUID().toString(),
                                email = email,
                                name = userName,
                                gameResult = gameResult
                            )
                )
            }
    }

    override fun getTopPlayers(): Flowable<List<IText>>? {
        return topPlayerRepo
            .getTopPlayersAsync()
            ?.map {resultList ->
                resultList.mapIndexed { index, topPlayer ->
                    val gameResult = topPlayer.gameResult
                    val name = topPlayer.name
                    return@mapIndexed getTopPlayerText(gameResult, name, index + 1)
                }
            }
    }

    private fun getTopPlayerText(gameResult: Int, name: String, index: Int): IText {
        val text = "\t\t${String.format(topPlayerTemplate, index, name, gameResult)}"
        return Text(text)
    }

}