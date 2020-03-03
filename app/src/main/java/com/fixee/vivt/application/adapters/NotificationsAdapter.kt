package com.fixee.vivt.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fixee.vivt.R
import com.fixee.vivt.data.remote.models.Notification
import kotlinx.android.synthetic.main.notification_list_item.view.*

class NotificationsAdapter(private val notifications: ArrayList<Notification>): RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_list_item, parent, false))
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notifications[position]
        holder.bind(item)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(notification: Notification) = with(itemView) {
            val fullDate = notification.time_create + " " + notification.date_create

            title_notify.text = notification.title
            text_notify.text = notification.text
            date_notify.text = fullDate
        }
    }
}