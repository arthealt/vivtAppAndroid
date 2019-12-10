package com.fixee.vivt.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fixee.vivt.R
import com.fixee.vivt.data.remote.models.Brs
import kotlinx.android.synthetic.main.brs_list_item.view.*

class BrsAdapter(private val brs: ArrayList<Brs>): RecyclerView.Adapter<BrsAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(layoutInflater.inflate(R.layout.brs_list_item, parent, false))
    }

    override fun getItemCount() = brs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = brs[position]
        holder.bind(item)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(brs: Brs) = with(itemView) {
            name.text = brs.PredmetName

            ballCP1.text = if (brs.BallCP1 == context.getString(R.string.not_ball) || brs.BallCP1.isEmpty()) {
                context.getString(R.string.ballCP1, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballCP1, brs.BallCP1)}

            ballCP2.text = if (brs.BallCP2 == context.getString(R.string.not_ball) || brs.BallCP2.isEmpty()) {
                context.getString(R.string.ballCP2, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballCP2, brs.BallCP2)}

            ballCP3.text = if (brs.BallCP3 == context.getString(R.string.not_ball) || brs.BallCP3.isEmpty()) {
                context.getString(R.string.ballCP3, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballCP3, brs.BallCP3)}

            ballCP4.text = if (brs.BallCP4 == context.getString(R.string.not_ball) || brs.BallCP4.isEmpty()) {
                context.getString(R.string.ballCP4, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballCP4, brs.BallCP4)}

            ballMC.text = if (brs.BallMC == context.getString(R.string.not_ball) || brs.BallMC.isEmpty()) {
                context.getString(R.string.ballMC, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballMC, brs.BallMC)}

            ballV.text = if (brs.BallV == context.getString(R.string.not_ball) || brs.BallV.isEmpty()) {
                context.getString(R.string.ballV, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballV, brs.BallV)}

            ballSum.text = if (brs.BallSum == context.getString(R.string.not_ball) || brs.BallSum.isEmpty()) {
                context.getString(R.string.ballSum, context.getString(R.string.none_ball)) } else {context.getString(R.string.ballSum, brs.BallSum)}
        }
    }

}