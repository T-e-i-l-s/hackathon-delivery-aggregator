package com.team.main_menu.common.helpers

import com.team.network.ApiConstants

fun buildLogoUrl(logoId: String): String {
    if (logoId.isBlank()) return ""
    return "${ApiConstants.BASE_URL}attachments/${logoId}/content"
}