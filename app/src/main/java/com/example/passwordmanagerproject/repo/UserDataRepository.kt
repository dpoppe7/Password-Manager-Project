package com.example.passwordmanagerproject.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.passwordmanagerproject.model.UserData


class UserDataRepository private constructor(context: Context) {

    companion object {
        private var instance: UserDataRepository? = null

        fun getInstance(context: Context): UserDataRepository {
            if (instance == null) {
                instance = UserDataRepository(context)
        }
        return instance!!
        }
    }

    private val database: UserDataDatabase = Room.databaseBuilder(
        context.applicationContext,
        UserDataDatabase::class.java,
        "new_account.db"
    )
        .allowMainThreadQueries()
        .build()

    private val userDataDao = database.userDataDao()


    init {}

     fun getUserDataList(): List<UserData> = userDataDao.getUserDataList()

    fun getUserDataLiveDataList(): LiveData<List<UserData>> = userDataDao.getUserDataLiveDataList()

    fun addUserData(userData: UserData) {
        userData.id = userDataDao.addUserData(userData)
    }

    fun deleteUserData(userID: Long) = userDataDao.deleteUserData(userID)

    fun blowItUp() = userDataDao.blowItUp()

    fun getUserData(userDataID: Long): UserData? = userDataDao.getUserData(userDataID)

    fun getUserDataLiveData(userDataID: Long): LiveData<UserData?> = userDataDao.getUserDataLiveData(userDataID)

    fun updateUserData(userData: UserData) = userDataDao.updateUserData(userData)
}