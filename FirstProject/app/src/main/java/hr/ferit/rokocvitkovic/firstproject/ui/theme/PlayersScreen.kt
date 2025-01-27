package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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


    val positionOrder = listOf("Golman", "Obrana", "Vezni", "Napadač")

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Filter:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo NK Graničar Klakar",
                modifier = Modifier.size(30.dp)
            )
            FilterDropdownMenu(
                selectedFilter = selectedFilter,
                filters = filters,
                onFilterSelected = { filter -> selectedFilter = filter }
            )
        }


        val filteredPlayers: List<Player> = when (selectedFilter) {
            "Strijelci" -> players.sortedByDescending { it.goals }
            "Asistenti" -> players.sortedByDescending { it.assists }
            "Minutaža" -> players.sortedByDescending { it.minutesPlayed }
            else -> players
        }

        if (selectedFilter == "Sve") {
            // Grupisanje i prikaz igrača po pozicijama
            val groupedPlayers = filteredPlayers.groupBy { it.position }

            LazyColumn {
                positionOrder.forEach { position ->
                    val playersInPosition = groupedPlayers[position]

                    if (!playersInPosition.isNullOrEmpty()) {
                        // Naslov za grupu
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
                        // Prikaz igrača u toj poziciji
                        items(playersInPosition.size) { index ->
                            PlayerCard(playersInPosition[index], selectedFilter)
                        }
                    }
                }
            }
        } else {
            // Prikaz igrača samo sortirano (bez grupisanja)
            LazyColumn {
                items(filteredPlayers.size) { index ->
                    PlayerCard(filteredPlayers[index], selectedFilter)
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


