package dev.guilherme.dog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import dev.guilherme.dog.R
import dev.guilherme.dog.databinding.ItemDogBinding
import dev.guilherme.dog.model.DogBreed
import dev.guilherme.dog.util.getProgressDrawable
import dev.guilherme.dog.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(),
    DogClickListener {

    fun updateDogslist(newList : List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount(): Int = dogsList.size


    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    class DogViewHolder(val view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)

    override fun onDogClick(v: View) {
        val action = ListFragmentDirections.actionDetailFragment()
        val uuid = v.dogId.text.toString().toInt()
        action.dogUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }
}