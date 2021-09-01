package ru.iqmafia.cleverhotels.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.iqmafia.cleverhotels.R
import ru.iqmafia.cleverhotels.database.models.AllHotelsResponse
import ru.iqmafia.cleverhotels.database.models.AllHotelsEntity
import ru.iqmafia.cleverhotels.ui.fragments.ListFragment

class ListRecyclerAdapter : RecyclerView.Adapter<ListRecyclerAdapter.ListVH>() {

    private var mListRetrofit = emptyList<AllHotelsResponse>()
    private var mListRoom = emptyList<AllHotelsEntity>()

    class ListVH(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.list_item_name_tv)
        val starsTv: TextView = view.findViewById(R.id.list_item_stars_tv)
        val distanceTv: TextView = view.findViewById(R.id.list_item_distance_tv)
        val suitesTv: TextView = view.findViewById(R.id.list_item_suites_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_recycler_item, parent, false)
        val viewHolder = ListVH(view)

        viewHolder.itemView.setOnClickListener {
            val idForDynamicFetch = mListRoom[viewHolder.bindingAdapterPosition].id.let { it!! }
            ListFragment.goToInfoFrag(idForDynamicFetch)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ListVH, position: Int) {
        holder.nameTv.text = mListRoom[position].name
        holder.starsTv.text = mListRoom[position].stars.toString()
        holder.distanceTv.text = mListRoom[position].distance.toString()
        holder.suitesTv.text = mListRoom[position].suitesCount.toString()


    }

    override fun getItemCount(): Int = mListRoom.size

    fun setList(list: List<AllHotelsEntity>) {
        mListRoom = list
        notifyDataSetChanged()
    }
}