package ir.villetrade.app.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.villetrade.app.data.Signal


@Composable
fun SignalCard(signal: Signal, onClick: () -> Unit) {
    val sideColor = when (signal.side?.lowercase()) {
        "buy", "long" -> Color(0xFF22C55E)
        "sell", "short" -> Color(0xFFEF4444)
        else -> Color(0xFF94A3B8)
    }
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = signal.asset ?: signal.title ?: "—",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    Modifier
                        .background(sideColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = (signal.side ?: signal.category ?: "").uppercase(),
                        color = sideColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InfoChip("ورود", signal.entry)
                InfoChip("حد ضرر", signal.stop_loss)
            }
            if (!signal.targets.isNullOrEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "اهداف: " + signal.targets.filter { it.isNotBlank() }.joinToString(" · "),
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
            if (!signal.created_at.isNullOrBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(signal.created_at, color = Color(0xFF64748B), fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun InfoChip(label: String, value: String?) {
    Column {
        Text(label, color = Color(0xFF64748B), fontSize = 11.sp)
        Text(value ?: "—", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}

@Composable
fun LoadingBox() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color(0xFF38BDF8))
    }
}

@Composable
fun ErrorBox(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message, color = Color(0xFFF87171))
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) { Text("تلاش مجدد") }
    }
}
