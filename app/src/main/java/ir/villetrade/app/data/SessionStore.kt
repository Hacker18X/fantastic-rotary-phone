package ir.villetrade.app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("vili_session")

class SessionStore(private val context: Context) {
    private val tokenKey = stringPreferencesKey("token")
    private val userKeyKey = stringPreferencesKey("user_key")
    private val nameKey = stringPreferencesKey("full_name")
    private val emailKey = stringPreferencesKey("email")

    val token: Flow<String?> = context.dataStore.data.map { it[tokenKey] }
    val userKey: Flow<String?> = context.dataStore.data.map { it[userKeyKey] }
    val fullName: Flow<String?> = context.dataStore.data.map { it[nameKey] }
    val email: Flow<String?> = context.dataStore.data.map { it[emailKey] }

    suspend fun saveSession(token: String, user: UserProfile) {
        context.dataStore.edit {
            it[tokenKey] = token
            it[userKeyKey] = user.user_key ?: ""
            it[nameKey] = user.full_name ?: ""
            it[emailKey] = user.email ?: ""
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
