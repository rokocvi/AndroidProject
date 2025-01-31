package hr.ferit.rokocvitkovic.firstproject.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.text.get

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

fun addPlayerToFirestore(player: Player) {
    val db = FirebaseFirestore.getInstance()
    val playerRef = db.collection("players")


    playerRef.add(player)
        .addOnSuccessListener {
            Log.d("Firestore", "Player added successfully!")
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error adding player: $e")
        }
}

fun deletePlayerFromFirestore(player: Player) {
    val db = FirebaseFirestore.getInstance()
    db.collection("players")
        .whereEqualTo("firstName", player.firstName)
        .whereEqualTo("lastName", player.lastName)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                db.collection("players").document(document.id).delete()
            }
        }
}


