package com.example.icecreamworld.ui.canva

import android.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun IceCreamWorldCanvas() {
    val pxValue = LocalDensity.current.run { 50.dp.toPx() }
    val strokePxValue = LocalDensity.current.run { 2.dp.toPx() }
    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = pxValue
        color = Color.rgb(181, 120, 97)
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

    val config = LocalConfiguration.current

    val screenWidth = config.screenWidthDp

    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            val iceX: Int
            val creamX: Int
            val worldX: Int
            val iceY: Int
            val creamY: Int
            val worldY: Int
            if (screenWidth > 390) {
                iceX = 380
                creamX = 80
                worldX = 290
                iceY = 80
                creamY = 80
                worldY = 80
            } else {
                iceX = 120
                creamX = -60
                worldX = 0
                iceY = 40
                creamY = 40
                worldY = 90
            }
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "Ice",
                    center.x.minus(iceX),
                    iceY.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "Ice",
                    center.x.minus(iceX),
                    iceY.dp.toPx(),
                    textPaint
                )
                it.nativeCanvas.drawText(
                    "Cream",
                    center.x.minus(creamX),
                    creamY.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "Cream",
                    center.x.minus(creamX),
                    creamY.dp.toPx(),
                    textPaint
                )
                it.nativeCanvas.drawText(
                    "World",
                    center.x.plus(worldX),
                    worldY.dp.toPx(),
                    textPaintStroke
                )
                it.nativeCanvas.drawText(
                    "World",
                    center.x.plus(worldX),
                    worldY.dp.toPx(),
                    textPaint
                )
            }
        }
    )
}