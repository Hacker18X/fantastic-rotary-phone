package ir.villetrade.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.villetrade.app.data.SessionStore
import ir.villetrade.app.data.Signal
import ir.villetrade.app.ui.screens.*
import ir.villetrade.app.ui.theme.ViliTheme

class MainActivity : ComponentActivity() {

    private lateinit var session: SessionStore

    private val notifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* no-op */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = SessionStore(applicationContext)
        requestNotifPermission()

        setContent {
            ViliTheme {
                var selectedSignal by remember { mutableStateOf<Signal?>(null) }
                var showLogin by remember { mutableStateOf(false) }
                val nav = rememberNavController()
                val backStack by nav.currentBackStackEntryAsState()
                val current = backStack?.destination?.route ?: "signals"

                if (showLogin) {
                    LoginScreen(
                        session = session,
                        onSuccess = { showLogin = false },
                        onSkip = { showLogin = false }
                    )
                } else if (selectedSignal != null) {
                    SignalDetailScreen(selectedSignal!!) { selectedSignal = null }
                } else {
                    Scaffold(
                        containerColor = Color(0xFF020617),
                        bottomBar = {
                            NavigationBar(containerColor = Color(0xFF0F172A)) {
                                NavigationBarItem(
                                    selected = current == "signals",
                                    onClick = { nav.navigate("signals") { launchSingleTop = true } },
                                    icon = { Icon(Icons.Default.Home, null) },
                                    label = { Text("سیگنال") }
                                )
                                NavigationBarItem(
                                    selected = current == "broadcasts",
                                    onClick = { nav.navigate("broadcasts") { launchSingleTop = true } },
                                    icon = { Icon(Icons.Default.Notifications, null) },
                                    label = { Text("اعلان") }
                                )
                                NavigationBarItem(
                                    selected = current == "profile",
                                    onClick = { nav.navigate("profile") { launchSingleTop = true } },
                                    icon = { Icon(Icons.Default.Person, null) },
                                    label = { Text("پروفایل") }
                                )
                            }
                        }
                    ) { padding ->
                        NavHost(
                            navController = nav,
                            startDestination = "signals",
                            modifier = Modifier.padding(padding)
                        ) {
                            composable("signals") {
                                SignalsScreen(onSignalClick = { selectedSignal = it })
                            }
                            composable("broadcasts") { BroadcastsScreen() }
                            composable("profile") {
                                ProfileScreen(
                                    session = session,
                                    onLogin = { showLogin = true },
                                    onLogout = { }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestNotifPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
