package com.eww.dummyapicall.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Users(
    val data: List<User>? = null,
)

@Entity(tableName = "user")
data class User(
    @field:SerializedName("website")
    @ColumnInfo(name = "website")
    val website: String? = null,

    @field:SerializedName("phone")
    @ColumnInfo(name = "phone")
    val phone: String? = null,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = null,

    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @field:SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String? = null,

    @field:SerializedName("username")
    @ColumnInfo(name = "username")
    val username: String? = null,
)