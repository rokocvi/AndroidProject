package hr.ferit.rokocvitkovic.firstproject.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class TeamViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val teamsData = mutableStateListOf<Team>()

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        db.collection("teams")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val team = document.toObject(Team::class.java)
                    teamsData.add(team)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("TeamViewModel", "Error getting documents: ", exception)
            }
    }
}
