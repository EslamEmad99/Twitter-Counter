package com.halan.domain.use_case.main

import com.halan.domain.use_case.util.Constants.TCO_URL_LENGTH
import com.halan.domain.use_case.util.Mockable
import java.util.regex.Pattern
import javax.inject.Inject

@Mockable
class CountTweetCharactersUseCase @Inject constructor() {

    operator fun invoke(tweet: String): Int {
        var characterCount = 0
        val words = tweet.split("\\s+".toRegex())

        for (word in words) {
            characterCount += when {
                isUrl(word) -> TCO_URL_LENGTH
                containsComplexUnicode(word) -> word.codePointCount(0, word.length)
                containsComplexScript(word) -> word.length
                else -> word.length
            }
        }

        // Count the spaces between words
        val spaceCount = words.size - 1
        characterCount += spaceCount

        return characterCount
    }

    private fun isUrl(word: String): Boolean {
        val urlPattern = Pattern.compile("(https?://\\S+|www\\.\\S+)")
        return urlPattern.matcher(word).matches()
    }

    private fun containsComplexUnicode(word: String): Boolean {
        val complexUnicodePattern = Pattern.compile("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+|[\\u2600-\\u27BF]+")
        return complexUnicodePattern.matcher(word).find()
    }

    private fun containsComplexScript(word: String): Boolean {
        val complexScriptPattern = Pattern.compile("[\\u4E00-\\u9FFF]+|[\\u3040-\\u309F]+|[\\u30A0-\\u30FF]+")
        return complexScriptPattern.matcher(word).find()
    }
}
