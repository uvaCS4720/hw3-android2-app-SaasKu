package edu.nd.pmcburne.hwapp.one.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import edu.nd.pmcburne.hwapp.one.model.Game
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.nd.pmcburne.hwapp.one.viewmodel.GameViewModel

/***************************************************************************************
 * REFERENCES
 * Title:  Pull to refresh
 * Author: Android Developers
 * URL:https://developer.android.com/develop/ui/compose/components/pull-to-refresh
 * Software License: Apache 2 License
 * Usage: I used this to component for the pull to refresh for my app
 ***************************************************************************************/

/***************************************************************************************
 * REFERENCE
 * Title: Date pickers
 * Author: Android Developers
 * URL: https://developer.android.com/develop/ui/compose/components/datepickers
 * Software License: Apache 2 License
 *
 * Usage: I used the example code for reference to do the calendar
 ***************************************************************************************/

/***************************************************************************************
 * REFERENCE
 * Title: Side-effects in Compose
 * Author: Android Developers
 * URL: https://developer.android.com/develop/ui/compose/side-effects#disposableeffect
 * Software License: Apache 2 License
 *
 * Usage: I used this code to make an event so that connecting to wifi from offline refreshes the app
 ***************************************************************************************/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val gender by viewModel.gn.collectAsStateWithLifecycle()
    var showPicker by remember { mutableStateOf(false) }
    val selectedDate by viewModel.ds.collectAsStateWithLifecycle()
    val formatter = java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val refreshState = rememberPullToRefreshState()
    val initialized by viewModel.initialized.collectAsStateWithLifecycle()
    val isLoading by viewModel.ld.collectAsStateWithLifecycle()
    val games by viewModel.gl.collectAsStateWithLifecycle()
    val isOn by viewModel.isOn.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                viewModel.setOnline(true)
            }
            override fun onLost(network: android.net.Network) {
                viewModel.setOnline(false)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callback)
        val net = connectivityManager.activeNetwork
        val cap = connectivityManager.getNetworkCapabilities(net)
        viewModel.setOnline(cap?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
        onDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "NCAA Basketball Scores",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (!isOn) {
                Card(
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "You are offline. You can still access previously stored games by clicking dates you've accessed before, but please connect to WiFi to access new games or update existing game data.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date: ${selectedDate.format(formatter)}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { showPicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Change Date")
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Division:",
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    FilterChip(
                        selected = gender == "men",
                        onClick = { viewModel.setGender("men")},
                        label = { Text("Men's") },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    FilterChip(
                        selected = gender == "women",
                        onClick = { viewModel.setGender("women")},
                        label = { Text("Women's") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pull to refresh info!",
                    style = MaterialTheme.typography.titleMedium
                )
            }



            if (showPicker) {
                DatePickerModal(
                    selectedDate = selectedDate,
                    onDateSelected = { viewModel.setDate(it)},
                    onDismiss = { showPicker = false }
                )
            }
            PullToRefreshBox(
                isRefreshing = isLoading,
                onRefresh = { viewModel.loadGames() },
                state = refreshState,
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    !initialized -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Pull down to load games.")
                        }
                    }
                    games.isEmpty() && !isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No games found for this date.")
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(games, key = { "${it.id}_${it.gender}" }) { game ->
                                GCard(game = game)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val newDate = Instant.ofEpochMilli(millis)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()
                    onDateSelected(newDate)
                }
                onDismiss()
            },
                colors = ButtonDefaults.textButtonColors(
                contentColor = Color.Black
            )
                ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black
                )) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

// TODO: CITE THIS SOURCE
// https://developer.android.com/develop/ui/compose/side-effects#disposableeffect

@Composable
fun GCard(game: Game){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {




            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Away",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }






            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = game.away,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    if (game.gameState != "pre") {
                        Text(
                            text = game.awayScore?.toString() ?: "-",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    when (game.gameState) {
                        "pre" -> Text(text = game.startTime, style = MaterialTheme.typography.bodyMedium)
                        "live" -> {
                            var periodDisplay = ""
                            periodDisplay = if (game.currentPeriod == "HALFTIME") "HT" else game.currentPeriod
                            Text(text = "Period: $periodDisplay", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Clock: ${game.contestClock}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Playtime left: ${game.calculateTimeRemaining()}", style = MaterialTheme.typography.bodySmall)
                        }
                        "final" -> Text(text = "FINAL", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = game.home,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    if (game.gameState != "pre") {
                        Text(
                            text = game.homeScore?.toString() ?: "-",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }

            if (game.gameState == "final") {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Winner: ${if (game.homeWon) game.home else game.away}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }
        }
    }
}