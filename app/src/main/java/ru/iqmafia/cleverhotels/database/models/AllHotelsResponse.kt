package ru.iqmafia.cleverhotels.database.models

import com.google.gson.annotations.SerializedName

data class AllHotelsResponse(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("suites_availability")
	val suitesAvailability: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stars")
	val stars: Double? = null
)