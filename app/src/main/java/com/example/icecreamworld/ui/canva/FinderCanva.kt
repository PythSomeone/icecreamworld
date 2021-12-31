package com.example.icecreamworld.ui.canva

import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.unit.sp
import com.example.icecreamworld.ui.theme.CanvasBrown

@Composable
fun IceCreamWorldCanvas() {
    val pxValue = LocalDensity.current.run { 50.dp.toPx() }
    val strokePxValue = LocalDensity.current.run { 2.dp.toPx() }
    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = pxValue
        color = Color.rgb(181,120,97)
        strokeWidth = strokePxValue
        strokeMiter = strokePxValue - 1
        strokeJoin = android.graphics.Paint.Join.ROUND
        textAlign = android.graphics.Paint.Align.CENTER
    }

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = pxValue
        color = Color.TRANSPARENT
        textAlign = android.graphics.Paint.Align.CENTER
    }


    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "Ice",
                    center.x.minus(380),
                    80.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "Ice",
                    center.x.minus(380),
                    80.dp.toPx(),
                    textPaint
                )
                it.nativeCanvas.drawText(
                    "Cream",
                    center.x.minus(80),
                    80.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "Cream",
                    center.x.minus(80),
                    80.dp.toPx(),
                    textPaint
                )
                it.nativeCanvas.drawText(
                    "World",
                    center.x.plus(290),
                    80.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "World",
                    center.x.plus(290),
                    80.dp.toPx(),
                    textPaint
                )
            }
        }
    )
}