package ru.rpuxa.stockpile.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ru.rpuxa.stockpile.R

class RoundImageView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var baseImage: Bitmap? = null
    var frameColor = ContextCompat.getColor(context, R.color.defaultFrameColor)
    var frameWidthPercent = 0.1f
    var hasFrame = false
        set(value) {
            field = value
            invalidate()
        }

    fun setImage(image: Bitmap) {
        baseImage = image
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(heightMeasureSpec, heightMeasureSpec)
    }

    private val paint = Paint()
    private val rectF = RectF()

    override fun onDraw(canvas: Canvas) {
        val baseImage = baseImage ?: return
        val radius = (height / 2).toFloat()
        if (hasFrame) {
            paint.color = frameColor
            canvas.drawCircle(radius, radius, radius, paint)
        }
        val frameWidth = frameWidthPercent * radius
        rectF.set(frameWidth, frameWidth, 2 * radius - frameWidth, 2 * radius - frameWidth)
        canvas.drawBitmap(
            baseImage,
            null,
            rectF,
            null
        )
    }
}