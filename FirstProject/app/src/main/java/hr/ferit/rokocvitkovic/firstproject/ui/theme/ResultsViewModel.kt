package hr.ferit.rokocvitkovic.firstproject.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ResultsViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val resultsData = mutableStateListOf<MatchResult>()

    init {
        fetchResults()
    }

    private fun fetchResults() {
        db.collection("results")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val matchResult = document.toObject(MatchResult::class.java)
                    resultsData.add(matchResult)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ResultsViewModel", "Error fetching results: ", exception)
            }
    }
}
