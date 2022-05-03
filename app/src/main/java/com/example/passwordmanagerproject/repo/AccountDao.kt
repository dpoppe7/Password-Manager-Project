package com.example.passwordmanagerproject.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.passwordmanagerproject.model.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM Account WHERE id = :id")
    fun getAccount(id: Long): LiveData<Account?>

    @Query("SELECT * FROM Account ORDER BY account_name COLLATE NOCASE")
    fun getAccounts(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccount(account: Account): Long

    @Update
    fun updateAccount(account: Account)

    @Delete
    fun deleteAccount(account: Account)
}