package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(schedules) { schedule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(2f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${schedule.homeTeam} vs ${schedule.awayTeam}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF2E7D32)
                    )
                }


                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = schedule.date,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = schedule.time,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }


                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = schedule.location,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}
