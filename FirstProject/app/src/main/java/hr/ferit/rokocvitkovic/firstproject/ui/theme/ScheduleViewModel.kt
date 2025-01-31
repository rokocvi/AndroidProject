package hr.ferit.rokocvitkovic.firstproject.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ScheduleViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val scheduleData = mutableStateListOf<MatchSchedule>()

    init {
        fetchSchedules()
    }

    private fun fetchSchedules() {
        db.collection("schedules")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val schedule = document.toObject(MatchSchedule::class.java)
                    scheduleData.add(schedule)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ScheduleViewModel", "Error getting schedules: ", exception)
            }
    }
}
