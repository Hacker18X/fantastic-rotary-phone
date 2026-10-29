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
import ir.villetrade.app.data.Signal
import ir.villetrade.app.ui.components.ErrorBox
import ir.villetrade.app.ui.components.LoadingBox
import ir.villetrade.app.ui.components.SignalCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignalsScreen(onSignalClick: (Signal) -> Unit) {
    var signals by remember { mutableStateOf<List<Signal>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var category by remember { mutableStateOf("all") }
    val scope = rememberCoroutineScope()

    fun load() {
        scope.launch {
            loading = true
            error = null
            try {
                val data = ApiClient.api.getAppData()
                signals = data.signals
            } catch (e: Exception) {
                error = e.message ?: "خطا در دریافت داده"
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) { load() }

    val filtered = when (category) {
        "all" -> signals
        else -> signals.filter { it.category.equals(category, ignoreCase = true) }
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("سیگنال‌ها", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF020617),
                titleContentColor = Color.White
            )
        )
        ScrollableTabRow(
            selectedTabIndex = listOf("all", "futures", "spot", "forex", "free").indexOf(category).coerceAtLeast(0),
            containerColor = Color(0xFF020617),
            edgePadding = 12.dp
        ) {
            listOf(
                "all" to "همه",
                "futures" to "فیوچرز",
                "spot" to "اسپات",
                "forex" to "فارکس",
                "free" to "رایگان"
            ).forEach { (id, label) ->
                Tab(
                    selected = category == id,
                    onClick = { category = id },
                    text = { Text(label, fontSize = 13.sp) }
                )
            }
        }

        when {
            loading -> LoadingBox()
            error != null -> ErrorBox(error!!) { load() }
            filtered.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("سیگنالی موجود نیست", color = Color(0xFF94A3B8))
            }
            else -> LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered, key = { it.id }) { s ->
                    SignalCard(s) { onSignalClick(s) }
                }
            }
        }
    }
}
