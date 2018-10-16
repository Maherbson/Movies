package br.com.filmes.model.entity

import com.google.gson.annotations.SerializedName

class GenreResult(@SerializedName("genres") var results: List<Genre>)