package hr.ferit.rokocvitkovic.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.ferit.rokocvitkovic.firstproject.ui.theme.Player
import hr.ferit.rokocvitkovic.firstproject.ui.theme.PlayerViewModel
import hr.ferit.rokocvitkovic.firstproject.ui.theme.PlayersScreen
import hr.ferit.rokocvitkovic.firstproject.ui.theme.TableScreen
import hr.ferit.rokocvitkovic.firstproject.ui.theme.Team
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import hr.ferit.rokocvitkovic.firstproject.ui.theme.ScheduleScreen
import hr.ferit.rokocvitkovic.firstproject.ui.theme.ScheduleViewModel
import hr.ferit.rokocvitkovic.firstproject.ui.theme.TeamViewModel


import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        setContent {
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreen() {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000) // Simulacija učitavanja
        isLoading = false
    }

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo NK Graničar Klakar",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NK Graničar Klakar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(color = Color.Blue)
        }
    } else {
        AppNavigation() // Pokrećemo navigaciju nakon učitavanja
    }
}




@Composable
fun MainScreen(navController: NavHostController) {
    val playerViewModel: PlayerViewModel = viewModel()
    val teamViewModel: TeamViewModel = viewModel()

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color(0xFF2E7D32)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomNavigationButton(
                        label = "Početna",
                        isSelected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        iconResId = R.drawable.ic_home // Zamijenite s odgovarajućim ID-om ikone
                    )
                    BottomNavigationButton(
                        label = "Tablica",
                        isSelected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        iconResId = R.drawable.ic_table // Zamijenite s odgovarajućim ID-om ikone
                    )
                    BottomNavigationButton(
                        label = "Igrači",
                        isSelected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        iconResId = R.drawable.ic_players // Zamijenite s odgovarajućim ID-om ikone
                    )
                    BottomNavigationButton(
                        label = "Raspored",
                        isSelected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        iconResId = R.drawable.ic_raspored // Zamijenite s odgovarajućim ID-om ikone
                    )
                    BottomNavigationButton(
                        label = "Rezultati",
                        isSelected = selectedTab == 4,
                        onClick = { selectedTab = 4 },
                        iconResId = R.drawable.ic_result // Zamijenite s odgovarajućim ID-om ikone
                    )

                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    0 -> HomeScreen(navController) // Prosleđujemo NavController
                    1 -> TableScreen(viewModel = teamViewModel)
                    2 -> PlayersScreen(viewModel = playerViewModel)
                    3 -> ScheduleScreen()
                    4 -> ResultsScreen()
                }
            }
        }
    )
}



@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(16.dp)
    ) {
        IconButton(
            onClick = {
                navController.navigate("infoScreen")
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.info_icon),
                contentDescription = "Informacije o klubu",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo NK Graničar Klakar",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "NK Graničar Klakar",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C6B4F),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Osnovan 1950. godine, NK Graničar Klakar ponosno sudjeluje u lokalnim nogometnim natjecanjima. Klub trenutno igra u 2. županijskoj ligi Brodsko-Posavske županije.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.home1),
                contentDescription = "Home Image 1",
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1.2f) // Lagano povećan omjer za bolji izgled
                    .offset(x = 12.dp) // Suptilan pomak udesno
                    .clip(MaterialTheme.shapes.medium) // Zaobljeni rubovi
                    .background(Color(0xFFE8F5E9)) // Nježnija pozadinska boja
                    .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium) // Tanki obrub
                    .shadow(4.dp, shape = MaterialTheme.shapes.medium) // Suptilna sjena
                    .padding(4.dp) // Manje unutrašnje margine
            )
        }


        }
    }








@Composable
fun BottomNavigationButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconResId: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            tint = if (isSelected) Color.Yellow else Color.Unspecified,
            modifier = Modifier.size(24.dp) // Veličina ikone
        )
        Spacer(modifier = Modifier.height(4.dp)) // Razmak između ikone i teksta
        Text(
            text = label,
            fontSize = 12.sp, // Manji tekst ispod ikone
            color = if (isSelected) Color.Yellow else Color.White
        )
    }
}







@Composable
fun ResultsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Prikaz Rezultata", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun InfoScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Strelica za povratak u gornjem desnom kutu
        IconButton(
            onClick = { navController.popBackStack() }, // Povratak na prethodnu stranicu
            modifier = Modifier
                .align(Alignment.TopEnd) // Pozicioniranje u gornji desni kut
                .padding(8.dp) // Margina
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_back), // Zamijenite sa svojom ikonicom
                contentDescription = "Povratak",
                tint = Color.Unspecified, // Boja strelice
                modifier = Modifier.size(32.dp) // Veličina ikone
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp), // Dovoljno prostora ispod strelice
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Informacije s ikonama i tekstom
            Text(
                text = "Informacije o klubu",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_calendar),
                    contentDescription = "Datum osnivanja",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Datum osnivanja: 20.07.1971.",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_adress),
                    contentDescription = "Adresa",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Adresa: Klakar 14, 35208 Klakar",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_stadion),
                    contentDescription = "Stadion",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Stadion: GRANIČAR K",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Kreiramo NavController unutar funkcije
    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") { MainScreen(navController) }
        composable("infoScreen") { InfoScreen(navController) }
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppNavigation()
}