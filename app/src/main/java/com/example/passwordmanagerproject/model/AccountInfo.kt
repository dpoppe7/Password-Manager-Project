package com.example.passwordmanagerproject.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(foreignKeys = arrayOf(ForeignKey(entity = Account::class,
        parentColumns=  ["id"],
        childColumns = ["account_number"],
        onDelete = CASCADE)
))
data class AccountInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,


    @NonNull
    var acctUserID: String = "",
    var acctUserPassword: String = "",
    var isPwned: Boolean = false,


    @NonNull
    @ColumnInfo(name="account_number")
    var acctIDNum: Long


)
