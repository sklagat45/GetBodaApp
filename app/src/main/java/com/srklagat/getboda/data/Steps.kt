package com.srklagat.getboda.data

import android.location.Location


data class Steps(
    val arrival: String="",
    val description: String ="",
    val distance: String="",
    val duration: Double,
    val load: List<Int> = emptyList(),
    val location: Location,
    val service: Int,
    val long: Double,
    val lat: Double,
    val type: String ="",
    val violation: List<Int> = emptyList(),
    val waitingTime: String=""
)