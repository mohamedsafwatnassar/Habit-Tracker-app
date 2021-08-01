package com.example.habittrackerapp.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.database.model.IntroView
import kotlinx.android.synthetic.main.intro_item_page.view.*

class ViewPagerIntroAdapter(introView: List<IntroView>) :
    RecyclerView.Adapter<ViewPagerIntroAdapter.IntroViewHolder>() {

    val list = introView;

    class IntroViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        return IntroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.intro_item_page, parent, false))

    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val currentView = list[position]
        holder.itemView.tv_description_intro.text = currentView.description
        holder.itemView.iv_image_intro.setImageResource(currentView.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}