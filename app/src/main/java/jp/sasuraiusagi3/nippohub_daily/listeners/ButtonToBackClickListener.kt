package jp.sasuraiusagi3.nippohub_daily.listeners

import android.app.Activity
import android.view.View

/**
 * Created by sasurai-usagi3 on 2019/05/11.
 */

class ButtonToBackClickListener(private val activity: Activity) : View.OnClickListener {
    override fun onClick(v: View?) {
        this.activity.finish()
    }
}