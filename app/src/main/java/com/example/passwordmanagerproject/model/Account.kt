package com.example.passwordmanagerproject.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


//Unique constraint found Here: https://stackoverflow.com/questions/48962106/add-unique-constraint-in-room-database-to-multiple-column
@Entity(indices = [Index(value = ["account_name"], unique = true)])
data class Account(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @NonNull
    @ColumnInfo(name="account_name")
    var acctName: String
)