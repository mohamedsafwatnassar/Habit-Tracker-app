package com.example.habittrackerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.database.model.Habit
import com.example.habittrackerapp.utils.Calculations
import kotlinx.android.synthetic.main.recycler_habit_item.view.*

class HabitListAdapter : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    var habitsList = emptyList<Habit>()

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.cv_cardView.setOnClickListener {
                val position = adapterPosition

                val action = HabitListDirections.actionHabitListToUpdateHabitItem(habitsList[position])

                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_habit_item, parent, false))

    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = habitsList[position]
        holder.itemView.iv_habit_icon.setImageResource(currentHabit.imageId)
        holder.itemView.tv_item_description.text = currentHabit.habit_description
        holder.itemView.tv_timeElapsed.text =
            Calculations.calculateTimeBetweenDates(currentHabit.habit_startTime)
        holder.itemView.tv_item_createdTimeStamp.text = "Since: ${currentHabit.habit_startTime}"
        holder.itemView.tv_item_title.text = "${currentHabit.habit_title}"
    }

    override fun getItemCount(): Int {
        return habitsList.size
    }

    fun setData(habit: List<Habit>){
        this.habitsList = habit
        notifyDataSetChanged()
    }
}