package com.example.muzukasii

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

//検索バー
//検索結果画面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(viewModel: SearchViewModel = viewModel()) {
    val shopList = viewModel.shopList
    val totalAvailable = viewModel.totalAvailable
    val context = LocalContext.current
    val locationService = remember { LocationService(context) }

    // 位置取得してVMに渡す共通処理
    fun fetchLocationAndSearch() {
        locationService.getLastLocation { location ->
            viewModel.updateLocation(location?.latitude, location?.longitude)
            viewModel.search()
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray),
            ) {
                Column {
                    TopAppBar(
                        title = {
                            Column(
                                modifier = Modifier
                                    .padding(top = 30.dp, start = 15.dp)
                                    .windowInsetsPadding(WindowInsets.statusBars)
                            ) {
                                Text("店舗を探す", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    "ご利用店舗を選択してください",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = viewModel.text,
                            onValueChange = { viewModel.onTextChange(it) }, // ✅
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = { fetchLocationAndSearch() }     // ✅
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .background(
                                    Color(0xFFECEFF1),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                OutlinedTextFieldDefaults.DecorationBox(
                                    value = viewModel.text,
                                    innerTextField = innerTextField,
                                    enabled = true,
                                    singleLine = true,
                                    visualTransformation = VisualTransformation.None,
                                    interactionSource = remember { MutableInteractionSource() },
                                    leadingIcon = {
                                        Icon(Icons.Default.Search, contentDescription = null)
                                    },
                                    placeholder = {
                                        Text("店舗を検索", fontSize = 14.sp, color = Color.Gray)
                                    },
                                    contentPadding = PaddingValues(
                                        horizontal = 8.dp,
                                        vertical = 0.dp
                                    ),
                                    container = {
                                        OutlinedTextFieldDefaults.Container(
                                            enabled = true,
                                            isError = false,
                                            interactionSource = remember { MutableInteractionSource() },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedBorderColor = Color(0xFFBBBBBB),
                                                unfocusedBorderColor = Color(0xFFBBBBBB)
                                            ),
                                            shape = RoundedCornerShape(20.dp),
                                            focusedBorderThickness = 1.dp,
                                            unfocusedBorderThickness = 1.dp
                                        )
                                    }
                                )
                            }
                        )
                    }

                    RaimonHeaderBand()

                    Text(
                        text = "現在地からの距離",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 2.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        items(viewModel.rangeOptions.indices.toList()) { index ->
                            val option = viewModel.rangeOptions[index]
                            val isSelected = viewModel.selectedRangeIndex == index // ✅
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) Color(0xFFC0392B) else Color.White,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) Color(0xFF922B21) else Color(0xFFE8C5BC)
                                ),
                                modifier = Modifier.clickable {
                                    locationService.getLastLocation { location ->
                                        viewModel.updateLocation(
                                            location?.latitude,
                                            location?.longitude
                                        )
                                        viewModel.onRangeSelected(index) // ✅
                                    }
                                }
                            ) {
                                Text(
                                    text = option.first,
                                    fontSize = 12.sp,
                                    color = if (isSelected) Color.White else Color(0xFF555555),
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 7.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            item {
                Text(
                    text = "近くの店舗 - ${totalAvailable}件",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 4.dp)
                )
            }

            itemsIndexed(shopList) { index, shop ->
                ShopList(shop = shop)
                if (index == shopList.lastIndex && shopList.size < totalAvailable) {
                    LaunchedEffect(shopList.size) {
                        viewModel.loadMore() // ✅ 引数なし
                    }
                }
            }
        }
    }
}


//リストカード
//詳細
@Composable
fun ShopList(shop: Shop) {
    var showSheet by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { showSheet = true },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(shop.photo?.mobile?.l ?: shop.photo?.pc?.l)
                    .crossfade(true)
                    .size(320, 240)
                    .build(),
                contentDescription = shop.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                error = painterResource(id = android.R.drawable.ic_menu_gallery),
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = shop.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
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

