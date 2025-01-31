package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ResultsScreen(viewModel: ResultsViewModel = viewModel()) {

    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


    val results = viewModel.resultsData.sortedByDescending { result ->
        dateFormat.parse(result.date)
    }

    var selectedFilter by remember { mutableStateOf("Sve") }
    val filters = listOf("Sve", "Kod kuće", "U gostima")


    val filteredResults = when (selectedFilter) {
        "Kod kuće" -> results.filter { it.homeTeam == "Nk Graničar Klakar" }
        "U gostima" -> results.filter { it.awayTeam == "Nk Graničar Klakar" }
        else -> results
    }

    Column(modifier = Modifier.fillMaxSize()) {

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


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(filteredResults) { result ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = Color(0xFFF5F5F5)
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(modifier = Modifier.weight(2f)) {

                        val homeColor = when {
                            result.homeScore > result.awayScore -> Color(0xFF4CAF50)
                            result.homeScore < result.awayScore -> Color.Black
                            else -> Color.Gray
                        }


                        val awayColor = when {
                            result.awayScore > result.homeScore -> Color(0xFF4CAF50)
                            result.awayScore < result.homeScore -> Color.Black
                            else -> Color.Gray
                        }


                        Text(
                            text = result.homeTeam,
                            fontWeight = FontWeight.Bold,
                            color = homeColor
                        )
                        Text(
                            text = result.awayTeam,
                            fontWeight = FontWeight.Bold,
                            color = awayColor
                        )


                        Text(
                            text = "Lokacija: ${result.location}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }


                    Text(
                        text = "${result.homeScore} : ${result.awayScore}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )


                    Text(
                        text = result.date,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}


