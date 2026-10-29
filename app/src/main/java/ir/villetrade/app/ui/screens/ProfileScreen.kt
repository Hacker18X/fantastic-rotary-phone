package ir.villetrade.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.villetrade.app.data.ApiClient
import ir.villetrade.app.data.SessionStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(session: SessionStore, onLogin: () -> Unit, onLogout: () -> Unit) {
    val token by session.token.collectAsState(initial = null)
    val name by session.fullName.collectAsState(initial = null)
    val email by session.email.collectAsState(initial = null)
    val userKey by session.userKey.collectAsState(initial = null)
    var subText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(userKey) {
        if (!userKey.isNullOrBlank()) {
            try {
                val st = ApiClient.api.subscriptionStatus(userKey!!)
                subText = st.subscription?.let {
                    "${it.plan_title ?: "اشتراک"} — ${it.status ?: ""} — تا ${it.expires_at ?: "—"}"
                } ?: "بدون اشتراک فعال"
            } catch (_: Exception) {
                subText = null
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("پروفایل", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF020617),
                titleContentColor = Color.White
            )
        )
        Column(Modifier.padding(16.dp)) {
            if (token.isNullOrBlank()) {
                Text("وارد حساب نشده‌اید", color = Color(0xFF94A3B8))
                Spacer(Modifier.height(12.dp))
                Button(onClick = onLogin, modifier = Modifier.fillMaxWidth()) {
                    Text("ورود / ثبت‌نام")
                }
            } else {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(name ?: "کاربر", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                        Spacer(Modifier.height(4.dp))
                        Text(email ?: "", color = Color(0xFF94A3B8))
                        if (subText != null) {
                            Spacer(Modifier.height(12.dp))
                            Text(subText!!, color = Color(0xFF38BDF8), fontSize = 13.sp)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            session.clear()
                            onLogout()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("خروج از حساب", color = Color(0xFFF87171))
                }
            }
        }
    }
}
