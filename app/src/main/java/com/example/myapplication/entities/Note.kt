package com.example.myapplication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey @ColumnInfo(name = "objectId") val objectId: String,
    val content: String,
    val parentObjectId: String,
    val status: Int,
    val tags: String,
    val createdAt: String,
    val updatedAt: String
)