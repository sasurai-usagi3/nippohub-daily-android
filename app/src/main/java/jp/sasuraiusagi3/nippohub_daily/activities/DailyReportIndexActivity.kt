package jp.sasuraiusagi3.nippohub_daily.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import jp.sasuraiusagi3.nippohub_daily.R
import jp.sasuraiusagi3.nippohub_daily.repositories.UserRepository

class DailyReportIndexActivity : AppCompatActivity() {

    companion object {
        fun build(context: Context) = Intent(context, DailyReportIndexActivity::class.java)
    }

    override fun onStart() {
        super.onStart()

        if (!UserRepository.didSignIn()) {
            val intent = SignInActivity.build(this)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report_index)

        val tab = findViewById<TabLayout>(R.id.daily_report_index_tab)
        val viewPager = findViewById<ViewPager>(R.id.daily_report_index_pager)

        viewPager.adapter = DailyReportListViewPagerAdapter(supportFragmentManager)
        tab.setupWithViewPager(viewPager)

        findViewById<Button>(R.id.daily_report_index_button_to_new).also {
            it.setOnClickListener(ButtonToNewClickListener(this))
        }
        findViewById<Button>(R.id.daily_report_index_button_to_settings).also {
            it.setOnClickListener(ButtonToSettingsClickListener(this))
        }
    }

    private class ButtonToSettingsClickListener(private val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = SettingsActivity.build(context)

            this.context.startActivity(intent)
        }
    }

    private class ButtonToNewClickListener(private val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = NewDailyReportActivity.build(context)

            this.context.startActivity(intent)
        }
    }

    private class DailyReportListViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount() = 5

        override fun getItem(position: Int) = DailyReportListFragment()
    }
}
