package hr.ferit.rokocvitkovic.firstproject.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class PlayerViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val playersData = mutableStateListOf<Player>()

    init {
        fetchPlayers()
    }

    private fun fetchPlayers() {
        db.collection("players")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val player = document.toObject(Player::class.java)
                    playersData.add(player)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PlayerViewModel", "Error getting documents: ", exception)
            }
    }
}

