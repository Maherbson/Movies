package br.com.filmes.model.entity

import com.google.gson.annotations.SerializedName

class MovieResult(@SerializedName("results") var results: List<Movie>,
                  @SerializedName("total_pages") var totalPages: Int,
                  @SerializedName("page") var currentPage: Int)