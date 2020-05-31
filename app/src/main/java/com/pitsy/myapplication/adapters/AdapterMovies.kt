package com.pitsy.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.pitsy.myapplication.R
import com.pitsy.myapplication.models.MovieModel
import kotlinx.android.synthetic.main.item_movie.view.*

class AdapterMovies (val list: List<MovieModel>) : RecyclerView.Adapter<AdapterMovies.ViewHolder>(){

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.item_movie,parent,false)){
        fun bind(movieModel: MovieModel){
            itemView.item_movie_actor.setText(movieModel.nameActor)
            itemView.item_movie_categorie.setText(movieModel.movieCategory)
            itemView.item_movie_name.setText(movieModel.nameMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater,parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list[position])

    }




}