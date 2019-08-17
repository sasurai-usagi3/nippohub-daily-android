package jp.sasuraiusagi3.nippohub_daily.use_cases

import jp.sasuraiusagi3.nippohub_daily.models.DailyReport
import jp.sasuraiusagi3.nippohub_daily.models.User
import jp.sasuraiusagi3.nippohub_daily.repositories.AccessKeyRepository
import jp.sasuraiusagi3.nippohub_daily.repositories.DailyReportRepository

object DailyReportShareUseCase {
    /**
     * アクセスキーを発行する
     *
     * @param user
     * @param dailyReport
     */
    fun share(user: User, dailyReport: DailyReport) {
        val accessKey = AccessKeyRepository.create(user, dailyReport)
        DailyReportRepository.updateAccessKey(user, dailyReport, accessKey.id)
    }

    /**
     * アクセスキーを削除する
     *
     * @param user
     * @param dailyReport
     */
    fun stopSharing(user: User, dailyReport: DailyReport) {
        AccessKeyRepository.delete(user, dailyReport)
        DailyReportRepository.updateAccessKey(user, dailyReport, null)
    }
}