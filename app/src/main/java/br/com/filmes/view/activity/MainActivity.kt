package br.com.filmes.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.com.filmes.R
import br.com.filmes.constants.Constants
import br.com.filmes.databinding.ActivityMainBinding
import br.com.filmes.model.entity.Movie
import br.com.filmes.model.interfaces.commons.ClickListener
import br.com.filmes.util.NetworkUtil
import br.com.filmes.viewmodel.MovieViewModel
import br.com.filmes.viewmodel.MovieViewModelFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
        ClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.swipeContainer.setOnRefreshListener(this)

        binding.layoutError!!.btTryAgain.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        binding.viewModel = ViewModelProviders.of(this, MovieViewModelFactory(this)).get(MovieViewModel::class.java)
        binding.viewModel!!.orientation = resources.configuration.orientation
    }

    override fun onResume() {
        super.onResume()
        checkNetwork()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        getQuery(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        getQuery(newText)
        return true
    }

    private fun getQuery(query: String?) {
        binding.viewModel!!.query = query
        binding.viewModel!!.clearListMovieSearch()
        binding.viewModel!!.searchMovie()
    }

    override fun clickListener(any: Any) {
        val movie = any as Movie
        startActivity(Intent(this, DetailMovieActivity::class.java).putExtra(Constants.OBJECT, movie))
    }

    override fun onRefresh() {
        binding.viewModel!!.refresh()
        binding.viewModel!!.refreshing.observe(this, Observer { refreshing ->
            binding.swipeContainer.isRefreshing = refreshing!!
        })
    }

    override fun onClick(v: View?) {
        binding.viewModel!!.checkListMovieIsEmpty()
    }

    private fun checkNetwork() {
        NetworkUtil.checkInternet(this).observe(this, Observer { network ->
            binding.viewModel!!.checkListMovieIsEmpty()
            binding.viewModel!!.showLayoutError().observe(this, Observer {
                i-> if (i!!) binding.layoutError!!.layoutParent.visibility = View.VISIBLE else binding.layoutError!!.layoutParent.visibility = View.GONE
            })
        })
    }

}