package hr.ferit.rokocvitkovic.firstproject.ui.theme

import androidx.annotation.DrawableRes

/*data class Player(
    val firstName: String,
    val lastName: String,
    @DrawableRes val iconResId: Int,
    val position: String,
    val godina: Int,
    val goals: Int,
    val assits: Int,
    val minutesPlayed: Int
)*/
data class Player(
    val firstName: String = "",
    val lastName: String = "",
    val position: String = "",
    val godina: Int = 0,
    val goals: Int = 0,
    val assists: Int = 0,
    val minutesPlayed: Int = 0, // URL for the player's image
)
