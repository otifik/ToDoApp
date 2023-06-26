package xyz.otifik.todoapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApplication: Application(){

    companion object {
        var  _context:Application? = null
        fun getContext():Context{
            return _context!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }

}