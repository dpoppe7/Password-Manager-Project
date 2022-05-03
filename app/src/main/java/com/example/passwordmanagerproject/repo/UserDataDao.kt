package com.example.passwordmanagerproject.repo

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.passwordmanagerproject.model.UserData
import javax.security.auth.Subject

@Dao
interface UserDataDao {

    @Query("SELECT * FROM UserData WHERE account_UID = :id")
    fun getUserData(id: Long): UserData?

    @Query("SELECT * FROM UserData WHERE account_UID = :id")
    fun getUserDataLiveData(id: Long): LiveData<UserData?>

    @Query("SELECT * FROM UserData ORDER BY account_id COLLATE NOCASE")
    fun getUserDataList(): List<UserData>

    @Query("SELECT * FROM UserData ORDER BY account_id COLLATE NOCASE")
    fun getUserDataLiveDataList(): LiveData<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserData(userData: UserData): Long

    @Update
    fun updateUserData(userData: UserData)

    @Query("DELETE FROM UserData WHERE account_UID = :id")
    fun deleteUserData(id: Long)

    @Query("DELETE FROM UserData")
    fun blowItUp()

}