package com.bahasain.ui.vocab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahasain.data.Result
import com.bahasain.data.VocabRepository
import com.bahasain.data.remote.request.TranslateRequest
import com.bahasain.data.remote.response.DataItemWord
import com.bahasain.data.remote.response.DataTranslate

class VocabViewModel(private val repository: VocabRepository) : ViewModel() {

    val translateResult = MutableLiveData<String?>()
    val posResult = MutableLiveData<String?>()

    fun getWordCategories(categories: String): LiveData<Result<List<DataItemWord?>?>> {
        return repository.getWordCategories(categories)
    }

    fun translate(wordTranslate: String) = repository.translate(TranslateRequest(wordTranslate))

    fun getWotd() = repository.getWotd()
}