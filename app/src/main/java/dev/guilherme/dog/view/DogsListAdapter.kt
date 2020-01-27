package dev.guilherme.dog.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import dev.guilherme.dog.R
import dev.guilherme.dog.model.DogBreed
import dev.guilherme.dog.util.getProgressDrawable
import dev.guilherme.dog.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    fun updateDogslist(newList : List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount(): Int = dogsList.size


    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog =  dogsList[position]

        holder.view.name.text = dog.dogBreed
        holder.view.lifespan.text = dog.lifeSpan
        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment())
        }
        holder.view.imageView.loadImage(dog.imageUrl, getProgressDrawable(holder.view.imageView.context))
    }

    class DogViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}