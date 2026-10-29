package ir.villetrade.app.data

data class AppDataResponse(
    val signals: List<Signal> = emptyList(),
    val results: List<TradeResult> = emptyList()
)

data class Signal(
    val id: Int = 0,
    val category: String? = null,
    val asset: String? = null,
    val side: String? = null,
    val entry: String? = null,
    val stop_loss: String? = null,
    val targets: List<String>? = null,
    val leverage: String? = null,
    val note: String? = null,
    val status: String? = null,
    val featured: Int = 0,
    val created_at: String? = null,
    val title: String? = null
)

data class TradeResult(
    val id: Int = 0,
    val asset: String? = null,
    val result: String? = null,
    val pnl: String? = null,
    val created_at: String? = null,
    val note: String? = null
)

data class Plan(
    val id: Int = 0,
    val title: String? = null,
    val price: String? = null,
    val duration_days: Int = 0,
    val description: String? = null,
    val sku: String? = null,
    val is_active: Int = 1
)

data class Broadcast(
    val id: Int = 0,
    val title: String? = null,
    val message: String? = null,
    val created_at: String? = null
)

data class LoginRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String
)

data class LoginResponse(
    val token: String? = null,
    val user: UserProfile? = null,
    val error: String? = null
)

data class UserProfile(
    val user_key: String? = null,
    val full_name: String? = null,
    val email: String? = null,
    val phone: String? = null
)

data class SubscriptionStatus(
    val authenticated: Boolean = false,
    val subscription: SubInfo? = null,
    val proof: Any? = null
)

data class SubInfo(
    val plan_title: String? = null,
    val status: String? = null,
    val expires_at: String? = null
)

data class ApiError(val error: String? = null, val message: String? = null)
