package ir.villetrade.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.villetrade.app.data.Signal
import ir.villetrade.app.ui.components.InfoChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignalDetailScreen(signal: Signal, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(signal.asset ?: "جزئیات سیگنال") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF020617),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF020617)
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = (signal.side ?: "").uppercase(),
                color = if (signal.side.equals("buy", true) || signal.side.equals("long", true))
                    Color(0xFF22C55E) else Color(0xFFEF4444),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoChip("ورود", signal.entry)
                        InfoChip("حد ضرر", signal.stop_loss)
                        InfoChip("اهرم", signal.leverage)
                    }
                    if (!signal.targets.isNullOrEmpty()) {
                        HorizontalDivider(color = Color(0xFF1E293B))
                        Text("اهداف", color = Color(0xFF94A3B8), fontSize = 12.sp)
                        signal.targets.filter { it.isNotBlank() }.forEachIndexed { i, t ->
                            Text("TP${i + 1}: $t", color = Color.White, fontWeight = FontWeight.Medium)
                        }
                    }
                    if (!signal.note.isNullOrBlank()) {
                        HorizontalDivider(color = Color(0xFF1E293B))
                        Text("یادداشت", color = Color(0xFF94A3B8), fontSize = 12.sp)
                        Text(signal.note, color = Color.White)
                    }
                    if (!signal.created_at.isNullOrBlank()) {
                        Text(signal.created_at, color = Color(0xFF64748B), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
