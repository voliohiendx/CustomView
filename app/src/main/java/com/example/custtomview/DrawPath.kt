package com.example.custtomview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawPath
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var measure = PathMeasure()
    private var pathScreen = Path()
    private var pathNotch = Path()
    private var paddingView = 20f
    private var space2Point = 100f
    var modeScreen = ModeScreen.NOTCH_BUNNY_EAR

    private val iconBitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.instagram)
    }

    private val rectIconBitmap: Rect by lazy {
        Rect(0, 0, iconBitmap.width, iconBitmap.height)
    }


    init {
        backgroundPaint.apply {
            color = Color.parseColor("#B0000000")
            strokeWidth = 20f
            style = Paint.Style.STROKE
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        anim()
    }

    fun anim() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                rotate += 5
                delay(16)
                postInvalidate()
            }
        }
    }

    val paint = Paint()
    var shader: LinearGradient? = null

    private var pos = FloatArray(2)
    private var distance = 0f
    private var rotate = 0f
    private var matrix2 = Matrix()
    private val rectDstCutBitmap = Rect()

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        shader = LinearGradient(
            0f, 0f, 0f, height.toFloat(), Color.RED, Color.BLUE, Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (modeScreen == ModeScreen.NOTCH_IP_14) {
            drawNotch()
        }
        drawPath()
        measure = PathMeasure(pathScreen, false)
        val mm = measure.length / ((measure.length / space2Point).toInt())
        distance = 0f
        while (true) {
            if (distance > measure.length) break
            measure.getPosTan(distance, pos, null)
            // canvas?.drawCircle(pos[0], pos[1], 10f, backgroundPaint)
//            rectDstCutBitmap.set(
//                (pos[0] - perSize / 2).toInt(),
//                (pos[1] - perSize / 2).toInt(),
//                (pos[0] + perSize / 2).toInt(),
//                (pos[1] + perSize / 2).toInt()
//            )
//            canvas?.drawBitmap(iconBitmap, rectIconBitmap, rectDstCutBitmap, null)
            distance += mm
        }

        paint.shader = shader
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas?.drawPath(pathScreen, backgroundPaint)
        if (modeScreen == ModeScreen.NOTCH_IP_14) {
            canvas?.drawPath(pathNotch, backgroundPaint)
        }
        canvas?.save()
        matrix2.setRotate(rotate, width / 2f, height / 2f)
        canvas?.setMatrix(matrix2)
        canvas?.drawCircle(width / 2f, height / 2f, 2000f, paint)
        canvas?.restore()
//
    }

    fun drawLineByModeScreen(mode: ModeScreen) {
        modeScreen = mode
        when (modeScreen) {
            ModeScreen.NOTCH_DROPLETS -> {
                radiusCornerScreen = 40f
                radiusCornerNotchTop = 30f
                radiusCornerNotchBottom = 40f
                spaceNotchWTop = 80f
                spaceNotchH = 70f
                invalidate()
            }
            ModeScreen.NOTCH_BUNNY_EAR -> {
                radiusCornerScreen = 40f
                radiusCornerNotchTop = 30f
                radiusCornerNotchBottom = 40f
                spaceNotchWTop = 300f
                spaceNotchH = 120f
                invalidate()
            }
            ModeScreen.NON -> {
                radiusCornerScreen = 40f
                radiusCornerNotchTop = 0f
                radiusCornerNotchBottom = 0f
                spaceNotchWTop = 0f
                spaceNotchH = 0f
                invalidate()
            }

            ModeScreen.NOTCH_IP_14 -> {
                radiusCornerScreen = 40f
                radiusCornerNotchTop = 0f
                radiusCornerNotchBottom = 0f
                spaceNotchWTop = 0f
                spaceNotchH = 0f

                radiusNotch = 40f
                spaceNotch14W = 400f
                spaceNotch14H = 70f
                invalidate()
            }
        }
    }

    private var radiusCornerScreen = 40f
    private var radiusCornerNotchTop = 30f
    private var radiusCornerNotchBottom = 40f
    private var spaceNotchWTop = 80f
    private var spaceNotchWBottom = 80f
    private var spaceNotchH = 70f

    private fun drawPath() {
        pathScreen.reset()

        pathScreen.apply {
            moveTo(paddingView + radiusCornerScreen, paddingView)
            lineTo(width / 2f - spaceNotchWTop / 2f - radiusCornerNotchTop, paddingView)
            quadTo(
                width / 2f - spaceNotchWTop / 2f,
                paddingView,
                width / 2f - spaceNotchWTop / 2f,
                paddingView + spaceNotchH / 2f
            )
            quadTo(
                width / 2f - spaceNotchWTop / 2f,
                paddingView + spaceNotchH,
                width / 2f - spaceNotchWTop / 2f + radiusCornerNotchBottom,
                paddingView + spaceNotchH
            )
            lineTo(
                width / 2f + spaceNotchWTop / 2f - radiusCornerNotchBottom, paddingView + spaceNotchH
            )
            quadTo(
                width / 2f + spaceNotchWTop / 2f,
                paddingView + spaceNotchH,
                width / 2f + spaceNotchWTop / 2f,
                paddingView + spaceNotchH / 2f
            )

            quadTo(
                width / 2f + spaceNotchWTop / 2f,
                paddingView,
                width / 2f + spaceNotchWTop / 2f + radiusCornerNotchTop,
                paddingView
            )
            lineTo(width - paddingView - radiusCornerScreen, paddingView)
            quadTo(
                width - paddingView,
                paddingView,
                width - paddingView,
                paddingView + radiusCornerScreen
            )
            lineTo(width - paddingView, height - paddingView - radiusCornerScreen)
            quadTo(
                width - paddingView,
                height - paddingView,
                width - paddingView - radiusCornerScreen,
                height - paddingView
            )
            lineTo(paddingView + radiusCornerScreen, height - paddingView)
            quadTo(
                paddingView,
                height - paddingView,
                paddingView,
                height - paddingView - radiusCornerScreen
            )
            lineTo(paddingView, paddingView + radiusCornerScreen)
            quadTo(paddingView, paddingView, paddingView + radiusCornerScreen, paddingView)
            close()
        }
    }

    private var radiusNotch = 40f
    private var spaceNotch14W = 400f
    private var spaceNotch14H = 70f
    private var pointNotch: PointF = PointF(350f, 500f)

    fun drawNotch() {
        pathNotch.reset()

        pathNotch.moveTo(pointNotch.x, pointNotch.y - spaceNotch14H / 2)
        pathNotch.lineTo(
            pointNotch.x + spaceNotch14W / 2 - radiusNotch, pointNotch.y - spaceNotch14H / 2
        )
        pathNotch.quadTo(
            pointNotch.x + spaceNotch14W / 2,
            pointNotch.y - spaceNotch14H / 2,
            pointNotch.x + spaceNotch14W / 2,
            pointNotch.y - spaceNotch14H / 2 + radiusNotch
        )
        pathNotch.lineTo(
            pointNotch.x + spaceNotch14W / 2, pointNotch.y + spaceNotch14H / 2 - radiusNotch
        )
        pathNotch.quadTo(
            pointNotch.x + spaceNotch14W / 2,
            pointNotch.y + spaceNotch14H / 2,
            pointNotch.x + spaceNotch14W / 2 - radiusNotch,
            pointNotch.y + spaceNotch14H / 2
        )
        pathNotch.lineTo(
            pointNotch.x - spaceNotch14W / 2 + radiusNotch, pointNotch.y + spaceNotch14H / 2
        )
        pathNotch.quadTo(
            pointNotch.x - spaceNotch14W / 2,
            pointNotch.y + spaceNotch14H / 2,
            pointNotch.x - spaceNotch14W / 2,
            pointNotch.y + spaceNotch14H / 2 - radiusNotch
        )
        pathNotch.lineTo(
            pointNotch.x - spaceNotch14W / 2, pointNotch.y - spaceNotch14H / 2 + radiusNotch
        )

        pathNotch.quadTo(
            pointNotch.x - spaceNotch14W / 2,
            pointNotch.y - spaceNotch14H / 2,
            pointNotch.x - spaceNotch14W / 2 + radiusNotch,
            pointNotch.y - spaceNotch14H / 2
        )
        pathNotch.close()
    }

    fun setRabbitEarsWidth(per: Float) {
        val width = per * width * 0.6f
        val check = if (radiusCornerNotchBottom < radiusCornerNotchTop) {
            radiusCornerNotchBottom
        } else radiusCornerNotchTop

        if (check <= width) {
            spaceNotchWTop = width
            setUpperBorderCurvature(perUpperBorder, false)
            setBottomBorderCurvature(perBottomBorder, false)
            invalidate()
        }
    }

    fun setRabbitEarsHeight(per: Float) {
        val height = per * height / 7
        val check = if (radiusCornerNotchBottom < radiusCornerNotchTop) {
            radiusCornerNotchBottom
        } else radiusCornerNotchTop

        if (check <= height) {
            spaceNotchH = height
            setUpperBorderCurvature(perUpperBorder, false)
            setBottomBorderCurvature(perBottomBorder, false)
            invalidate()
        }
    }

    var perUpperBorder = 1f
    fun setUpperBorderCurvature(per: Float, invalidate: Boolean = true) {
        perUpperBorder = per
        radiusCornerNotchTop= per * spaceNotchWTop
//        radiusCornerNotchTop = if (spaceNotchH > spaceNotchW) {
//            per * spaceNotchW
//        } else {
//            per * spaceNotchH
//        }
        if (invalidate) invalidate()
    }

    var perBottomBorder = 1f
    fun setBottomBorderCurvature(per: Float, invalidate: Boolean = false) {
        perBottomBorder = per
        radiusCornerNotchBottom = if (spaceNotchH > spaceNotchWTop) {
            per * spaceNotchWTop * 0.5f
        } else {
            per * spaceNotchH * 0.5f
        }

        if (invalidate) invalidate()
    }

    fun setXNotch14(per: Float) {
        pointNotch.x = per * width
        invalidate()
    }

    fun setYNotch14(per: Float) {
        pointNotch.y = per * height

        invalidate()
    }

    fun setWightNotch14(per: Float) {
        spaceNotch14W = per * width * 0.4f
        setRadiusNotch14(perNotchBorder, false)
        invalidate()
    }

    fun setHeightNotch14(per: Float) {
        spaceNotch14H = per * height * 0.4f
        setRadiusNotch14(perNotchBorder, false)
        invalidate()
    }

    var perNotchBorder = 1f
    fun setRadiusNotch14(per: Float, invalidate: Boolean = true) {
        perNotchBorder = per
        radiusNotch = if (spaceNotch14H > spaceNotch14W) {
            per * spaceNotch14W * 0.5f
        } else {
            per * spaceNotch14H * 0.5f
        }
        if (invalidate) invalidate()
    }

    var perSize = 1f
    fun setBorderSizeLightning(per: Float) {
        perSize = per * 100f
        invalidate()
    }

    fun setRadiusLightning(per: Float) {
        radiusCornerScreen = per * width * 0.4f
        invalidate()
    }

}