package jp.sasuraiusagi3.nippohub_daily.presentations.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import jp.sasuraiusagi3.nippohub_daily.R

class DailyReportShareURL : FrameLayout, View.OnClickListener {
    private val groupShared: LinearLayout
    private val btnShare: Button
    private val textEditURL: EditText
    private val btnStopSharing: Button
    var accessKey: String? = null
        @SuppressLint("SetTextI18n")
        set(value) {
            field = value

            if (value.isNullOrBlank()) {
                groupShared.visibility = View.GONE
                btnShare.visibility = View.VISIBLE
            } else {
                groupShared.visibility = View.VISIBLE
                btnShare.visibility = View.GONE
                textEditURL.setText("https://nippohub.com/daily_reports/public/$value")
            }
        }
    var onClickShare: (() -> Unit)? = null
    var onClickStopSharing: (() -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : super(context, attrs, defStyleAttrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int, defStyleRes: Int) : super(context, attrs, defStyleAttrs, defStyleRes)

    init {
        val view = View.inflate(context, R.layout.view_daily_report_share_url, this)

        groupShared = view.findViewById(R.id.daily_report_share_url_group_shared)
        btnShare = view.findViewById<Button>(R.id.daily_report_share_url_btn_share).apply {
            setOnClickListener(this@DailyReportShareURL)
        }
        textEditURL = view.findViewById(R.id.daily_report_share_url_text_edit_url)
        btnStopSharing = view.findViewById<Button>(R.id.daily_report_share_url_btn_stop_sharing).apply {
            setOnClickListener(this@DailyReportShareURL)
        }

    }

    override fun onClick(p0: View?) {
        p0 ?: return

        when(p0.id) {
            R.id.daily_report_share_url_btn_share -> onClickShare?.invoke()
            R.id.daily_report_share_url_btn_stop_sharing -> onClickStopSharing?.invoke()
        }
    }
}