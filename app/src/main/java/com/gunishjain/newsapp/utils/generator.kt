package com.gunishjain.newsapp.utils

import com.gunishjain.newsapp.data.model.Article
import java.security.MessageDigest

fun generateUniqueHash(article: Article): String {
    // Concatenate article attributes to form the input string
    val input = "${article.source}${article.title}${article.url}"
    // Create a SHA-256 digest instance
    val digest = MessageDigest.getInstance("SHA-256")
    // Apply the digest to the input bytes
    val hashBytes = digest.digest(input.toByteArray())

    // Convert the byte array to a hexadecimal string
    val hexString = StringBuilder()
    for (byte in hashBytes) {
        val hex = Integer.toHexString(0xff and byte.toInt())
        if (hex.length == 1) {
            hexString.append('0')
        }
        hexString.append(hex)
    }
    return hexString.toString()
}