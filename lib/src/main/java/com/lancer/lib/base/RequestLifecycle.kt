package com.lancer.lib.base

interface RequestLifecycle {

    fun startLoading()

    fun loadFinished()

    fun loadFailed()
}