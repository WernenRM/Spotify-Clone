package com.wernen.spotifyclone.others

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.wernen.spotifyclone.R

class LoadingDialog() {

    private lateinit var dialog : AlertDialog

    @SuppressLint("InflateParams")
    fun startLoadingDialog(activity: Activity){
        val builder = AlertDialog.Builder(activity)

        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}