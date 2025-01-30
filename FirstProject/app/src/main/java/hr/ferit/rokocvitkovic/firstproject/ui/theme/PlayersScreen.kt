package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import hr.ferit.rokocvitkovic.firstproject.R



@Composable
fun PlayersScreen(viewModel: PlayerViewModel) {
    val players = viewModel.playersData

    var selectedFilter by remember { mutableStateOf("Sve") }
    val filters: List<String> = listOf("Sve", "Strijelci", "Asistenti", "Minutaža")
    var searchQuery by remember { mutableStateOf("") }
    var showAddPlayerFields by remember { mutableStateOf(false) } // Kontrola prikaza polja

    val positionOrder = listOf("Golman", "Obrana", "Vezni", "Napadač")

    val filteredPlayers = players.filter { player ->
        player.firstName.contains(searchQuery, ignoreCase = true) ||
                player.lastName.contains(searchQuery, ignoreCase = true)
    }

    // Varijable za unos novog igrača
    var newPlayerName by remember { mutableStateOf("") }
    var newPlayerPosition by remember { mutableStateOf("") }
    var newPlayerGoals by remember { mutableStateOf("") }
    var newPlayerAssists by remember { mutableStateOf("") }
    var newPlayerMinutes by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Pretraživač
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pretraži igrače") },
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo NK Graničar Klakar",
                modifier = Modifier.size(30.dp)
            )
        }

        // Filtriranje igrača i prikaz
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Filter:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            FilterDropdownMenu(
                selectedFilter = selectedFilter,
                filters = filters,
                onFilterSelected = { filter -> selectedFilter = filter }
            )
        }

        val playersForDisplay: List<Player> = when (selectedFilter) {
            "Strijelci" -> filteredPlayers.sortedByDescending { it.goals }
            "Asistenti" -> filteredPlayers.sortedByDescending { it.assists }
            "Minutaža" -> filteredPlayers.sortedByDescending { it.minutesPlayed }
            else -> filteredPlayers
        }

        LazyColumn(
            modifier = Modifier.weight(1f) // LazyColumn zauzima preostali prostor
        ) {
            if (selectedFilter == "Sve") {
                val groupedPlayers = playersForDisplay.groupBy { it.position }
                positionOrder.forEach { position ->
                    val playersInPosition = groupedPlayers[position]
                    if (!playersInPosition.isNullOrEmpty()) {
                        item {
                            Text(
                                text = position,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                                    .padding(8.dp),
                                color = Color.White
                            )
                        }
                        items(playersInPosition.size) { index ->
                            PlayerCard(playersInPosition[index], selectedFilter)
                        }
                    }
                }
            } else {
                items(playersForDisplay.size) { index ->
                    PlayerCard(playersForDisplay[index], selectedFilter)
                }
            }
        }

        // Gumb za prikaz/skrivanje polja za unos novog igrača
        Button(
            onClick = { showAddPlayerFields = !showAddPlayerFields },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(if (showAddPlayerFields) "Sakrij unos" else "Dodaj novog igrača")
        }

        // Polja za unos igrača (prikazuju se samo ako je gumb pritisnut)
        if (showAddPlayerFields) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp) // Unutarnji padding za bolji UI
            ) {
                TextField(
                    value = newPlayerName,
                    onValueChange = { newPlayerName = it },
                    label = { Text("Ime i prezime") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = newPlayerPosition,
                    onValueChange = { newPlayerPosition = it },
                    label = { Text("Pozicija") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = newPlayerGoals,
                        onValueChange = { newPlayerGoals = it },
                        label = { Text("Golovi") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )

                    TextField(
                        value = newPlayerAssists,
                        onValueChange = { newPlayerAssists = it },
                        label = { Text("Asistencije") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                TextField(
                    value = newPlayerMinutes,
                    onValueChange = { newPlayerMinutes = it },
                    label = { Text("Minute") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Gumb za potvrdu dodavanja igrača
                Button(
                    onClick = {
                        val player = Player(
                            firstName = newPlayerName.split(" ")[0],
                            lastName = newPlayerName.split(" ").getOrElse(1) { "" },
                            godina = 20,
                            position = newPlayerPosition,
                            goals = newPlayerGoals.toIntOrNull() ?: 0,
                            assists = newPlayerAssists.toIntOrNull() ?: 0,
                            minutesPlayed = newPlayerMinutes.toIntOrNull() ?: 0
                        )
                        addPlayerToFirestore(player)

                        // Resetiraj polja nakon dodavanja
                        newPlayerName = ""
                        newPlayerPosition = ""
                        newPlayerGoals = ""
                        newPlayerAssists = ""
                        newPlayerMinutes = ""
                        showAddPlayerFields = false // Sakrij formu nakon dodavanja
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Spremi Igrača")
                }
            }
        }
    }
}






@Composable
fun PlayerCard(player: Player, selectedFilter: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val imagePainter = R.drawable.player

        Image(
            painter = painterResource(id = R.drawable.player),
            contentDescription = player.position,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${player.firstName} ${player.lastName}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Godine: ${player.godina}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Pozicija: ${player.position}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        when (selectedFilter) {
            "Strijelci" -> Text(
                text = "Golovi: ${player.goals}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            "Asistenti" -> Text(
                text = "Asistencije: ${player.assists}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            "Minutaža" -> Text(
                text = "Minute: ${player.minutesPlayed}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun FilterDropdownMenu(
    selectedFilter: String,
    filters: List<String>,
    onFilterSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        TextButton(onClick = { expanded = true }) {
            Text(text = selectedFilter, fontSize = 16.sp, color = Color.Black)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            filters.forEach { filter ->
                DropdownMenuItem(
                    onClick = {
                        onFilterSelected(filter)
                        expanded = false
                    },
                    text = {
                        Text(text = filter, fontSize = 16.sp)
                    }
                )
            }
        }
    }
}


