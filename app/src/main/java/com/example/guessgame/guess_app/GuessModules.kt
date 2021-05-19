package com.example.guessgame.guess_app

import android.content.Context
import com.example.guessgame.R
import com.example.guessgame.guess_app.configuration.GuessConfiguration
import com.example.guessgame.guess_app.configuration.IGuessConfiguration
import com.example.guessgame.managers.realm.IRealmManager
import com.example.guessgame.managers.realm.RealmManager
import com.example.guessgame.managers.top_player.ITopPlayerManager
import com.example.guessgame.managers.top_player.TopPlayerManager
import com.example.guessgame.managers.user.IUserProfileManager
import com.example.guessgame.managers.user.UserProfileManager
import com.example.guessgame.network.api.GuessGameApi
import com.example.guessgame.network.base.BaseNetworkManager
import com.example.guessgame.network.base.IBaseNetworkManager
import com.example.guessgame.repo.top_player.ITopPlayerRepo
import com.example.guessgame.repo.top_player.TopPlayerRepo
import com.example.guessgame.repo.user_profile.IUserProfileRepo
import com.example.guessgame.repo.user_profile.UserProfileRepo
import com.example.guessgame.screens.game.GameViewModel
import com.example.guessgame.screens.game.adapter.GuessesAdapter
import com.example.guessgame.screens.game.manager.GameManager
import com.example.guessgame.screens.game.manager.IGameManager
import com.example.guessgame.screens.history.HistoryViewModel
import com.example.guessgame.screens.history.adapter.HistoryAdapter
import com.example.guessgame.screens.main.MainViewModel
import com.example.guessgame.screens.registration.RegistrationViewModel
import com.example.guessgame.screens.registration.manager.IRegistrationItemsManager
import com.example.guessgame.screens.registration.manager.RegistrationItemsManager
import com.example.guessgame.screens.registration.model.items.EmailRegistrationItemImpl
import com.example.guessgame.screens.registration.model.items.NameRegistrationItemImpl
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class GuessModules {
    fun createModules(context: Context): List<Module> {

        val appModules = createAppModules(context)

        val registrationModule = createRegistrationModule(context)
        val gameModule = createGameModule(context)
        val historyModule = createHistoryModule(context)
        val mainModule = createMainModule(context)

        return listOf(appModules, registrationModule, gameModule, historyModule, mainModule)
    }

    private fun createHistoryModule(context: Context): Module {
        return module {
            factory { HistoryAdapter() }

            viewModel {
                HistoryViewModel(get())
            }

        }
    }

    private fun createAppModules(context: Context): Module {

        createRealm(context)
        return module {
            single<IRealmManager> { RealmManager(Realm.getDefaultInstance()) }
            factory<IGuessConfiguration> { GuessConfiguration() }

            single<IUserProfileRepo> {
                UserProfileRepo(get())
            }

            single<ITopPlayerRepo> {
                TopPlayerRepo(get())
            }

            single<ITopPlayerManager> {
                TopPlayerManager(get(), context.getString(R.string.game_screen_guess_item_template_text))
            }

            single<IBaseNetworkManager> {
                BaseNetworkManager()
            }

            single<IUserProfileManager> {
                UserProfileManager(get(), get<IGuessConfiguration>().getNumbersRange())
            }
        }
    }


    private fun createMainModule(context: Context): Module {
        return module {

            viewModel {
                MainViewModel(get(), get())
            }

        }

    }


    private fun createRegistrationModule(context: Context): Module {
        return module {

            factory<IRegistrationItemsManager> {

                RegistrationItemsManager(
                    NameRegistrationItemImpl(context.getString(R.string.registration_screen_name_field_title_text)),
                    EmailRegistrationItemImpl(context.getString(R.string.registration_screen_email_field_title_text))
                )
            }

            viewModel {
                RegistrationViewModel(get(), get(), get())
            }

        }
    }

    private fun createGameModule(context: Context): Module {
        return module {
            factory { GuessesAdapter() }

            single <IGameManager> {
                GameManager(
                    context.getString(R.string.game_screen_guess_item_template_text),
                    get(),
                    get<IGuessConfiguration>().getNumbersRange(),
                    get<IBaseNetworkManager>().buildRetrofit()
                        .create(GuessGameApi.Game::class.java),
                    get(),
                    context.getString(R.string.game_screen_success_guess_default_text),
                    context.getString(R.string.game_screen_higher_guess_default_text),
                    context.getString(R.string.game_screen_lower_guess_default_text),
                    context.getString(R.string.game_screen_correct_guess_default_text)
                )
            }

            viewModel {
                GameViewModel(get())
            }

        }
    }


    private fun createRealm(context: Context): Realm {

        Realm.init(context)

        val realmConfiguration = RealmConfiguration.Builder()
            .name("guess_db.realm")
            .encryptionKey(ByteArray(64))
            .schemaVersion(1)
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)

        return Realm.getDefaultInstance()

    }

}
