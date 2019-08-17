package jp.sasuraiusagi3.nippohub_daily.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport

/**
 * Created by sasurai-usagi3 on 2019/04/30.
 */

class DailyReportListAdapter : BaseAdapter {
    private val context: Context
    private val inflater: LayoutInflater
    var dailyReports: List<DailyReport> = emptyList()

    constructor(context: Context) {
        this.context = context
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int = dailyReports.count()

    override fun getItem(p0: Int) = dailyReports[p0]

    override fun getItemId(p0: Int) = p0.toLong() // TODO: これで良いか確認する

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val dailyReport = dailyReports[p0]
        val view = p1 ?: inflater.inflate(R.layout.view_daily_report_list_item, p2, false)

        view.findViewById<TextView>(R.id.daily_report_list_item_title).text = "${dailyReport.date.toString()} ${dailyReport.title}"

        return view
    }
}