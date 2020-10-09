package com.example.myapplication.view

import android.content.Context
import android.graphics.*
import android.graphics.Shader.TileMode
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R


class SegmentedProgressBar(context: Context?, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {
    private var cornerRadius = 43f

    private val paint = Paint()
    var strokePaint = Paint()
    private var barContexts: List<BarContext> = listOf()


    private val segment = RectF(
        0f,
        0f,
        0f,
        height.toFloat()
    )


    override fun onDraw(canvas: Canvas) {
        var filling = 0f
        cornerRadius = resources.getInteger(R.integer.card_radius).toFloat()
        val barContextsLastIndex = barContexts.lastIndex
        barContexts.forEachIndexed { i, bar ->

            val shader: Shader = LinearGradient(
                0f, 0f, 5f, height.toFloat(),
                intArrayOf(bar.colorTo, bar.colorFrom), null, TileMode.CLAMP
            )

            paint.shader = shader

            segment.right = width * bar.percentage + filling
            segment.left = filling
            segment.bottom = height.toFloat()
            strokePaint.style = Paint.Style.STROKE
            strokePaint.color = Color.BLACK
            strokePaint.strokeWidth = 10f
            var topLeftRadius = 0f
            var bottomLeftRadius = 0f
            var topRightRadius = 0f
            var bottomRightRadius = 0f
            when (i) {
                0 -> {
                    topLeftRadius = cornerRadius
                    bottomLeftRadius = cornerRadius
                }
                barContextsLastIndex -> {
                    topRightRadius = cornerRadius
                    bottomRightRadius = cornerRadius
                }
            }

            canvas.drawPath(
                getPathOfRoundedRectF(
                    segment,
                    topLeftRadius = topLeftRadius,
                    bottomLeftRadius = bottomLeftRadius,
                    topRightRadius = topRightRadius,
                    bottomRightRadius = bottomRightRadius
                ), paint
            )

            strokePaint.shader = LinearGradient(
                0f,
                0f,
                0f,
                height.toFloat(),
                intArrayOf(Color.parseColor("#80FFFFFF"), Color.parseColor("#33000000")),
                null,
                TileMode.CLAMP
            )
            strokePaint.maskFilter = BlurMaskFilter(18f, BlurMaskFilter.Blur.INNER)
            strokePaint.strokeWidth = 12f
            canvas.drawPath(
                getPathOfRoundedRectF(
                    segment,
                    topLeftRadius = topLeftRadius + topLeftRadius * 0.2f,
                    bottomLeftRadius = bottomLeftRadius + bottomLeftRadius * 0.2f,
                    topRightRadius = topRightRadius + topRightRadius * 0.2f,
                    bottomRightRadius = bottomRightRadius + bottomRightRadius * 0.2f,
                    excludeVertical = true
                ),
                strokePaint
            )

            filling += width * bar.percentage
        }
    }

    fun setContexts(barContexts: List<BarContext>) {
        this.barContexts = barContexts
    }

    private fun getPathOfRoundedRectF(
        rect: RectF,
        topLeftRadius: Float = 0f,
        topRightRadius: Float = 0f,
        bottomRightRadius: Float = 0f,
        bottomLeftRadius: Float = 0f,
        excludeVertical: Boolean = false
    ): Path {
        val tlRadius = topLeftRadius.coerceAtLeast(0f)
        val trRadius = topRightRadius.coerceAtLeast(0f)
        val brRadius = bottomRightRadius.coerceAtLeast(0f)
        val blRadius = bottomLeftRadius.coerceAtLeast(0f)

        with(Path()) {
            moveTo(rect.left + tlRadius, rect.top)


            //setup top border
            lineTo(rect.right - trRadius, rect.top)

            //setup top-right corner
            arcTo(
                RectF(
                    rect.right - trRadius * 2f,
                    rect.top,
                    rect.right,
                    rect.top + trRadius * 2f
                ), -90f, 90f
            )
            if (trRadius != 0f || !excludeVertical) {
                //setup right border
                lineTo(rect.right, rect.bottom - brRadius)

                //setup bottom-right corner
                arcTo(
                    RectF(
                        rect.right - brRadius * 2f,
                        rect.bottom - brRadius * 2f,
                        rect.right,
                        rect.bottom
                    ), 0f, 90f
                )
            } else {
                moveTo(rect.right, rect.bottom - brRadius)
            }

            //setup bottom border
            lineTo(rect.left + blRadius, rect.bottom)

            //setup bottom-left corner
            arcTo(
                RectF(
                    rect.left,
                    rect.bottom - blRadius * 2f,
                    rect.left + blRadius * 2f,
                    rect.bottom
                ), 90f, 90f
            )

            if (tlRadius != 0f || !excludeVertical) {
                //setup left border
                lineTo(rect.left, rect.top + tlRadius)

                //setup top-left corner
                arcTo(
                    RectF(
                        rect.left,
                        rect.top,
                        rect.left + tlRadius * 2f,
                        rect.top + tlRadius * 2f
                    ),
                    180f,
                    90f
                )
            } else {
                moveTo(rect.left, rect.top + tlRadius)
            }

            return this
        }
    }


    class BarContext(val colorFrom: Int, val colorTo: Int, val percentage: Float)
}