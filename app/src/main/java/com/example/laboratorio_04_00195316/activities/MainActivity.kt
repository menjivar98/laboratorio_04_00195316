package com.example.laboratorio_04_00195316.activities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.example.laboratorio_04_00195316.R
import com.example.laboratorio_04_00195316.adapters.MovieAdapter
import com.example.laboratorio_04_00195316.model.Movie
import com.example.laboratorio_04_00195316.utils.NetworkUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var movieList: ArrayList<Movie> = ArrayList()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun initRecylerView(){
        var viewManager = LinearLayoutManager(this)

    }

    fun initSearchbar() {
        add_movie_btn.setOnClickListener{if (!movie_name_et.text.isEmpty()){ FetchMovie().execute(movie_name_et.toString()) }}
    }


    fun addMovieToList(movie: Movie){
        movieList.add(movie)
        movieAdapter.changeList(movieList)
    }

    private inner class FetchMovie : AsyncTask <String, Void, String>(){
        override fun doInBackground(vararg params: String): String {
            if (params.isNullOrEmpty()) return ""

            val movieName = params[0]
            val movieUrl = NetworkUtils().buildSearchURL(movieName)

            return try {
                NetworkUtils().getResponseFromHttpURL(movieUrl)
            }catch (e: IOException){
                ""
            }
        }

        override fun onPostExecute(movieInfo: String) {
            super.onPostExecute(movieInfo)

            if(!movieInfo.isEmpty()){
                val movieJson = JSONObject(movieInfo)

                if(movieJson.getString("Response") == "True"){
                    val movie = Gson().fromJson<Movie>(movieInfo, Movie::class.java)
                    addMovieToList(movie)
                }else{
                    Snackbar.make(main_ll,"No existe la pelicula en la base",Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}
