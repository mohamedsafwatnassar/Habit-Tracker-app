package com.example.habittrackerapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittrackerapp.database.model.Habit
import com.example.habittrackerapp.viewModel.HabitViewModel
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitList: Fragment(R.layout.fragment_habit_list) {

    lateinit var habitList: List<Habit>
    lateinit var habitViewModel: HabitViewModel
    lateinit var habitAdapter: HabitListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitAdapter = HabitListAdapter()
        rv_habits.adapter = habitAdapter
        rv_habits.layoutManager = LinearLayoutManager(context)

        // get All habits
        viewModel()

        fab_add.setOnClickListener{
            findNavController().navigate(R.id.action_habitList_to_createHabitItem)
        }

        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            habitAdapter.setData(habitList)
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun viewModel() {
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
            habitAdapter.setData(it)
            habitList = it

            if (it.isEmpty()) {
                rv_habits.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else {
                rv_habits.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_delete -> habitViewModel.deleteAllHabits()
        }

        return super.onOptionsItemSelected(item)
    }
}