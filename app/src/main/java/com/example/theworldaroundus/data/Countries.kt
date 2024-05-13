package com.example.theworldaroundus.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject

@Entity(tableName = "country", primaryKeys = ["nameCommon"])
data class CountryDb(
    val iconPng: String?,
    val iconSvg: String?,
    val iconAlt: String?,
    val nameCommon: String,
    val nameOfficial: String?,
    val nameNativeName: String?
)

data class Country(
    @SerializedName("flags") val flags: Flags?,
    @SerializedName("name") val name: Name?
)

data class Flags(
    @SerializedName("png") val png: String?,
    @SerializedName("svg") val svg: String?,
    @SerializedName("alt") val alt: String?
)

data class Name(
    @SerializedName("common") val common: String?,
    @SerializedName("official") val official: String?,
    @SerializedName("nativeName") val nativeName: Map<String, Map<String, String>>?
)