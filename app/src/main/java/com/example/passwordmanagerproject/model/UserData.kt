package com.example.passwordmanagerproject.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity
data class UserData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="account_UID")
    var id: Long = 0,


    @Nullable
    @ColumnInfo(name="account_name")
    var acctName: String = "",

    @Nullable
    @ColumnInfo(name="account_id")
    var acctID: String = "",


    @Nullable
    @ColumnInfo(name="acct_password")
    var acctPassword: String = "",


    @Nullable
    @ColumnInfo(name="is_pwned")
    var isPwned: Boolean = false,



)