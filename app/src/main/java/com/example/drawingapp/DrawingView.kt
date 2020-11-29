package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context : Context, attrs : AttributeSet) : View(context,attrs) {
    private var  mDrawpath : CustomPath? = null
    private var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint : Paint? = null
    private var mCanvasPaint : Paint? = null
    private var mBrushSize : Float = 0.toFloat()
    private var color : Int = Color.BLACK
    private var canvas : Canvas? = null
    private val mpaths = ArrayList<CustomPath>()

    init{
        setupDrawing()
    }
    private fun setupDrawing(){
        mDrawPaint = Paint()
        mDrawpath = CustomPath(color,mBrushSize)
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        //mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)

    }
// change canvas to canvas? if fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)
        for(path in mpaths){
            mDrawPaint!!.strokeWidth = path.brushThickness
            mDrawPaint!!.color = path.color
            canvas.drawPath(path,mDrawPaint!!)
        }

        if(!mDrawpath!!.isEmpty){
            mDrawPaint!!.strokeWidth = mDrawpath!!.brushThickness
            mDrawPaint!!.color = mDrawpath!!.color
            canvas.drawPath(mDrawpath!!,mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                mDrawpath!!.color = color
                mDrawpath!!.brushThickness = mBrushSize

                mDrawpath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawpath!!.moveTo(touchX,touchY)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawpath!!.lineTo(touchX,touchY)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                mpaths.add(mDrawpath!!)
                mDrawpath = CustomPath(color,mBrushSize)
            }else-> return false

        }

        invalidate()
        return true

    }
    fun setSizeforBrush(newsize : Float){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newsize,resources.displayMetrics)
        mDrawPaint!!.strokeWidth = mBrushSize
    }
    fun setcolor(newcolor : String){
        color = Color.parseColor(newcolor)
        mDrawPaint!!.color = color
    }
    internal inner class CustomPath(var color : Int, var brushThickness : Float) : Path(){

    }
}