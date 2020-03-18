package br.com.jeramovies.presentation.ui.myList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jeramovies.databinding.FragmentMyListBinding
import br.com.jeramovies.presentation.util.livedata.observe
import org.koin.android.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {

    private lateinit var binding: FragmentMyListBinding
    private val myListMovieAdapter by lazy { MyListMovieAdapter(viewModel::onMovieClicked) }
    private val viewModel: MyListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        //TODO -> Get movies from Realm or Room
        viewModel.savedMovies.observe(this, myListMovieAdapter::submitList)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myListMovieAdapter
    }
}