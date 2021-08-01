package com.jwhh.notekeeper

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.color_selector.view.*

class ColorSelector @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private var listOfColors = listOf(Color.BLUE, Color.RED, Color.GREEN)
    private var selectedColorIndex = 0

    init {
        orientation = LinearLayout.HORIZONTAL
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        back_arrow.setOnClickListener {
            selectPreviousColor()
        }
        forward_arrow.setOnClickListener {
            selectNextColor()
        }

        colorEnabled.setOnCheckedChangeListener{ buttonView, isChecked ->
            broadcastColor()
        }
    }

    private fun selectNextColor() {
        if (selectedColorIndex == listOfColors.lastIndex) {
            selectedColorIndex = 0
        } else {
            selectedColorIndex++
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor()


    }

    private fun selectPreviousColor() {
        if (selectedColorIndex == 0) {
            selectedColorIndex = listOfColors.lastIndex
        } else {
            selectedColorIndex--
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor()
    }

    private var colorSelectedListener: ColorSelectedListner? = null

    interface ColorSelectedListner {
        fun onColorSelected(color: Int)
    }

    fun setColorSelectedListener(listener: ColorSelectedListner){
        this.colorSelectedListener = listener
    }

    fun setSelectedColor(color: Int){
        var index = listOfColors.indexOf(color)
        if(index == -1){
            colorEnabled.isChecked = false
            index = 0
        }else{
            colorEnabled.isChecked = true
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
    }

    private fun broadcastColor() {
        val color = if (colorEnabled.isChecked) listOfColors[selectedColorIndex] else Color.TRANSPARENT
        this.colorSelectedListener?.onColorSelected(color)
    }
}