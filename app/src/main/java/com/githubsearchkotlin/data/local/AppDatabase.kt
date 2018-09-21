package com.githubsearchkotlin.data.local

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
object AppDatabase {

   const val NAME = "SearchGithubKotlinDB"
   const val VERSION = 1

}