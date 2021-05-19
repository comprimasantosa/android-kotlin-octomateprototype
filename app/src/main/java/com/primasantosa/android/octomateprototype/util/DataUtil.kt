package com.primasantosa.android.octomateprototype.util

import com.primasantosa.android.octomateprototype.data.model.Announcement
import com.primasantosa.android.octomateprototype.data.model.Event
import com.primasantosa.android.octomateprototype.data.model.Reminder

object DataUtil {
    val announcementData = listOf(
        Announcement(
            title = "Adecco",
            subtitle = "A warm welcome onboard!",
            body = "Hi all, here is a quick onboarding document to get you started",
            tag = "General",
            date = "07/10/2019"
        ),
        Announcement(
            title = "Adecco",
            subtitle = "A warm welcome onboard!",
            body = "Hi all, here is a quick onboarding document to get you started",
            tag = "General",
            date = "07/10/2019"
        ),
        Announcement(
            title = "Adecco",
            subtitle = "A warm welcome onboard!",
            body = "Hi all, here is a quick onboarding document to get you started",
            tag = "General",
            date = "07/10/2019"
        )
    )

    val reminderData = listOf(
        Reminder(
            title = "Your timesheet has not been submitted!",
            placement = "09321 (IBM Singapore)",
            dateFrom = "12 Jan 2018",
            dateTo = "15 Dec 2018"
        ),
        Reminder(
            title = "Your timesheet has not been submitted!",
            placement = "09321 (IBM Singapore)",
            dateFrom = "12 Jan 2018",
            dateTo = "15 Dec 2018"
        ),
        Reminder(
            title = "Your timesheet has not been submitted!",
            placement = "09321 (IBM Singapore)",
            dateFrom = "12 Jan 2018",
            dateTo = "15 Dec 2018"
        )
    )

    val eventData = listOf(
        Event(
            title = "Another Associate",
            totalHours = "02H 00M",
            totalBreak = "00H 00M",
            timeStart = "05:00 PM",
            timeEnd = "07:00 PM",
            isEditable = true,
            tag = "Submitted"

        ),
        Event(
            title = "SINB Associate",
            totalHours = "07H 00M",
            totalBreak = "01H 00M",
            timeStart = "07:00 AM",
            timeEnd = "02:00 PM",
            isEditable = false,
            tag = "Approved"
        ),
        Event(
            title = "SINB Associate",
            totalHours = "02H 00M",
            totalBreak = "00H 00M",
            timeStart = "05:00 PM",
            timeEnd = "07:00 PM",
            isEditable = false,
            tag = "Rejected"
        )
    )
}