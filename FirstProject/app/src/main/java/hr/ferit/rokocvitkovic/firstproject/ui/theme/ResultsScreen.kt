package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    // Definiranje SimpleDateFormat za parsiranje datuma
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    // Sortiranje rezultata prema datumu (od najnovijeg prema najstarijem)
    val results = viewModel.resultsData.sortedByDescending { result ->
        dateFormat.parse(result.date) // Parsiranje stringa u Date
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(results) { result ->
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
                // Timovi, rezultat i lokacija
                Column(modifier = Modifier.weight(2f)) {
                    // Boja za homeTeam
                    val homeColor = when {
                        result.homeScore > result.awayScore -> Color(0xFF4CAF50) // Zelena za pobjedu
                        result.homeScore < result.awayScore -> Color.Black // Neutralna za poraz
                        else -> Color.Gray // Neutralna za neriješeno
                    }

                    // Boja za awayTeam
                    val awayColor = when {
                        result.awayScore > result.homeScore -> Color(0xFF4CAF50) // Zelena za pobjedu
                        result.awayScore < result.homeScore -> Color.Black // Neutralna za poraz
                        else -> Color.Gray // Neutralna za neriješeno
                    }

                    // Prikaz naziva timova
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

                    // Prikaz lokacije utakmice
                    Text(
                        text = "Lokacija: ${result.location}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Prikaz rezultata
                Text(
                    text = "${result.homeScore} : ${result.awayScore}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // Datum
                Text(
                    text = result.date,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
