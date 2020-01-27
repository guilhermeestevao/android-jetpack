package dev.guilherme.dog.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.guilherme.dog.model.DogBreed

class DetailViewModel: ViewModel() {

    var dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(){
        val dog = DogBreed("1", "Dog 1", "15", "", "", "", "")
        dogLiveData.value = dog
    }
}