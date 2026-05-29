package com.example.muzukasii

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onBannerClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val locationService = remember { LocationService(context) }

    // 位置取得してVMに渡すだけ
    LaunchedEffect(Unit) {
        locationService.getLastLocation { location ->
            viewModel.updateLocation(location?.latitude, location?.longitude)
        }
    }

    Scaffold(
        topBar = { HomeHeader() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.gekikara),
                    contentDescription = "激辛好きおすすめ店舗",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(16.dp)
                        .clickable { onBannerClick() }
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                // VMのリストをそのまま渡す
                ShopSection(
                    title = "現在地に近いラーメン屋",
                    shopList = viewModel.shopList
                )
            }
        }
    }
}

// ヘッダー（変更なし）
@Composable
fun HomeHeader() {
    Surface(
        color = Color.White,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ramen),
                contentDescription = "ロゴ",
                modifier = Modifier.height(50.dp)
            )
        }
    }
}

// リストを受け取るだけ、自分では何も取得しない
@Composable
fun ShopSection(
    title: String,
    shopList: List<Shop>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(shopList) { shop ->
                HomeShopCard(shop = shop)
            }
        }
    }
}

// カード（変更なし）
@Composable
fun HomeShopCard(shop: Shop) {
    var showSheet by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(9.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(160.dp)
            .padding(4.dp)
            .clickable { showSheet = true }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(shop.photo?.mobile?.l)
                    .crossfade(true)
                    .size(320, 240)
                    .build(),
                contentDescription = shop.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = shop.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                shop.access?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    if (showSheet) {
        ShopDetails(
            shop = shop,
            onDismiss = { showSheet = false }
        )
    }
}