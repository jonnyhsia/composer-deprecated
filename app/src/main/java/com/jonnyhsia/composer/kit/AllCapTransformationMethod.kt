package com.jonnyhsia.composer.kit

import android.text.method.ReplacementTransformationMethod

class AllCapTransformationMethod : ReplacementTransformationMethod() {

    /** 被替代的小写字符与空白格 */
    private val lower = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ')
    /** 替代的大写字符与_ */
    private val upper = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_')

    override fun getOriginal() = lower

    override fun getReplacement() = upper
}  