package shah.rahul.mvihilt.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import shah.rahul.mvihilt.model.Blog
import shah.rahul.mvihilt.repository.MainRepository
import shah.rahul.mvihilt.util.DataState

class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel()
{
    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent) {

                is MainStateEvent.GetBlogEvents -> {
                    mainRepository.getBlog()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {
                    //NONE
                }
            }
        }
    }
}

sealed class MainStateEvent {

    object GetBlogEvents: MainStateEvent()

    object None: MainStateEvent()
}




