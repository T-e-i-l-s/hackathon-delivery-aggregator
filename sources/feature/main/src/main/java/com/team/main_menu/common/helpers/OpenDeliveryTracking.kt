package com.team.main_menu.common.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.team.network.ApiConstants.T_BANK_BASE_URL

fun openTracking(context: Context, trackingId: String) {
    val url = "${T_BANK_BASE_URL}gorod/tracking/parcel/$trackingId/"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
