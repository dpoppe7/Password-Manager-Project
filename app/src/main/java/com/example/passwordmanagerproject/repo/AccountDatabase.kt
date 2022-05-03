package com.example.passwordmanagerproject.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanagerproject.model.Account
import com.example.passwordmanagerproject.model.AccountInfo
import com.example.passwordmanagerproject.model.UserData

@Database(entities =[UserData::class], version = 3)
abstract class UserDataDatabase: RoomDatabase() {
    abstract fun userDataDao():UserDataDao
}