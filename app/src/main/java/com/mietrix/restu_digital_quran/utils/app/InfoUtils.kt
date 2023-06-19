/*
 * (c) Faisal Khan. Created on 21/11/2021.
 */
package com.mietrix.restu_digital_quran.utils.app

import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.mietrix.restu_digital_quran.R
import com.mietrix.restu_digital_quran.api.models.AppUrls
import com.mietrix.restu_digital_quran.utils.Logger
import com.mietrix.restu_digital_quran.utils.univ.MessageUtils
import com.mietrix.restu_digital_quran.widgets.dialog.loader.PeaceProgressDialog
import java.util.concurrent.CancellationException

object InfoUtils {
    @JvmStatic
    fun openFeedbackPage(context: Context) {
        openTab(context, UrlsManager.URL_KEY_FEEDBACK)
    }

    @JvmStatic
    fun openPrivacyPolicy(context: Context) {
        openTab(context, UrlsManager.URL_KEY_PRIVACY_POLICY)
    }

    @JvmStatic
    fun openAbout(context: Context) {
        openTab(context, UrlsManager.URL_KEY_ABOUT)
    }

    @JvmStatic
    fun openHelp(context: Context) {
        openTab(context, UrlsManager.URL_KEY_HELP)
    }

    private fun openTab(context: Context, urlKey: String) {
        val urlsManager = UrlsManager(context)
        val dialog = PeaceProgressDialog(context).apply {
            setMessage(R.string.strTextPleaseWait)
            setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.strLabelCancel)) { _, _ ->
                urlsManager.cancel()
                dismiss()
            }
            show()
        }

        val failedCallback = { e: Exception ->
            e.printStackTrace()
            dialog.dismiss()
            if (e !is CancellationException) {
                Logger.reportError(e)
                MessageUtils.popMessage(
                    context,
                    context.getString(R.string.strMsgSomethingWrong),
                    "${context.getString(R.string.strMsgCouldNotOpenPage)} ${
                        context.getString(
                            R.string.strMsgTryLater
                        )
                    }",
                    context.getString(R.string.strLabelClose),
                    null
                )
            }
        }

        urlsManager.getUrlsJson({ (privacyPolicy, about, help, feedback): AppUrls ->
            val url: String? = when (urlKey) {
                UrlsManager.URL_KEY_FEEDBACK -> feedback
                UrlsManager.URL_KEY_PRIVACY_POLICY -> privacyPolicy
                UrlsManager.URL_KEY_ABOUT -> about
                UrlsManager.URL_KEY_HELP -> help
                else -> null
            }

            try {
                prepareCustomTab(context).launchUrl(context, Uri.parse(url))
                dialog.dismiss()
            } catch (e: Exception) {
                failedCallback(e)
            }
        }, failedCallback)
    }

    private fun prepareCustomTab(context: Context): CustomTabsIntent {
        return CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorBGPage))
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .build()
    }
}
