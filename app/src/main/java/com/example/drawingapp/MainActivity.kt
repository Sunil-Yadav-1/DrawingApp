package com.example.drawingapp

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*


class MainActivity : AppCompatActivity() {
    private var mimagebutton : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawing_view.setSizeforBrush(20.toFloat())
        mimagebutton = ll_color_pallets[0] as ImageButton
        mimagebutton!!.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.color_pallet_pressed)
        )
        bs_btn.setOnClickListener { showBrushsizeChooserDialog() }

    }

    private fun showBrushsizeChooserDialog(){
        val brushdialog = Dialog(this)
        brushdialog.setContentView(R.layout.dialog_brush_size)
        brushdialog.setTitle("Brush size:")
        val button_small = brushdialog.ib_small
        val button_medium = brushdialog.ib_medium
        val button_large = brushdialog.ib_large
        button_small.setOnClickListener{
            drawing_view.setSizeforBrush(10.toFloat())
            brushdialog.dismiss()

        }
        button_medium.setOnClickListener{
            drawing_view.setSizeforBrush(20.toFloat())
            brushdialog.dismiss()
        }
        button_large.setOnClickListener{
            drawing_view.setSizeforBrush(30.toFloat())
            brushdialog.dismiss()
        }
        brushdialog.show()
    }

    fun paintclicked(view : View){
        if(view != mimagebutton){
            val imagebutton = view as ImageButton
            val colortag = view.tag.toString()
            drawing_view.setcolor(colortag)
            imagebutton.setImageDrawable(
                    ContextCompat.getDrawable(this,R.drawable.color_pallet_pressed)
            )
            mimagebutton!!.setImageDrawable(
                    ContextCompat.getDrawable(this,R.drawable.color_pallet_normal)
            )
            mimagebutton = view
        }
    }


}