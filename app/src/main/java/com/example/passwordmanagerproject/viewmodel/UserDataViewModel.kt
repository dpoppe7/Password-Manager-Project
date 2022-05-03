package com.example.passwordmanagerproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.passwordmanagerproject.model.UserData
import com.example.passwordmanagerproject.repo.UserDataRepository

class UserDataViewModel(application: Application): AndroidViewModel(application) {

        private val userDataRepo = UserDataRepository.getInstance(application.applicationContext)

        val userDataList: List<UserData> = userDataRepo.getUserDataList()

        val userDataLiveDataList: LiveData<List<UserData>> = userDataRepo.getUserDataLiveDataList()

        fun getUserData(userID: Long): UserData? = userDataRepo.getUserData(userID)

        fun getUserDataLiveData(userID: Long): LiveData<UserData?> = userDataRepo.getUserDataLiveData(userID)

        fun addUserData(userData: UserData) = userDataRepo.addUserData(userData)

        fun deleteUserData(userID: Long) = userDataRepo.deleteUserData(userID)

        fun blowItUp() = userDataRepo.blowItUp()
    }
