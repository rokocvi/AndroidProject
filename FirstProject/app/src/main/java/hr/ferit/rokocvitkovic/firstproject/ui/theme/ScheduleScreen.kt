package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel = viewModel()) {
    val schedules = viewModel.scheduleData
    var selectedFilter by remember { mutableStateOf("Sve") }
    val filters = listOf("Sve", "Domaće", "Gostujuće")
    val homeTeamName = "Nk Graničar Klakar"

    val filteredSchedules = when (selectedFilter) {
        "Domaće" -> schedules.filter { it.homeTeam == homeTeamName }
        "Gostujuće" -> schedules.filter { it.awayTeam == homeTeamName }
        else -> schedules
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Filter:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            FilterDropdownMenu(selectedFilter, filters) { selectedFilter = it }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredSchedules) { schedule ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = "${schedule.homeTeam} vs ${schedule.awayTeam}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF2E7D32)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = schedule.date, fontSize = 14.sp, color = Color.Gray)
                        Text(text = schedule.time, fontSize = 14.sp, color = Color.Gray)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = schedule.location, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.End)
                    }
                }
            }
        }
    }
}


