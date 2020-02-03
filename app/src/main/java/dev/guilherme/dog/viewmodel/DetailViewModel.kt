package dev.guilherme.dog.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.guilherme.dog.model.DogBreed
import dev.guilherme.dog.model.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {

    var dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(uuid: Int){
        launch {
            val dog = DogDatabase(getApplication()).daoDog().getDog(uuid)
            dogLiveData.value = dog
        }
    }
}