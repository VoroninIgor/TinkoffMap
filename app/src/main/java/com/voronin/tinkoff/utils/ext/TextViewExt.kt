package com.voronin.tinkoff.utils.ext

import android.text.Spannable
import android.text.TextPaint
import android.text.style.URLSpan
import android.text.util.Linkify
import android.util.Patterns
import android.widget.TextView

fun TextView.makePhoneAutoLink() {
    Linkify.addLinks(
        this,
        Patterns.PHONE,
        "tel:",
        Linkify.sPhoneNumberMatchFilter,
        Linkify.sPhoneNumberTransformFilter
    )
    stripUnderlines()
}

/**
 * При использовании autoLink удаляется автоматическое подчёркивание
 */
fun TextView.stripUnderlines() {
    if (text !is Spannable) return

    val spannableText = text as Spannable
    val spans = spannableText.getSpans(0, spannableText.length, URLSpan::class.java)
    for (span in spans) {
        val start = spannableText.getSpanStart(span)
        val end = spannableText.getSpanEnd(span)
        spannableText.removeSpan(span)

        val newSpan = object : URLSpan(span.url) {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        spannableText.setSpan(newSpan, start, end, 0)
    }
}
