package jp.sasuraiusagi3.nippohub_daily.presentations.activities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.fragment.app.Fragment
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.adapters.DailyReportListAdapter
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.repositories.DailyReportRepository
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository
import java.time.YearMonth

class DailyReportListFragment : Fragment() {

    lateinit var yearMonth: YearMonth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_daily_report_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = UserRepository.currentUser ?: return
        val adapter = DailyReportListAdapter(context!!)

        view.findViewById<ListView>(R.id.daily_report_list_table_view).also {
            it.adapter = adapter
            it.onItemClickListener = DailyReportListClickListener(context!!)
        }

        DailyReportRepository.fetch(currentUser, yearMonth.atDay(1), yearMonth.atEndOfMonth()) {
            adapter.dailyReports = it
            adapter.notifyDataSetChanged()
        }
    }

    private class DailyReportListClickListener(private val context: Context) : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val dailyReport = parent?.adapter?.getItem(position) as DailyReport
            val intent = DailyReportShowActivity.build(context, dailyReport)

            context.startActivity(intent)
        }
    }
}