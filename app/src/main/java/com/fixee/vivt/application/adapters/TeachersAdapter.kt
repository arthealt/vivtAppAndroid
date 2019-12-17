package com.fixee.vivt.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.fixee.vivt.R
import com.fixee.vivt.data.remote.models.Teacher
import kotlinx.android.synthetic.main.teacher_list_item.view.*


class TeachersAdapter(private val teachers: ArrayList<Teacher>): RecyclerView.Adapter<TeachersAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context
    private var teachersFilter: ArrayList<Teacher> = teachers

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(layoutInflater.inflate(R.layout.teacher_list_item, parent, false))
    }

    override fun getItemCount() = teachersFilter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = teachersFilter[position]
        holder.bind(item)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                teachersFilter = if (charString.isEmpty()) {
                    teachers
                } else {
                    val filteredList: ArrayList<Teacher> = ArrayList()
                    for (teacher in teachers) {
                        if (context.getString(R.string.name_teacher, teacher.last_name, teacher.first_name, teacher.mid_name).toLowerCase().contains(charString)) {
                            filteredList.add(teacher)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = teachersFilter
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                teachersFilter = filterResults.values as ArrayList<Teacher>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(teacher: Teacher) = with(itemView) {
            name.text = context.getString(R.string.name_teacher, teacher.last_name, teacher.first_name, teacher.mid_name)
        }
    }
}