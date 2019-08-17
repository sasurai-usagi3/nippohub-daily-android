package jp.sasuraiusagi3.nippohub_daily.repositories

import com.google.firebase.database.FirebaseDatabase
import jp.sasuraiusagi3.nippohub_daily.models.AccessKey
import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.models.User

object AccessKeyRepository {
    private val instance = FirebaseDatabase.getInstance()

    fun create(user: User, dailyReport: DailyReport): AccessKey {
        val row = instance.getReference("/access_keys").push()

        row.setValue(
                mapOf(
                        "user_id" to user.id,
                        "daily_report_id" to dailyReport.id
                )
        )

        // TODO: IDがnullの対応
        return AccessKey(row.key ?: "", user.id, dailyReport.id)
    }

    fun delete(user: User, dailyReport: DailyReport) {
        instance.getReference("/access_keys/${dailyReport.accessKey}").setValue(null)
    }
}