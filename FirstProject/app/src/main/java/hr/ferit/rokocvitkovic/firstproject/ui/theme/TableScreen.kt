package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.ferit.rokocvitkovic.firstproject.R

@Composable
fun TableScreen(viewModel: TeamViewModel) {
    var selectedFilter by remember { mutableStateOf("Sve") }
    val filters = listOf("Sve", "Doma", "Gosti")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Filter:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo NK Graničar Klakar",
                modifier = Modifier.size(30.dp)
            )
            DropdownMenuFilter(selectedFilter, filters) { filter ->
                selectedFilter = filter
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        TableContent(viewModel.teamsData, filter = selectedFilter)
    }
}


@Composable
fun DropdownMenuFilter(selectedFilter: String, filters: List<String>, onFilterSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        Button(onClick = { expanded = true }) {
            Text(text = selectedFilter)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filters.forEach { filter ->
                DropdownMenuItem(
                    text = { Text(filter) },
                    onClick = {
                        onFilterSelected(filter)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun TableContent(teams: List<Team>, filter: String) {
    val filteredTeams = when (filter) {
        "Doma" -> teams.sortedWith(
            compareByDescending<Team> { it.homePoints }
                .thenByDescending { it.goalDiffHome }
        )
        "Gosti" -> teams.sortedWith(
            compareByDescending<Team> { it.awayPoints }
                .thenByDescending { it.goalDiffAway }
        )
        
        else -> teams.sortedWith(
            compareByDescending<Team> { it.points }
                .thenByDescending { it.goalDiff }
        )
    }

    Column {

        Row(
            Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "#", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "Klub", fontWeight = FontWeight.Bold, modifier = Modifier.weight(3f))
            Text(text = "P", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "W", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "D", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "L", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "+/-", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(text = "B", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }


        filteredTeams.forEachIndexed { index, team ->
            val data = when (filter) {
                "Doma" -> listOf(
                    team.homeGames, team.winsHome, team.drawHome, team.loseHome,
                    "${team.goalDiffHome}", team.homePoints
                )
                "Gosti" -> listOf(
                    team.awayGames, team.winsAway, team.drawAway, team.loseAway,
                    "${team.goalDiffAway}", team.awayPoints
                )
                else -> listOf(
                    team.played, team.wins, team.draws, team.losses,
                    team.goalDiff, team.points
                )
            }

            val played = data[0]
            val wins = data[1]
            val draws = data[2]
            val losses = data[3]
            val goalDiff = data[4]
            val points = data[5]

            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${index + 1}", modifier = Modifier.weight(1f))
                Text(team.name, modifier = Modifier.weight(3f))
                Text("$played", modifier = Modifier.weight(1f))
                Text("$wins", modifier = Modifier.weight(1f))
                Text("$draws", modifier = Modifier.weight(1f))
                Text("$losses", modifier = Modifier.weight(1f))
                Text(goalDiff.toString(), modifier = Modifier.weight(1f))
                Text("$points", modifier = Modifier.weight(1f))
            }
        }
    }
}
