package com.thecrimsonpizza.tvtrackerkotlin.app.domain.serie

import com.thecrimsonpizza.tvtrackerkotlin.core.base.BaseClass
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Following(
    var added: Boolean = false,
    var addedDate: Date? = null,
    var watchedDate: Date? = null
) : BaseClass {
    @IgnoredOnParcel
    var watched: Boolean = false
        set(value) {
            field = value
            watchedDate =
                if (watched && watchedDate == null) Date() else if (!watched) null else watchedDate
        }
}