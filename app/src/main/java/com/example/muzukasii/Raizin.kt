package com.example.muzukasii

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RaimonHeaderBand(
    modifier: Modifier = Modifier,
    height: Dp = 20.dp,
    backgroundColor: Color = Color(0xFFE02020),
    patternColor: Color = Color.White,
    strokeWidth: Dp = 2.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        drawRect(color = backgroundColor)

        val sw = strokeWidth.toPx()
        val unitSize = size.height  // 1モチーフの幅 = 高さと同じ
        val gap = unitSize * 0.12f  // モチーフ間のギャップ
        val motifWidth = unitSize + gap
        val count = (size.width / motifWidth).toInt() + 1

        repeat(count) { i ->
            val offsetX = i * motifWidth
            drawRaimonMotif(
                offsetX = offsetX,
                size = unitSize,
                color = patternColor,
                strokeWidth = sw
            )
        }
    }
}

private fun DrawScope.drawRaimonMotif(
    offsetX: Float,
    size: Float,
    color: Color,
    strokeWidth: Float,
) {
    val s = size
    val p = strokeWidth / 2f
    val stroke = Stroke(
        width = strokeWidth,
        cap = StrokeCap.Square,
        join = StrokeJoin.Miter
    )

    // 外枠（上辺→右辺→下辺→左辺）
    // 開口部: 左上（渦の入口）
    val outer = Path().apply {
        moveTo(offsetX + s * 0.5f, p)           // 上辺中央から開始
        lineTo(offsetX + s - p, p)              // 上辺右端
        lineTo(offsetX + s - p, s - p)          // 右辺下
        lineTo(offsetX + p, s - p)              // 下辺左
        lineTo(offsetX + p, p)                  // 左辺上
        lineTo(offsetX + s * 0.5f, p)           // 上辺中央へ戻る（開口）
    }
    drawPath(outer, color = color, style = stroke)

    // 二段目（外枠から1段内側、逆向き）
    val m = s * 0.2f  // マージン
    val inner1 = Path().apply {
        moveTo(offsetX + s * 0.5f, m + p)
        lineTo(offsetX + m + p, m + p)
        lineTo(offsetX + m + p, s - m - p)
        lineTo(offsetX + s - m - p, s - m - p)
        lineTo(offsetX + s - m - p, m + p)
        lineTo(offsetX + s * 0.5f, m + p)
    }
    drawPath(inner1, color = color, style = stroke)

    // 三段目（さらに内側、中心へ向かう線）
    val m2 = s * 0.4f
    drawLine(
        color = color,
        start = Offset(offsetX + s * 0.5f, m2),
        end = Offset(offsetX + s * 0.5f, s * 0.5f),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Square
    )
    drawLine(
        color = color,
        start = Offset(offsetX + m2, s * 0.5f),
        end = Offset(offsetX + s * 0.5f, s * 0.5f),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Square
    )
}