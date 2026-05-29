package com.example.muzukasii

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RecommendedShop(
    val name: String,
    val comment: String,
    val description: String,
    val imageRes: Int,
    val spicyLevel: Int,
    val url: String,
)

//おすすめ一覧の中身
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedScreen(onBack: () -> Unit, onCardClick: (String) -> Unit) {

    val shops = listOf(
        RecommendedShop(
            name = "一風堂 梅田店",
            comment = "ここがおすすめ",
            description = "ここ数年に出たからか麺、期間限定で替え玉も辛くなったり？山椒の辛さが好きな方にはおすすめ！",
            imageRes = R.drawable.shop_ippudo,
            url = "https://www.hotpepper.jp/strJ000449898/",
            spicyLevel = 2
        ),
        RecommendedShop(
            name = "来来亭　十三店",
            comment = "ここがおすすめ",
            description = "期間限定だった旨辛麺、いまとなっては十数年も続いていていつまでも変わらずにおいしい！辛いのが得意な人はMAXに挑戦してみても...？",
            imageRes = R.drawable.shop_kanekyuemon,
            url = "https://www.hotpepper.jp/strJ000743744/",
            spicyLevel = 4
        ),
        RecommendedShop(
            name = "井の庄 天満",
            comment = "ここがおすすめ",
            description = "カップラーメンの辛辛魚が店舗で食べられる！魚介類が好きな方にはおすすめ！",
            imageRes = R.drawable.shop_karakarauo,
            url = "https://tabelog.com/osaka/A2701/A270103/27151024/",
            spicyLevel = 3
        ),

        )


    Scaffold(
        topBar = {
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "激辛好きおすすめ店舗",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(shops) { shop ->
                RecommendedCard(shop = shop, onCardClick = onCardClick)
            }
        }
    }
}


//おすすめ店舗のカード
@Composable
fun RecommendedCard(shop: RecommendedShop, onCardClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCardClick(shop.url) }
    ) {
        //画像
        Image(
            painter = painterResource(id = shop.imageRes),
            contentDescription = shop.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.22f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 10.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(5) { index ->
                Text(
                    text = "🌶️",
                    fontSize = 14.sp,
                    color = if (index < shop.spicyLevel) Color.White
                    else Color.White.copy(alpha = 0.25f)
                )
            }
        }

        //グラデーションオーバーレイ
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.72f)
                        ),
                        startY = 300f
                    )
                )
        )

        //テキスト
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = shop.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = shop.comment,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = shop.description,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.75f),
                lineHeight = 18.sp
            )
        }
    }
}