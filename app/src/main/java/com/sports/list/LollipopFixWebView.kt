package com.sports.list

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView

@Suppress("MagicNumber")
private fun Context.getLollipopFixWebView(): Context {
    return if (Build.VERSION.SDK_INT in 21..22) {
        createConfigurationContext(Configuration())
    } else this
}

class LollipopFixWebView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context.getLollipopFixWebView(), attrs, defStyleAttr) {

    init {
        isFocusable = true
        isFocusableInTouchMode = true
    }

}
