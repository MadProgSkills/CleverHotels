package ru.iqmafia.cleverhotels.database.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class InfoResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("suites_availability")
	val suitesAvailability: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stars")
	val stars: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readString(),
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Double::class.java.classLoader) as? Double
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(image)
		parcel.writeString(address)
		parcel.writeString(suitesAvailability)
		parcel.writeValue(distance)
		parcel.writeString(name)
		parcel.writeValue(lon)
		parcel.writeValue(id)
		parcel.writeValue(stars)
		parcel.writeValue(lat)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<InfoResponse> {
		override fun createFromParcel(parcel: Parcel): InfoResponse {
			return InfoResponse(parcel)
		}

		override fun newArray(size: Int): Array<InfoResponse?> {
			return arrayOfNulls(size)
		}
	}
}