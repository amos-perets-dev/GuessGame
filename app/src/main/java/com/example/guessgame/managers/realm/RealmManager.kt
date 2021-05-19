package com.example.guessgame.managers.realm

import android.util.Log
import com.example.guessgame.data.UserProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults

class RealmManager(private val realm: Realm) : IRealmManager {

    override fun <E : RealmObject> insertAsync(`object`: E): Completable {
        return Completable.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                val objectFromRealm = realm.copyToRealmOrUpdate(`object`)
                if (objectFromRealm == null){
                    emitter.onError(Throwable())
                } else {
                    emitter.onComplete()
                }
            }
        }
    }

    override  fun <E : RealmObject> getDataAsync(type: Class<E>): Flowable<RealmResults<E>>? {
        return Realm.getDefaultInstance().where(type)
            .findAllAsync()
            .asFlowable()
    }

    override fun <E : RealmObject> getObject(type: Class<E>): Observable<E> {
       return Observable.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm: Realm ->
                val user = realm.where(type).findFirst()
                if (user != null) {
                    emitter.onNext(realm.copyFromRealm(user) as E)
                }
            }
        }
    }


    override fun setIsLoggedIn(isLoggedIn: Boolean): Completable =
        Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = getObjectAsync(realm, UserProfile::class.java)
                user.isLoggedIn = isLoggedIn
                emitter.onComplete()
            }
        }

    override fun addGuess(guess: Int, isFinishGame: Boolean): Completable {
      return  Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = getObjectAsync(realm, UserProfile::class.java)
                user.guessesList.add(guess)
                user.isFinishGame = isFinishGame
                emitter.onComplete()
            }
      }
    }

    override fun reInit(targetNumber: Int): Completable {
        return  Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = realm.where(UserProfile::class.java).findAll().firstOrNull()
                if (user != null){
                    if (user.isFinishGame){
                        user.targetNumber = targetNumber
                        user.guessesList.clear()
                        user.gameResult = -1
                        user.isFinishGame = false
                    }
                    emitter.onComplete()
                } else{
                    emitter.onComplete()
                }

            }
        }
    }

    override fun clearUser(): Completable {
        return  Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = realm.where(UserProfile::class.java).findAll()
                user.deleteAllFromRealm()
                emitter.onComplete()
            }
        }
    }

    private fun <E : RealmObject> getObjectAsync(realm: Realm, type: Class<E>) =
        realm.where(type).findFirstAsync()
}