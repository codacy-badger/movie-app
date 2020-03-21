package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import br.com.jeramovies.presentation.ui.myList.MyListFragment
import br.com.jeramovies.presentation.ui.search.SearchMoviesFragment
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.observeChanges
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val viewModel: MainViewModel by viewModel()
    private val searchFragment by lazy { SearchMoviesFragment() }
    private val myListFragment by lazy { MyListFragment() }
    private val mainFragment by lazy { MainFragment() }
    private lateinit var activeFragmentBeforeSearch: Fragment
    private lateinit var activeFragment: Fragment
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(binding.toolbar)
        setupFragments()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return menu?.let {
            menuInflater.inflate(R.menu.menu_search, menu)
            true
        } ?: super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_search) {
            setupSearchView((item.actionView as SearchView))
            showFragment(searchFragment)
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_movies -> {
                showFragment(mainFragment)
                true
            }
            R.id.action_my_list -> {
                showFragment(myListFragment)
                binding.appBarLayout.setExpanded(true)
                true
            }
            else -> false
        }
    }

    private fun setupFragments() {
        addHideFragmentToStack(searchFragment)
        addHideFragmentToStack(myListFragment)
        addActiveFragmentToStack(mainFragment)
    }

    private fun addHideFragmentToStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, fragment)
            .hide(fragment)
            .commit()
    }

    private fun addActiveFragmentToStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, fragment)
            .commit()
        activeFragment = fragment
    }

    private fun showFragment(fragmentToShow: Fragment) {
        if (fragmentToShow is SearchMoviesFragment)
            activeFragmentBeforeSearch = activeFragment
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragmentToShow)
            .commit()
        activeFragment = fragmentToShow
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.observeChanges(lifecycle, viewModel::onSearchText)
        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View?) {
                showFragment(activeFragmentBeforeSearch)
            }

            override fun onViewAttachedToWindow(p0: View?) {/* NOTHING TO DO */
            }
        })
    }
}