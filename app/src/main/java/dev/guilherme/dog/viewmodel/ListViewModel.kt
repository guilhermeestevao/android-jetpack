package dev.guilherme.dog.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.guilherme.dog.model.DogBreed
import dev.guilherme.dog.model.DogDatabase
import dev.guilherme.dog.model.DogsApiService
import dev.guilherme.dog.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application): BaseViewModel(application) {

    private val prefsHelper =  SharedPreferencesHelper()
    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()
    private val refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){

        val updateTime = prefsHelper.getUdateTime()

        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }

    }

    private fun fetchFromDatabase(){
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).daoDog().getAllDogs()
            dogsRetrived(dogs)
            Toast.makeText(getApplication(), "Dogs retrived from database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>(){

                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogsLocaly(dogList)
                        Toast.makeText(getApplication(), "Dogs retrived from endpoint", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun dogsRetrived(dogList: List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocaly(dogList: List<DogBreed>) {
        launch {
            val dao = DogDatabase(getApplication()).daoDog()
            dao.deleteAllDogs()
            val result = dao.insertAll(*dogList.toTypedArray())
            val list = dogList.mapIndexed { index, dogBreed ->
                dogBreed.uuid = result[index].toInt()
                dogBreed
            }
            dogsRetrived(list)
            prefsHelper.saveUpdateTimer(System.nanoTime())
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}