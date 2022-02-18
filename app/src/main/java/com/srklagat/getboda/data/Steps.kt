package com.srklagat.getboda.data

import android.location.Location


data class Steps(
    val id: Int? = null,
    val arrival: Long? = null,
    val long: Double,
    val lat: Double,
    val stop: Int? = null,
    val locationName: String? = null,
    val type: String? = null
    /*val distance: String="",
    val duration: Int,
    val locationName: String,
    val load: List<Int> = emptyList(),
    val location: Location,
    val service: Int,
    val long: Double,
    val lat: Double,
    val type: String ="",
    val violation: List<Int> = emptyList(),
    val waitingTime: String=""*/
)