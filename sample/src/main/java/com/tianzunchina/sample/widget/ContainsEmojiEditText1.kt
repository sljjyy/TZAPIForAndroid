package com.tianzunchina.sample.widget

import android.content.Context
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher
import android.util.AttributeSet
import com.tianzunchina.android.api.log.TZToastTool

/**
 * Created by Administrator on 2017/5/31.
 */
class ContainsEmojiEditText1 : android.support.v7.widget.AppCompatEditText {
    //输入表情前的光标位置
    private var cursorPos: Int = 0
    //输入表情前EditText中的文本
    private var inputAfterText: String? = null
    //是否重置了EditText的内容
    private var resetText: Boolean = false

    private var mContext: Context? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        initEditText()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        initEditText()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        initEditText()
    }

    // 初始化edittext 控件
    private fun initEditText() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!resetText) {
                    cursorPos = selectionEnd
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString()
                }

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!resetText) {
                    if (count - before >= 2) {//表情符号的字符长度最小为2
                        val input = s.subSequence(cursorPos, cursorPos + count)
                        if (containsEmoji(input.toString())) {
                            resetText = true
                            TZToastTool.mark("不支持输入Emoji表情符号")
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            setText(inputAfterText)
                            val text = text
                            if (text is Spannable) {
                                Selection.setSelection(text, text.length)
                            }
                        }
                    }
                } else {
                    resetText = false
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    companion object {


        /**
         * 检测是否有emoji表情

         * @param source
         * *
         * @return
         */
        fun containsEmoji(source: String): Boolean {
            val len = source.length
            for (i in 0..len - 1) {
                val codePoint = source[i]
                if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否是Emoji

         * @param codePoint 比较的单个字符
         * *
         * @return
         */
        private fun isEmojiCharacter(codePoint: Char): Boolean {
            return codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA ||
                    codePoint.toInt() == 0xD || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF ||
                    codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF
        }
    }

}
