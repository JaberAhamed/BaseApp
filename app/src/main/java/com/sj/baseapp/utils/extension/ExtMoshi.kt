@file:OptIn(ExperimentalStdlibApi::class)

package com.sj.baseapp.utils.extension
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import java.net.URLDecoder
import java.net.URLEncoder

object MoshiUtil {
    fun getMoshi(): Moshi = Moshi.Builder()
        .build()
}

/**
 * Note for Navigation Component for Compose:
 * If the data has special character (eg., + etc.), the url encode-decode will not work properly.
 * For example, the "+" will be replaced with a space on decode.
 * Because Navigation Component auto convert "%2B" with "+",
 * so on decode it replace "+" by space.
 */
inline fun <reified T> String?.getObjFromJson(urlDecode: Boolean = true): T? {
    if (this == null) return null

    val jsonAdapter = MoshiUtil.getMoshi().adapter<T>().lenient()

    val result = jsonAdapter.fromJson(
        if (urlDecode) {
            this.urlDecode()
        } else {
            this
        }
    )

    return result
}

/**
 * Note for Navigation Component for Compose:
 * If the data has special character (eg., + etc.), the url encode-decode will not work properly.
 * For example, the "+" will be replaced with a space on decode.
 * Because Navigation Component auto convert "%2B" with "+",
 * so on decode it replace "+" by space.
 */
inline fun <reified T> T?.getJsonFromObj(urlEncode: Boolean = true): String? {
    if (this == null) return null

    val jsonAdapter = MoshiUtil.getMoshi().adapter<T>().lenient()

    return jsonAdapter.toJson(this).let { json ->
        val result = if (urlEncode) {
            json.urlEncode()
        } else {
            json
        }

        result
    }
}

fun String.urlEncode(): String = URLEncoder.encode(this, "utf-8")

fun String.urlDecode(): String = URLDecoder.decode(this, "utf-8")