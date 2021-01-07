package shah.rahul.mvihilt.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shah.rahul.mvihilt.model.Blog
import shah.rahul.mvihilt.retrofit.BlogRetrofit
import shah.rahul.mvihilt.retrofit.NetworkMapper
import shah.rahul.mvihilt.room.BlogDao
import shah.rahul.mvihilt.room.CacheMapper
import shah.rahul.mvihilt.util.DataState
import java.lang.Exception

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
){
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cacheBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheBlogs)))
        }catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}