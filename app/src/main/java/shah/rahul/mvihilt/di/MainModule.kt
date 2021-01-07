package shah.rahul.mvihilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import shah.rahul.mvihilt.repository.MainRepository
import shah.rahul.mvihilt.retrofit.BlogRetrofit
import shah.rahul.mvihilt.retrofit.NetworkMapper
import shah.rahul.mvihilt.room.BlogDao
import shah.rahul.mvihilt.room.CacheMapper
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object MainModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}