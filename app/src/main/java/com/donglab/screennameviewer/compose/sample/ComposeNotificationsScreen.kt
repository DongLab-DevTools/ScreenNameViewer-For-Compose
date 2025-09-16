package com.donglab.screennameviewer.compose.sample

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ComposeNotificationsScreen() {
    val accentColor = androidx.compose.ui.graphics.Color(0xFF2196F3)
    var notifications by remember { mutableStateOf(generateNotifications()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
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
                            Icons.Filled.Notifications,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "알림 목록",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Material3 디자인의 알림 화면입니다.\n각 알림을 스와이프하여 관리할 수 있습니다.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        notifications = notifications.toMutableList().apply {
                            add(0, generateRandomNotification())
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("새 알림")
                }

                OutlinedButton(
                    onClick = { notifications = emptyList() },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.Clear, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("모두 삭제")
                }
            }
        }

        if (notifications.isEmpty()) {
            item {
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
                            text = "알림이 없습니다",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        itemsIndexed(notifications) { index, notification ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (notification.isRead)
                        MaterialTheme.colorScheme.surface
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.size(40.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = notification.type.color.copy(alpha = 0.2f)
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                notification.type.icon,
                                contentDescription = null,
                                tint = notification.type.color,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = notification.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.Bold
                        )
                        Text(
                            text = notification.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = notification.time,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(
                        onClick = {
                            notifications = notifications.toMutableList().apply {
                                removeAt(index)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "삭제",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

data class NotificationItem(
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

data class NotificationType(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: androidx.compose.ui.graphics.Color
)

private fun generateNotifications(): List<NotificationItem> {
    val accentColor = androidx.compose.ui.graphics.Color(0xFF2196F3)
    return listOf(
        NotificationItem(
            "새 메시지",
            "안녕하세요! 새로운 메시지가 도착했습니다.",
            "방금 전",
            NotificationType("message", Icons.Filled.Email, accentColor),
            false
        ),
        NotificationItem(
            "시스템 업데이트",
            "앱이 최신 버전으로 업데이트되었습니다.",
            "1시간 전",
            NotificationType("system", Icons.Filled.Settings, accentColor),
            true
        ),
        NotificationItem(
            "할인 혜택",
            "특별 할인 이벤트가 시작되었습니다!",
            "2시간 전",
            NotificationType("promotion", Icons.Filled.ShoppingCart, accentColor),
            false
        ),
        NotificationItem(
            "친구 요청",
            "새로운 친구 요청이 있습니다.",
            "어제",
            NotificationType("social", Icons.Filled.Person, accentColor),
            true
        )
    )
}

private fun generateRandomNotification(): NotificationItem {
    val accentColor = androidx.compose.ui.graphics.Color(0xFF2196F3)
    val types = listOf(
        NotificationType("message", Icons.Filled.Email, accentColor),
        NotificationType("system", Icons.Filled.Settings, accentColor),
        NotificationType("promotion", Icons.Filled.ShoppingCart, accentColor),
        NotificationType("social", Icons.Filled.Person, accentColor)
    )

    val titles = listOf("새 알림", "중요한 업데이트", "특별 혜택", "시스템 메시지")
    val messages = listOf(
        "새로운 내용이 업데이트되었습니다.",
        "확인이 필요한 사항이 있습니다.",
        "특별한 제안을 확인해보세요.",
        "시스템에서 알려드립니다."
    )

    return NotificationItem(
        title = titles.random(),
        message = messages.random(),
        time = "방금 전",
        type = types.random(),
        isRead = false
    )
}