package br.com.jeramovies.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jeramovies.databinding.FragmentSearchBinding
import br.com.jeramovies.presentation.ui.main.MainViewModel
import br.com.jeramovies.presentation.ui.movies.MoviesAdapter
import br.com.jeramovies.presentation.util.livedata.observe
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMoviesFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private val activityViewModel: MainViewModel by sharedViewModel()
    private var adapter: MoviesAdapter? = null
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
        }
        setupRecyclerView()
        subscribeUi()
        return binding.root
    }

    private fun setupRecyclerView() {
        if (adapter == null) {
            adapter = MoviesAdapter(viewModel::onMovieClicked, viewModel::onSaveClicked)
        }
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeUi() {
        viewModel.searchMovies.observe(viewLifecycleOwner) { movies -> adapter?.submitList(movies) }
        viewModel.goTo.observe(viewLifecycleOwner, activityViewModel::goTo)
        activityViewModel.searchText.observe(viewLifecycleOwner, viewModel::searchMovies)
    }
}