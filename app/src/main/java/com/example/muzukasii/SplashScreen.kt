package com.example.muzukasii

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

//タイトル画面※ホットペッパーのクレジット表記あり
@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var showCredit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)       // ロゴ表示後少し待つ
        showCredit = true  // クレジットをスライドイン
        delay(2000)      // クレジット表示後待つ
        onFinished()     // ホームへ遷移
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ramen),
                contentDescription = "ロゴ",
                modifier = Modifier.height(120.dp)
            )

            AnimatedVisibility(
                visible = showCredit,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.hotpepper_m),
                        contentDescription = "Powered by ホットペッパーグルメ Webサービス",
                        modifier = Modifier
                            .height(70.dp)
                            .width(176.dp)
                    )
                }
            }
        }
    }
}