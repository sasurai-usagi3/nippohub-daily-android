package jp.sasuraiusagi3.nippohub_daily.models

import java.io.Serializable
import java.time.LocalDate

/**
 * Created by sasurai-usagi3 on 2019/04/26.
 */

data class DailyReport(
        val id: String,
        val date: LocalDate,
        val title: String,
        val content: String
) : Serializable