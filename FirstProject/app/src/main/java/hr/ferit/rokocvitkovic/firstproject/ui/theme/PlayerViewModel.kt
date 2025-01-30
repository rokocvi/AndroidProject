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

fun addPlayerToFirestore(player: Player) {
    val db = FirebaseFirestore.getInstance()
    val playerRef = db.collection("players")  // Kolekcija 'players' u Firestore

    // Dodavanje novog dokumenta u kolekciju 'players'
    playerRef.add(player)
        .addOnSuccessListener {
            // Ovdje možete obraditi uspješan unos
            Log.d("Firestore", "Player added successfully!")
        }
        .addOnFailureListener { e ->
            // Ovdje možete obraditi grešku
            Log.e("Firestore", "Error adding player: $e")
        }
}

