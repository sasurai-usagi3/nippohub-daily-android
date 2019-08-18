package jp.sasuraiusagi3.nippohub_daily.presentations.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import jp.sasuraiusagi3.nippohub_daily.R

class DailyReportShareURL : FrameLayout {
    private val groupShared: LinearLayout
    private val btnShare: Button
    private val textEditURL: EditText
    private val btnStopSharing: Button
    var shareURL: String? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int, defStyleRes: Int) : super(context, attrs, defStyleAttrs, defStyleRes)

    init {
        val view = View.inflate(context, R.layout.view_daily_report_share_url, this)

        groupShared = view.findViewById(R.id.daily_report_share_url_group_shared)
        btnShare = view.findViewById(R.id.daily_report_share_url_btn_share)
        textEditURL = view.findViewById(R.id.daily_report_share_url_text_edit_url)
        btnStopSharing = view.findViewById(R.id.daily_report_share_url_btn_stop_sharing)
    }
}