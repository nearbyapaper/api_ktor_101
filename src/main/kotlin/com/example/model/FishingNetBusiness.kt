package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class FishingNetBusiness(
    val name: String ?= "",
    val value: String ?= "",
    val otherHaveThisValue: String ?= "",
    val wantToAcceptThisValue: String ?= "",
    val whyYouWantToAcceptThisValue: String ?= "",
    val durableValue: String ?= "",
)
