package com.donglab.screennameviewer.compose.sample

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ComposeFavoritesScreen() {
    var favorites by remember { mutableStateOf(generateFavorites()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "즐겨찾기",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "즐겨찾기 항목들을 그리드 형태로 표시합니다.\nMaterial3의 Card와 Grid를 활용했습니다.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    favorites = favorites.toMutableList().apply {
                        add(generateRandomFavorite())
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("추가")
            }

            OutlinedButton(
                onClick = { favorites = emptyList() },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Clear, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("모두 삭제")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (favorites.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "즐겨찾기가 없습니다",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "새로운 즐겨찾기를 추가해보세요",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(favorites) { index, favorite ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier.size(40.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = favorite.category.color.copy(alpha = 0.2f)
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                favorite.category.icon,
                                                contentDescription = null,
                                                tint = favorite.category.color,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            favorites = favorites.toMutableList().apply {
                                                removeAt(index)
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = "삭제",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = favorite.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = favorite.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = favorite.rating.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class FavoriteItem(
    val title: String,
    val description: String,
    val category: FavoriteCategory,
    val rating: Float
)

data class FavoriteCategory(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: androidx.compose.ui.graphics.Color
)

private fun generateFavorites(): List<FavoriteItem> {
    return listOf(
        FavoriteItem(
            "Jetpack Compose",
            "안드로이드의 최신 UI 프레임워크",
            FavoriteCategory("기술", Icons.Filled.Build, androidx.compose.ui.graphics.Color.Blue),
            4.8f
        ),
        FavoriteItem(
            "Material Design 3",
            "구글의 디자인 시스템",
            FavoriteCategory("디자인", Icons.Filled.Create, androidx.compose.ui.graphics.Color.Green),
            4.7f
        ),
        FavoriteItem(
            "Kotlin",
            "안드로이드 공식 프로그래밍 언어",
            FavoriteCategory("언어", Icons.Filled.Info, androidx.compose.ui.graphics.Color.Red),
            4.9f
        ),
        FavoriteItem(
            "Android Studio",
            "안드로이드 개발 IDE",
            FavoriteCategory("도구", Icons.Filled.Build, androidx.compose.ui.graphics.Color.Magenta),
            4.6f
        ),
        FavoriteItem(
            "Navigation",
            "앱 내 화면 전환 라이브러리",
            FavoriteCategory("라이브러리", Icons.Filled.LocationOn, androidx.compose.ui.graphics.Color.Cyan),
            4.5f
        ),
        FavoriteItem(
            "Room Database",
            "로컬 데이터베이스 솔루션",
            FavoriteCategory("데이터", Icons.Filled.List, androidx.compose.ui.graphics.Color(0xFFFF9800)),
            4.4f
        )
    )
}

private fun generateRandomFavorite(): FavoriteItem {
    val categories = listOf(
        FavoriteCategory("기술", Icons.Filled.Build, androidx.compose.ui.graphics.Color.Blue),
        FavoriteCategory("디자인", Icons.Filled.Create, androidx.compose.ui.graphics.Color.Green),
        FavoriteCategory("언어", Icons.Filled.Info, androidx.compose.ui.graphics.Color.Red),
        FavoriteCategory("도구", Icons.Filled.Build, androidx.compose.ui.graphics.Color.Magenta)
    )

    val titles = listOf("새로운 기술", "유용한 도구", "훌륭한 라이브러리", "멋진 프레임워크")
    val descriptions = listOf(
        "개발에 도움이 되는 새로운 기술입니다.",
        "생산성을 높여주는 도구입니다.",
        "코드 품질을 개선해줍니다.",
        "개발 경험을 향상시킵니다."
    )

    return FavoriteItem(
        title = titles.random(),
        description = descriptions.random(),
        category = categories.random(),
        rating = listOf(3.5f, 4.0f, 4.2f, 4.5f, 4.8f, 5.0f).random()
    )
}