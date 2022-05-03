package com.example.passwordmanagerproject.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.passwordmanagerproject.model.AccountInfo
import javax.security.auth.Subject

@Dao
interface AccountInfoDao {

    @Query("SELECT * FROM AccountInfo WHERE id = :id")
    fun getAccountInfo(id: Long): LiveData<AccountInfo?>

    //According to Database design this function is redundant as
    //Current Relationship only allows for 1:1 relationship
    /*@Query("SELECT * FROM AccountInfo WHERE id = :id ORDER BY id")
    fun getAccountInfos(): LiveData<List<AccountInfo>>
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccountInfo(accountInfo: AccountInfo): Long

    @Update
    fun updateAccountInfo(accountInfo: AccountInfo)

    @Delete
    fun deleteAccountInfo(accountInfo: AccountInfo)

}