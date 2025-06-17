package edu.ucne.registro_tecnicos.data.di

import android.content.Context
import androidx.room.Room
import edu.ucne.registro_tecnicos.data.local.database.TecnicosDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTecnicosDb(
        @ApplicationContext appContext: Context
    ): TecnicosDb {
        return Room.databaseBuilder(
            appContext,
            TecnicosDb::class.java,
            "TecnicosDb"
        ).build()
    }

    @Provides
    fun provideTicketDao(db: TecnicosDb) = db.TicketDao()

    @Provides
    fun provideTecnicoDao(db: TecnicosDb) = db.tecnicosDao()

    @Provides
    fun provideTicketResponseDao(db: TecnicosDb) = db.ticketResponseDao()

    @Provides
    fun providePrioridadDao(db: TecnicosDb) = db.PrioridadDao()
}