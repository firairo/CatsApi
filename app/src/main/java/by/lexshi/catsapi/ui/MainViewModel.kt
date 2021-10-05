package by.lexshi.catsapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.lexshi.catsapi.PagingSource
import by.lexshi.catsapi.network.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorksViewModel @Inject constructor(
    private val apiService: Api
) : ViewModel() {

    // не применяй магические значения, выноси всегда в именованые константы
    val listData = Pager(PagingConfig(pageSize = 20, maxSize = 60)) {
        PagingSource(apiService)

    }.flow
        .cachedIn(viewModelScope)

}