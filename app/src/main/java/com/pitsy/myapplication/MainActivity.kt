package com.pitsy.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pitsy.myapplication.adapters.AdapterMovies
import com.pitsy.myapplication.models.MovieModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var actorSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies.layoutManager = LinearLayoutManager(this)

        btnAddMovieJuniffer.setOnClickListener { addMovieJennifer() }
        btnAddMovieLeo.setOnClickListener { addMovieLeo() }
        btnAddMovieLuciano.setOnClickListener { addMovieLuciano() }
        btnSaveMovie.setOnClickListener { saveMovie() }

    }

    private fun saveMovie() {
        toggleVisibility(true)
        val movie : MovieModel = getMovie()

        FirebaseDatabase.getInstance()
            .getReference(movie.nameActor)
            .child(movie.idMovie)
            .setValue(movie)

        loadMovies(movie.nameActor)
    }

    private fun loadMovies(nameActor: String) {

        FirebaseDatabase.getInstance()
            .getReference(nameActor)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {


                }

                override fun onDataChange(p0: DataSnapshot) {
                    onLoadMovies(p0)
                }

            })

    }

    private fun onLoadMovies(p0: DataSnapshot) {
        if (p0.exists()){
            val list = mutableListOf<MovieModel>()
            p0.children.forEach {movie->
                val model = movie.getValue(MovieModel::class.java)!!
                list.add(model)
            }

            rvMovies.adapter = AdapterMovies(list)
        }
    }

    private fun getMovie(): MovieModel{

        val nameMovie = etMovieName.text.toString()
        val nameActor = actorSelected
        val movieCategory:String = spCategorie.selectedItem.toString()
        val idMovie = FirebaseDatabase.getInstance().getReference(nameActor).push().key

        val movie : MovieModel = MovieModel(nameMovie,nameActor,movieCategory,idMovie!!)

        return movie
    }




    private fun addMovieLuciano() {
        actorSelected = "Luciano De Alexandro"
        tvTitleAdd.text="Agregar pelicula de: $actorSelected"
        toggleVisibility(false)
    }

    private fun addMovieLeo() {
        actorSelected = "Leonardo D'Caprio"
        tvTitleAdd.text="Agregar pelicula de: $actorSelected"
        toggleVisibility(false)
    }

    private fun addMovieJennifer() {
        actorSelected = "Jennifer Lopez"
        tvTitleAdd.text="Agregar pelicula de: $actorSelected"
        toggleVisibility(false)

    }
    private fun toggleVisibility(isVisibleList: Boolean) {
        if (isVisibleList){
            rvMovies.visibility = View.VISIBLE
            lnAddMovie.visibility = View.GONE
        }else{
            rvMovies.visibility = View.GONE
            lnAddMovie.visibility = View.VISIBLE
        }

    }
}
