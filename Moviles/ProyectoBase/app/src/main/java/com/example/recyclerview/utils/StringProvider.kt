package com.example.recyclerview.utils

import android.content.Context

class StringProvider(val context: Context) {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }
}