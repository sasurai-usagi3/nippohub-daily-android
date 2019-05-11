package jp.sasuraiusagi3.nippohub_daily.repositories

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import java.time.LocalDate

/**
 * Created by sasurai-usagi3 on 2019/05/11.
 */

private typealias FetchDailyReportsCallBackFun = (List<DailyReport>) -> Unit

object DailyReportRepository {
    private val instance = FirebaseDatabase.getInstance()
    // TODO: コールバックをラムダに直したい
    /**
     * 指定したユーザの日報を最大$limitつ取得する
     *
     * @param userId ユーザ
     * @param limit 最大値
     */
    fun fetch(user: FirebaseUser, limit: Int = 30, callBackFun: FetchDailyReportsCallBackFun) {
        this.instance
                .getReference("/users/${user.uid}/daily_reports")
                .orderByChild("date")
                .limitToLast(limit)
                .addValueEventListener(DailyReportIndexFetcher(callBackFun))
    }

    fun create(user: FirebaseUser, date: LocalDate, title: String, content: String) {
        val ref = this.instance.getReference("/users/${user.uid}/daily_reports").push()

        ref.setValue(
                mapOf(
                        "date" to date.toString(),
                        "title" to title,
                        "content" to content,
                        "createdAt" to System.currentTimeMillis()
                )
        )
    }

    fun update(user: FirebaseUser, dailyReport: DailyReport) {
        this.instance
                .getReference("/users/${user.uid}/daily_reports/${dailyReport.id}")
                .setValue(
                        mapOf(
                                "date" to dailyReport.date.toString(),
                                "title" to dailyReport.title,
                                "content" to dailyReport.content
                        )
                )
    }

    private class DailyReportIndexFetcher(private val callBackFun: FetchDailyReportsCallBackFun) : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            val dailyReports = p0.children.map {
                DailyReport(
                        it.key!!,
                        LocalDate.parse(it.child("date").value as String),
                        it.child("title").value as String,
                        it.child("content").value as String
                )
            }.sortedBy { it.date }.reversed()

            this.callBackFun(dailyReports)
        }

        override fun onCancelled(p0: DatabaseError) {}
    }
}