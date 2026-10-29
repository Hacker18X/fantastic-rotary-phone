package ir.villetrade.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.villetrade.app.data.ApiClient
import ir.villetrade.app.data.Broadcast
import ir.villetrade.app.ui.components.ErrorBox
import ir.villetrade.app.ui.components.LoadingBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastsScreen() {
    var items by remember { mutableStateOf<List<Broadcast>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    fun load() {
        scope.launch {
            loading = true
            error = null
            try {
                items = ApiClient.api.getBroadcasts()
            } catch (e: Exception) {
                error = e.message ?: "خطا"
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) { load() }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("اعلان‌ها", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF020617),
                titleContentColor = Color.White
            )
        )
        when {
            loading -> LoadingBox()
            error != null -> ErrorBox(error!!) { load() }
            items.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("اعلانی نیست", color = Color(0xFF94A3B8))
            }
            else -> LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(items) { b ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text(b.title ?: "اعلان", fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(Modifier.height(6.dp))
                            Text(b.message ?: "", color = Color(0xFFCBD5E1), fontSize = 13.sp)
                            if (!b.created_at.isNullOrBlank()) {
                                Spacer(Modifier.height(6.dp))
                                Text(b.created_at, color = Color(0xFF64748B), fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
