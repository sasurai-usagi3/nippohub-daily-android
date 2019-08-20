package jp.sasuraiusagi3.nippohub_daily.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.models.User
import java.time.LocalDate
import java.time.format.DateTimeParseException

/**
 * Created by sasurai-usagi3 on 2019/05/11.
 */

private typealias FetchDailyReportsCallBackFun = (List<DailyReport>) -> Unit

object DailyReportRepository {
    private val instance = FirebaseDatabase.getInstance()
    /**
     * 指定したユーザの日報を最大$limitつ取得する
     *
     * @param user ユーザ
     * @param limit 最大値
     */
    fun fetch(user: User, gteq_date: LocalDate? = null, lteq_date: LocalDate? = null, limit: Int = 30, callBackFun: FetchDailyReportsCallBackFun) {
        instance
                .getReference("/users/${user.id}/daily_reports")
                .orderByChild("date")
                .let {
                    if (gteq_date != null) {
                        it.startAt(gteq_date.toString())
                    } else {
                        it
                    }
                }
                .let {
                    if (lteq_date != null) {
                        it.endAt(lteq_date.toString())
                    } else {
                        it
                    }
                }
                .limitToLast(limit)
                .addValueEventListener(DailyReportIndexFetcher(callBackFun))
    }

    /**
     * 指定したユーザの日報を作成する
     *
     * @param user ユーザ
     * @param date 日付
     * @param title タイトル
     * @param content 内容
     */
    fun create(user: User, date: LocalDate, title: String, content: String) {
        val ref = instance.getReference("/users/${user.id}/daily_reports").push()

        ref.setValue(
                mapOf(
                        "date" to date.toString(),
                        "title" to title,
                        "content" to content,
                        "createdAt" to System.currentTimeMillis()
                )
        )
    }

    /**
     * 指定したユーザの渡された日報を更新する
     *
     * @param user ユーザ
     * @param dailyReport 日報
     */
    fun update(user: User, dailyReport: DailyReport) {
        instance
                .getReference("/users/${user.id}/daily_reports/${dailyReport.id}")
                .updateChildren(
                        mapOf(
                                "date" to dailyReport.date.toString(),
                                "title" to dailyReport.title,
                                "content" to dailyReport.content
                        )
                )
    }

    /**
     * 指定したユーザの指定した日報のアクセスキーを更新する
     *
     * @param user
     * @param dailyReport
     * @param accessKey
     */
    fun updateAccessKey(user: User, dailyReport: DailyReport, accessKey: String?) {
        instance
                .getReference("/users/${user.id}/daily_reports/${dailyReport.id}")
                .updateChildren(
                        mapOf(
                                "access_key" to accessKey
                        )
                )
    }

    private class DailyReportIndexFetcher(private val callBackFun: FetchDailyReportsCallBackFun) : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            val dailyReports = p0.children.map {
                DailyReport(
                        it.key!!,
                        try {
                            LocalDate.parse(it.child("date").value as String)
                        } catch (e: DateTimeParseException) {
                            // TODO: createdAtなどから極力復元を試みる
                            LocalDate.now()
                        },
                        it.child("title").value as String,
                        it.child("content").value as String,
                        ""
                )
            }.sortedBy { it.date }.reversed()

            callBackFun(dailyReports)
        }

        override fun onCancelled(p0: DatabaseError) {}
    }
}