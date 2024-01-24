package com.cc221023.arcanemind.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.cc221023.arcanemind.R
import com.cc221023.arcanemind.RandomDaily
import com.cc221023.arcanemind.ui.theme.Black
import com.cc221023.arcanemind.ui.theme.DarkGray
import com.cc221023.arcanemind.ui.theme.EggShelly
import com.cc221023.arcanemind.ui.theme.MidGray
import com.cc221023.arcanemind.ui.theme.PitchBlack
import com.cc221023.arcanemind.ui.theme.Red
import com.cc221023.arcanemind.ui.theme.White
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    //to not show the bottom bar on the DrawDailyResultScreen => only show it on the other screens
    val bottomBar: (@Composable () -> Unit) =
        if (state.value.selectedScreen != Screens.DrawDailyResult) {
            { BottomNavigationBar(navController, state.value.selectedScreen) }
        } else {
            { }
        }
    Scaffold(
        bottomBar = bottomBar
    ) { it ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screens.Home.route
        ) {
            composable(Screens.Home.route) {
                mainViewModel.selectScreen(Screens.Home)
                HomeScreen(navController)
            }
            composable(Screens.Info.route) {
                mainViewModel.selectScreen(Screens.Info)
                InfoScreen(navController)
            }
            composable(Screens.Account.route) {
                mainViewModel.selectScreen(Screens.Account)
                mainViewModel.getAllDailyCards()
                HistoryScreen(mainViewModel)
            }

            composable(Screens.DrawDaily.route) {
                mainViewModel.selectScreen(Screens.DrawDaily)
                DrawDailyScreen(mainViewModel, navController)
            }
            composable(Screens.EditCard.route) {
                mainViewModel.selectScreen(Screens.EditCard)
                EditCardModal(mainViewModel)
            }

            composable(Screens.DrawDailyResult.route) {
                mainViewModel.selectScreen(Screens.DrawDailyResult)
                DisplayDailyResultScreen(mainViewModel, navController)
            }

            composable(Screens.MajorArcana.route) {
                mainViewModel.selectScreen(Screens.MajorArcana)
                MajorArcanaScreen(mainViewModel, navController)
            }

            composable(Screens.MinorArcana.route) {
                mainViewModel.selectScreen(Screens.MinorArcana)
                MinorArcanaScreen(mainViewModel, navController)
            }
            composable(Screens.UnderstandingTarot.route) {
                mainViewModel.selectScreen(Screens.UnderstandingTarot)
                UnderstandingTarotScreen()
            }
            composable(Screens.Reading.route) {
                mainViewModel.selectScreen(Screens.Reading)
                ReadingScreen()
            }
            composable(Screens.CardDetail.route) {
                val nameShort = it.arguments?.getString("nameShort")
                CardDetailScreen(mainViewModel, navController, nameShort!!)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screens) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black)
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            BottomNavigation(
                backgroundColor = Black,
                contentColor = Black,
                elevation = 0.dp,


                ) {
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Black,
                        unselectedIconColor = EggShelly,
                        indicatorColor = EggShelly,
                    ),
                    selected = (selectedScreen == Screens.Info),
                    onClick = { navController.navigate(Screens.Info.route) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.iconinfo),
                            contentDescription = "information about tarot cards",
                            modifier = Modifier
                                .size(50.dp, 34.dp)
                                .scale(1.4f)

                        )
                    },

                    )

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Black,
                        unselectedIconColor = EggShelly,
                        indicatorColor = EggShelly,
                    ),
                    selected = (selectedScreen == Screens.Home),
                    onClick = { navController.navigate(Screens.Home.route) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.homeicon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp, 34.dp)
                                .scale(1.2f)
                        )
                    },

                    )

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Black,
                        unselectedIconColor = EggShelly,
                        indicatorColor = EggShelly,

                        ),
                    selected = (selectedScreen == Screens.Account),
                    onClick = { navController.navigate(Screens.Account.route) },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.cardsicon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp, 34.dp)
                                .scale(1f)
                        )

                    },


                    )
            }

            Row(
                modifier = Modifier
                    .background(color = Black)
                    .fillMaxWidth()
                    .padding(bottom = 17.dp, start = 0.dp, end = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,

                ) {
                Text(
                    "Info",
                    fontSize = 14.sp,
                    color = EggShelly,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier
                        .width(50.dp)
                )
                Text(
                    "Home",
                    fontSize = 14.sp,
                    color = EggShelly,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier
                        .width(120.dp)
                )
                Text(
                    "History",
                    fontSize = 14.sp,
                    color = EggShelly,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier
                        .width(55.dp)
                )
            }
        }
    }
}


@Composable
fun HomeScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp, top = 30.dp, bottom = 5.dp)

                ) {
                    Text(
                        buildAnnotatedString { append("Hello\nstranger!\n") },
                        fontSize = 50.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                        lineHeight = 55.sp,
                        modifier = Modifier
                            .height(120.dp)
                    )
                    Text(
                        buildAnnotatedString { append("Have an arcane day!") },
                        fontSize = 20.sp,
                        color = MidGray,
                        fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),

                        )
                }
                
                Button(
                    onClick = {
                        navController.navigate(Screens.DrawDaily.route)
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .size(500.dp, 420.dp)
                        .absoluteOffset(0.dp, (-10).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Black
                    ),
                    contentPadding = PaddingValues(
                        bottom = 0.dp,
                        top = 30.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(290.dp, 340.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Black, PitchBlack)
                                ), RoundedCornerShape(20.dp)
                            )
                            .zIndex(1f)
                    ) {
                        Box( //for the border to look good
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(290.dp, 340.dp)
                                .border(1.dp, EggShelly, RoundedCornerShape(20.dp))
                                .zIndex(1f)
                        ) {}

                        Image(
                            painter = painterResource(id = R.drawable.tarotcardsrandom2),
                            contentDescription = "tarot cards",
                            modifier = Modifier
                                .scale(1.3f)
                                .absoluteOffset(x = 15.dp, y = (-65).dp)
                                .zIndex(2f)

                        )
                        Text(
                            "Daily card",
                            fontSize = 22.sp,
                            letterSpacing = 0.15.em,
                            fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                            color = EggShelly,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 250.dp, start = 20.dp)

                        )
                        Text(
                            "Draw a daily card to read your fortune!",
                            fontSize = 14.sp,
                            letterSpacing = 0.15.em,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.artifika_regular,
                                    FontWeight.Light
                                )
                            ),
                            color = White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 290.dp, start = 25.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .absoluteOffset(0.dp, (-20).dp)
                        .padding(top = 0.dp, start = 30.dp, end = 30.dp, bottom = 0.dp)
                        .clip(RoundedCornerShape(20.dp))

                        .fillMaxWidth()
                        .background(color = PitchBlack)
                        .height(140.dp)
                        .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                ) {

                    Text(
                        "Tarot helps us look within ourselves to understand our emotions, the reasoning behind our words and conduct, and the source of our conflicts. \n" +
                                "~ Benebell Wen",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.artifika_regular, FontWeight.Light)),
                        color = White,
                        fontSize = 13.sp,
                        lineHeight = 25.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()


                    )
                }
            }
        }
    }
}


@Composable
fun DrawDailyScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    val lazyColumnState = rememberLazyListState()
    rememberScrollState()
    //Log.d("DrawDaily", "Switches Screens to DrawDailyScreen")
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black),
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .zIndex(2f)
            .fillMaxSize()
            .padding(start = 5.dp, end = 25.dp, bottom = 25.dp)
            .absoluteOffset(x = 0.dp, y = 10.dp),
    ) {
        Button(
            onClick = { navController.navigate(Screens.Home.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = EggShelly,
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                contentDescription = null,
                tint = EggShelly,
                modifier = Modifier
                    .size(50.dp, 50.dp)
            )
        }
        Text(
            "Daily Card", textAlign = TextAlign.Center, color = White,
            fontFamily = FontFamily(Font(R.font.almendra_regular)),
            fontSize = 24.sp,
            letterSpacing = 0.15.em,
            modifier = Modifier
                .padding(start = 30.dp, top = 15.dp)
        )
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally, state = lazyColumnState,
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .padding(top = 20.dp, start = 25.dp, end = 25.dp, bottom = 25.dp)
    ) {
        item {
            Text(
                text = "Draw your daily card!",
                color = White,
                fontSize = 44.sp,
                fontFamily = FontFamily(Font(R.font.almendra_bold)),
                modifier = Modifier.padding(
                    top = 60.dp,
                    start = 25.dp,
                    end = 25.dp,
                    bottom = 25.dp
                ),
                textAlign = TextAlign.Center,
                lineHeight = 55.sp
            )
            Image(
                painter = painterResource(id = R.drawable.handtarot),
                contentDescription = "Tarot Card",
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.3f)
                    .padding(top = 10.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 25.dp, end = 25.dp)
                // .size(60.dp, 500.dp),
            ) {
                Text(
                    text = "Get some insight about the day ahead:",
                    color = White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.almendra_regular, FontWeight.Light)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 0.dp, start = 25.dp, end = 25.dp, bottom = 25.dp)
                        .fillMaxWidth()
                )

                Image(
                    painter = painterResource(id = R.drawable.line),
                    contentDescription = "line",
                    modifier = Modifier
                        .scale(2.3f)

                )

                Box() {
                    Button(
                        onClick = {
                            mainViewModel.fetchRandomTarotCard()
                            navController.navigate(Screens.DrawDailyResult.route)
                        },
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                            .height(65.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EggShelly,
                            contentColor = Black,
                            disabledContentColor = Black
                        ),
                    )
                    {
                        Text(
                            buildAnnotatedString {
                                append("Draw a card")
                            },
                            color = Black,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDailyResultScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    var comment by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    val scrollState = rememberScrollState()
    val randomCardState by mainViewModel.tarotCardState.collectAsState()
    val lazyColumnState = rememberLazyListState()

    Log.d(
        "DisplayDaily",
        "Switches Screens to DisplayDailyResultScreen, randomCardState: $randomCardState"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF161616))
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .zIndex(2f)
            .fillMaxSize()
            .padding(start = 5.dp, end = 25.dp, bottom = 25.dp)
            .absoluteOffset(x = 0.dp, y = 10.dp),
    ) {
        Text(
            "Your daily card", textAlign = TextAlign.Center, color = EggShelly,
            fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
            fontSize = 32.sp,
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp)
        )
    }

    // Scrollable content using LazyColumn
    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 85.dp)
    ) {
        // Display the randomly drawn card
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                randomCardState.let { randomCard ->
                    Log.d(
                        "DisplayDaily",
                        "Switches Screens to DisplayDailyResultScreen, randomCard: ${randomCard}"
                    )
                    Text(
                        text = " ${randomCard.name}",
                        color = White,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.artifika_regular,
                                FontWeight.Light
                            )
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 0.dp, start = 25.dp, end = 25.dp)
                    )
                    Box(

                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .size(500.dp, 275.dp)
                            .padding(top = 20.dp, bottom = 0.dp, start = 130.dp, end = 130.dp)
                            .background(color = Color.White, RoundedCornerShape(20.dp))
                    ) {
                        AsyncImage(
                            model = "https://sacred-texts.com/tarot/pkt/img/${randomCardState.nameShort}.jpg",
                            contentDescription = randomCardState.desc,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(10.dp))
                                .padding(10.dp)
                                .zIndex(1f)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 20.dp, start = 25.dp, end = 25.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Black, PitchBlack)
                                    ), RoundedCornerShape(20.dp)
                                )
                                .border(1.dp, DarkGray, RoundedCornerShape(20.dp)),
                        ) {
                            Text(
                                text = randomCard.meaningUp,
                                color = White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .height(250.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Black, PitchBlack)
                        ), RoundedCornerShape(20.dp)
                    )
                    .border(1.dp, DarkGray, RoundedCornerShape(20.dp))

                    .verticalScroll(state = scrollState),

                ) {
                Text(
                    text = "Your thoughts:",
                    color = White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        top = 15.dp,
                        start = 20.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = comment,
                    onValueChange = { newText -> comment = newText },
                    label = { Text(text = "Add your interpretation (you can also do this later)...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(15.dp)
                        .absoluteOffset(0.dp, 34.dp)
                        .clip(shape = RoundedCornerShape(25.dp)),
                    colors = TextFieldDefaults.textFieldColors(containerColor = White)
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 25.dp, start = 5.dp, end = 5.dp, bottom = 20.dp)


            ) {
                Button(
                    onClick = {
                        val currentDate = LocalDate.now() // Get the current date
                        val formattedDate =
                            currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) // Save the date in the wished format

                        mainViewModel.saveRandomCard(
                            RandomDaily(
                                name = randomCardState.name,
                                id = 0,
                                meaningUp = randomCardState.meaningUp,
                                desc = randomCardState.desc,
                                comment = comment.text,
                                name_short = randomCardState.nameShort,

                                imgUrl = "https://sacred-texts.com/tarot/pkt/img/${randomCardState.nameShort}.jpg",
                                date = formattedDate,
                            )
                        ) // Save the card to the database
                        navController.navigate(Screens.Home.route)
                    },
                    shape = RoundedCornerShape(35.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .height(65.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EggShelly,
                        contentColor = Black,
                        disabledContentColor = Black
                    ),
                )
                {
                    Text(
                        buildAnnotatedString {
                            append("Save and exit")
                        },
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}


@Composable
fun InfoScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 30.dp, end = 20.dp, bottom = 0.dp)

        ) {
            item {
                Text(
                    buildAnnotatedString {
                        append(
                            "Your tarot\n" +
                                    "archive\n"
                        )
                    },
                    fontSize = 50.sp,
                    lineHeight = 55.sp,
                    color = White,
                    fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                    modifier = Modifier
                )

                Text(
                    buildAnnotatedString {
                        append("Stay educated!")
                    },
                    fontSize = 18.sp,
                    color = MidGray,
                    fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                    modifier = Modifier
                        .absoluteOffset(x = 0.dp, y = (-65).dp)
                )

                Box(
                    modifier = Modifier
                        .absoluteOffset(y = (-55).dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = PitchBlack)
                        .fillMaxWidth()
                        .height(170.dp)
                        .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Here you can find information about tarot and the art of reading your card draws. For a more in depth interpretation the general meaning of the deck consisting of 87 cards  is split into the minor and major arcana.")
                        },
                        color = White,
                        textAlign = TextAlign.Justify,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                        modifier = Modifier.padding(15.dp)
                    )
                }
                Button(
                    onClick = { navController.navigate(Screens.UnderstandingTarot.route) },
                    shape = RoundedCornerShape(35.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .absoluteOffset(y = (-45).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EggShelly,
                        contentColor = Black,
                        disabledContentColor = Black
                    ),
                )
                {
                    Text(
                        buildAnnotatedString {
                            append("Understanding Tarot")
                        },
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = { navController.navigate(Screens.Reading.route) },
                    shape = RoundedCornerShape(35.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .absoluteOffset(y = (-35).dp)
                        .clip(shape = RectangleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EggShelly,
                        contentColor = Black,
                        disabledContentColor = Black
                    ),
                )
                {
                    Text(
                        buildAnnotatedString {
                            append("Reading the cards")
                        },
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                        modifier = Modifier
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .absoluteOffset(y = (-20).dp)
                        .height(250.dp)
                        .fillMaxWidth()
                        .clip(shape = RectangleShape),
                ) {
                    Button(
                        onClick = { navController.navigate(Screens.MinorArcana.route) },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(300.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Black
                        )
                    )
                    {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .width(145.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Black, PitchBlack)
                                    ), RoundedCornerShape(20.dp)
                                )
                                .zIndex(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp)
                                    .width(145.dp)
                                    .border(1.dp, EggShelly, RoundedCornerShape(20.dp))
                                    .zIndex(1f)
                            ) {
                            }
                            Image(
                                //painter = painterResource(id = R.drawable.tarotcards),
                                painter = painterResource(id = R.drawable.hand_left),
                                contentDescription = "tarot cards",
                                modifier = Modifier
                                    .scale(1.5f)
                                    .absoluteOffset(x = (-20).dp, y = (-22).dp)
                                    .zIndex(2f)
                            )
                            //Spacer(modifier = Modifier.height(90.dp))
                            Text(
                                "The minor arcana",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                                color = EggShelly,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .absoluteOffset(x = 15.dp, y = 120.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                    Button(
                        onClick = { navController.navigate(Screens.MajorArcana.route) },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(300.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Black
                        )
                    )
                    {
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .height(200.dp)
                                .width(145.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Black, PitchBlack)
                                    ), RoundedCornerShape(20.dp)
                                )
                                .zIndex(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(0.dp)
                                    .height(200.dp)
                                    .width(145.dp)
                                    .border(1.dp, EggShelly, RoundedCornerShape(20.dp))
                                    .zIndex(1f)
                            ) {}
                            Image(
                                // painter = painterResource(id = R.drawable.fourcards),
                                painter = painterResource(id = R.drawable.hand_right),

                                contentDescription = "tarot cards",
                                modifier = Modifier
                                    .scale(1.5f)
                                    .absoluteOffset(x = 35.dp, y = (-20).dp)
                                    .zIndex(2f)
                            )
                            //Spacer(modifier = Modifier.height(90.dp))
                            Text(
                                "The major arcana",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                                color = EggShelly,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .absoluteOffset(x = 15.dp, y = 120.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(mainViewModel: MainViewModel) {
    val lazyColumnState = rememberLazyListState()
    var showDialog by remember { mutableStateOf(false) }
    var itemsWithDialog by remember { mutableStateOf(mapOf<RandomDaily, Boolean>()) }

    LocalContext.current


    //to show only a short part of the text

    val maxLength = 20
    val truncateText: (String) -> String = { text ->
        if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }
    val maxLengthName = 12
    val truncateName: (String) -> String = { text ->
        if (text.length > maxLengthName) {
            text.substring(0, maxLengthName) + "..."
        } else {
            text
        }
    }
    val daily_cards: List<RandomDaily> = emptyList()


    daily_cards.size
    var itemToDelete by remember { mutableStateOf<RandomDaily?>(null) }
    val state = mainViewModel.mainViewState.collectAsState()
    // Create a DateFormatter object for displaying date in specified format.
    // Now you can format the date using SimpleDateFormat
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF161616))
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp, end = 0.dp, top = 30.dp, bottom = 25.dp)
        ) {
            Text(
                buildAnnotatedString { append("Your History!\n") },
                color = White,
                fontSize = 50.sp,
                fontFamily = FontFamily(Font(R.font.almendra_bold)),
                modifier = Modifier.padding(end = 75.dp, start = 25.dp),

                lineHeight = 55.sp,
            )
            Text(
                buildAnnotatedString { append("Get all your last draws!") },
                fontSize = 20.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier.padding(end = 25.dp, start = 25.dp)
                    .absoluteOffset(x = 0.dp, y = (-58).dp),
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(end = 25.dp, start = 25.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .absoluteOffset(y = (-35).dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = PitchBlack)
                        .fillMaxWidth()
                        .height(100.dp)
                        .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                ) {
                    Text(
                        buildAnnotatedString {
                            append("Here you can find all your saved cards. You can edit or delete them by clicking on the card.")
                        },
                        color = White,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                        modifier = Modifier.padding(15.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)

                )}
                Spacer(modifier = Modifier.height(10.dp))

                if (state.value.daily_cards.isEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(end = 25.dp, start = 25.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Image(
                                painter = painterResource(id = R.drawable.witchy_stuff),
                                contentDescription = "divider",
                                modifier = Modifier

                                    .scale(1.2f)

                            )
                            Text(
                                text = "Seems like you haven't drawn a card yet...",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                style = TextStyle(
                                    color = White,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.artifika_regular,
                                            FontWeight.Light
                                        )
                                    ),
                                ),
                                modifier = Modifier
                                    .absoluteOffset(x = 0.dp, y = (-30).dp)
                                    .padding(0.dp),
                                textAlign = TextAlign.Center
                            )

                        }
                    }
                } else {
                    LazyColumn(
                        state = lazyColumnState,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        //  Log.d("allcardsRandom", "${state.value.daily_cards}")
                        items(
                            state.value.daily_cards.reversed()
                        ) { RandomDaily ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.padding(end = 25.dp, start = 25.dp, top = 15.dp)
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .size(700.dp, 80.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Black, PitchBlack)
                                        )
                                    )
                                    .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                                    .clickable { mainViewModel.editRandomDailyCard(RandomDaily) }
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Bottom,
                                    modifier = Modifier

                                        .padding(top = 15.dp)

                                ) {
                                    Row(
                                        verticalAlignment = Alignment.Bottom,
                                        modifier = Modifier
                                            // .size(400.dp, 20.dp)
                                            .padding(start = 15.dp)

                                    ) {
                                        Text(
                                            text = truncateName(RandomDaily.name),
                                            fontSize = 22.sp,
                                            color = White,
                                            fontFamily = FontFamily(
                                                Font(
                                                    R.font.almendra_regular,
                                                    FontWeight.Light
                                                )
                                            ),
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier.width(160.dp)

                                        )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(
                                            text = RandomDaily.date,
                                            fontSize = 14.sp,
                                            color = White,
                                            fontFamily = FontFamily(
                                                Font(
                                                    R.font.asap_regular,
                                                    FontWeight.Light
                                                )
                                            ),
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .padding(start = 0.dp, top = 5.dp, bottom = 5.dp)


                                        )


                                    }
                                    Text(
                                        text = truncateText(RandomDaily.comment),

                                        fontSize = 16.sp,
                                        color = White,
                                        textAlign = TextAlign.Start,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.asap_regular,
                                                FontWeight.Light
                                            )
                                        ),
                                        modifier = Modifier
                                            .padding(start = 15.dp, top = 5.dp)

                                    )
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                IconButton(
                                    onClick = {
                                        itemsWithDialog = itemsWithDialog.toMutableMap().apply {
                                            this[RandomDaily] = true}
                                    }
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.deletebin),
                                        contentDescription = "delete button",
                                        modifier = Modifier
                                            .size(70.dp, 70.dp)
                                            .zIndex(1f)
                                            .padding(top = 15.dp, bottom = 15.dp),
                                        tint = White,
                                    )
                                }
                                if (itemsWithDialog[RandomDaily] == true) {
                                    AlertDialog(
                                        modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                                        backgroundColor = Black,
                                        contentColor = White,
                                        onDismissRequest = {
                                            itemsWithDialog = itemsWithDialog.toMutableMap().apply {
                                                this[RandomDaily] = false
                                            }
                                            // Handle dismiss if needed
                                            showDialog = false
                                        },
                                        title = {
                                            Text(
                                                text = "Confirm Deletion",
                                                color = EggShelly,
                                                fontFamily = FontFamily(
                                                    Font(R.font.almendra_bold, FontWeight.Light)
                                                ),
                                                fontSize = 24.sp,
                                            )
                                        },
                                        text = {
                                            Text(
                                                text = "Are you sure you want to delete this daily draw?",
                                                color = White
                                            )
                                        },
                                        confirmButton = {
                                            Button(
                                                onClick = {
                                                    itemsWithDialog[RandomDaily]?.let { isDialogVisible ->
                                                        if (isDialogVisible) {
                                                            mainViewModel.deleteButton(RandomDaily)
                                                        }
                                                    }
                                                    showDialog = false
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Red,
                                                    contentColor = White
                                                ),
                                                modifier = Modifier.padding(
                                                    bottom = 20.dp,
                                                    end = 20.dp
                                                )
                                            ) {
                                                Text("Delete")
                                            }
                                        },
                                        dismissButton = {
                                            Button(
                                                onClick = {
                                                    showDialog = false
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = EggShelly,
                                                    contentColor = Black
                                                ),
                                                modifier = Modifier.padding(
                                                    bottom = 20.dp,
                                                    end = 10.dp
                                                )
                                            ) {
                                                Text("Cancel")
                                            }
                                        }
                                    )
                                }
                                Log.d("EditCard", "Clicked on Text")
                                EditCardModal(mainViewModel)
                            }
                        }
                    }
                }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardModal(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val randomCardState by mainViewModel.randomDailyState.collectAsState()
    // Log.d("EditCard","In EditCardModal, openDialog: ${state.value.openDialog}")

    if (state.value.openDialog) {
        var comment by rememberSaveable { mutableStateOf(randomCardState.comment) }

        AlertDialog(
            onDismissRequest = {
                mainViewModel.closeDialog()
            },
            contentColor = White,
            backgroundColor = Black,
            text = {
                Column {
                    Text(
                        text = "Edit your comment",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        style = TextStyle(
                            color = EggShelly,
                            fontFamily = FontFamily(
                                Font(R.font.almendra_bold, FontWeight.Light)
                            ),
                        )
                    )

                    TextField(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        value = comment,
                        onValueChange = { newText -> comment = newText },
                        label = { Text(text = "comment") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Black,
                            focusedLabelColor = DarkGray,
                            unfocusedLabelColor = DarkGray,
                            containerColor = White,
                            unfocusedIndicatorColor = Black,
                            focusedIndicatorColor = Black
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        //  Log.d("EditCard","On confirm, ${randomCardState}")
                        mainViewModel.updateRandomDailyCard(
                            RandomDaily(
                                randomCardState.name,
                                randomCardState.id,
                                randomCardState.meaningUp,
                                randomCardState.desc,
                                comment,
                                randomCardState.name_short,
                                randomCardState.imgUrl,
                                randomCardState.date
                            )
                        )
                        Log.d("EditCard", "Updated comment")
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp, end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EggShelly,
                        contentColor = Black
                    ),
                ) {
                    Text(text = "Confirm", color = Black)
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajorArcanaScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    var searchText by remember { mutableStateOf("") }
    val majorArcanaCards by mainViewModel.majorArcanaCards.collectAsState()

    //Log.d("MajorArcana", "majorArcanaCards: $majorArcanaCards")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 25.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ) {
            Text(
                buildAnnotatedString { append("The major arcana!\n") },
                color = White,
                fontSize = 44.sp,
                fontFamily = FontFamily(Font(R.font.almendra_bold)),
                modifier = Modifier.padding(end = 55.dp),

                lineHeight = 45.sp
            )
            Text(
                buildAnnotatedString { append("The most powerful cards") },
                fontSize = 20.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = (-55).dp),
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(60.dp)
                    .absoluteOffset(x = 0.dp, y = (-45).dp),

                label = { Text("Search by name", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    containerColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White, // Change the tint color of the search icon
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                    )
                },
                singleLine = true,

                )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Set the number of items in each row (change as needed)
                modifier = Modifier
                    .fillMaxSize()
                //.padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                //.absoluteOffset(x = 0.dp, y = 20.dp),
            ) {

                val filteredCards = majorArcanaCards.filter {
                    it.name.contains(searchText, ignoreCase = true)
                }


                items((filteredCards)) { card ->
                    //   Log.d("MajorArcana", "card: ${card.nameShort}")
                    Button(
                        onClick = {
                            navController.navigate(Screens.CardDetail.createRoute(card.nameShort))
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .size(700.dp, 300.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,

                            )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)

                        ) {
                            // Display image
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .clip(shape = RoundedCornerShape(15.dp))
                                    .background(color = Color.White)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = "https://sacred-texts.com/tarot/pkt/img/${card.nameShort}.jpg"
                                    ),
                                    contentDescription = "Tarot Card",
                                    modifier = Modifier

                                        .padding(start = 10.dp, end = 10.dp)
                                        .size(200.dp, 225.dp)
                                )
                            }
                            // Display card name
                            Text(
                                text = card.name,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.artifika_regular,
                                        FontWeight.Light
                                    )
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth()
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinorArcanaScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    var searchText by remember { mutableStateOf("") }
    val minorArcanaCards by mainViewModel.minorArcanaCards.collectAsState()

    // Log.d("MinorArcana", "minorArcanaCards: $minorArcanaCards")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 0.dp, bottom = 25.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ) {
            Text(
                buildAnnotatedString { append("The minor arcana!\n") },
                color = White,
                fontSize = 44.sp,
                fontFamily = FontFamily(Font(R.font.almendra_bold)),
                modifier = Modifier.padding(end = 55.dp),

                lineHeight = 45.sp
            )
            Text(
                buildAnnotatedString { append("The four suits: Wands, Swords, Cups and Pentacles!") },
                fontSize = 20.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = (-55).dp),
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = { newText -> searchText = newText },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(60.dp)
                    .absoluteOffset(x = 0.dp, y = (-45).dp),

                label = { Text("Search by name", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    containerColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White, // Change the tint color of the search icon
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                    )
                },
                singleLine = true,

                )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Set the number of items in each row
                modifier = Modifier
                    .fillMaxSize()
                //.padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp)
                //.absoluteOffset(x = 0.dp, y = 20.dp),
            ) {

                val filteredCards = minorArcanaCards.filter {
                    it.name.contains(searchText, ignoreCase = true)
                }


                items((filteredCards)) { card ->
                    Button(
                        onClick = { navController.navigate(Screens.CardDetail.createRoute(card.nameShort)) },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .size(700.dp, 300.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,

                            )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            // Display image
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .clip(shape = RoundedCornerShape(15.dp))
                                    .background(color = Color.White)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = "https://sacred-texts.com/tarot/pkt/img/${card.nameShort}.jpg"
                                    ),
                                    contentDescription = "Tarot Card",
                                    modifier = Modifier

                                        .padding(start = 10.dp, end = 10.dp)
                                        .size(200.dp, 225.dp)
                                )
                            }
                            // Display card name
                            Text(
                                text = card.name,
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.artifika_regular,
                                        FontWeight.Light
                                    )
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth()
                                    .height(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UnderstandingTarotScreen() {
    rememberScrollState()
    val lazyColumnState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.5f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
                .blur(10.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()

                .padding(start = 25.dp, top = 0.dp, end = 20.dp, bottom = 20.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ) {
            Text(
                buildAnnotatedString { append("About Tarot\n") },
                color = White,
                fontSize = 44.sp,
                fontFamily = FontFamily(Font(R.font.almendra_bold)),
                modifier = Modifier.padding(end = 55.dp),

                lineHeight = 45.sp
            )
            Text(
                buildAnnotatedString { append("The history of the divination practice!") },
                fontSize = 20.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = (-55).dp),
            )
        }
        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item() {
                Text(
                    "The story about the cards\n", color = EggShelly,
                    fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                    fontSize = 20.sp,
                    letterSpacing = 0.15.em,
                    textAlign = TextAlign.Center,


                    )

                Text(
                    buildAnnotatedString {
                        append("The tarot is a pack of playing cards, used from the mid-15th century in various parts of Europe to play games such as Italian tarocchini, French tarot and Austrian Königrufen, many of which are still played today. \n\n In the late 18th century, some tarot decks began to be used for divination via tarot card reading and cartomancy leading to custom decks developed for such occult purposes.\n")
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier.padding(10.dp)

                )
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
                Text(
                    buildAnnotatedString {
                        append("Like common playing cards, the tarot has four suits (which vary by region: French suits in Northern Europe, Latin suits in Southern Europe, and German suits in Central Europe). \n \nEach suit has 14 cards, ten cards numbering from one (or Ace) to ten and four face cards (King, Queen, Knight, and Jack/Knave). In addition, the tarot has a separate 21-card trump suit and a single card known as the Fool. Depending on the game, the Fool may act as the top trump or may be played to avoid following suit.\n")
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier.padding(10.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
                Text(
                    buildAnnotatedString {
                        append("François Rabelais gives tarau as the name of one of the games played by Gargantua in his Gargantua and Pantagruel; this is likely the earliest attestation of the French form of the name. \n \n Tarot cards are used throughout much of Europe to play card games. In English-speaking countries, where these games are largely unplayed, tarot cards are now used primarily for divinatory purposes. \nOccultists call the trump cards and the Fool the major arcana while the ten pip and four court cards in each suit are called minor arcana. \nThe cards are traced by some occult writers to ancient Egypt or the Kabbalah but there is no documented evidence of such origins or of the usage of tarot for divination before the 18th century.\n")
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier.padding(10.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
            }
        }
    }

}


@Composable
fun ReadingScreen() {
    rememberScrollState()
    val lazyColumnState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.5f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
                .blur(10.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, top = 0.dp, end = 20.dp, bottom = 20.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ) {
            Text(
                buildAnnotatedString { append("Reading the cards\n") },
                color = White,
                fontSize = 44.sp,
                fontFamily = FontFamily(Font(R.font.almendra_bold)),
                modifier = Modifier.padding(end = 55.dp),

                lineHeight = 45.sp
            )
            Text(
                buildAnnotatedString { append("Get information on how to hold a reading!") },
                fontSize = 20.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = (-55).dp),
            )
        }
        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {

                Text(
                    "Understanding the Cards\n", color = EggShelly,
                    fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                    fontSize = 20.sp,
                    letterSpacing = 0.15.em,
                    textAlign = TextAlign.Center,


                    )

                Text(
                    buildAnnotatedString {
                        append(
                            "Major Arcana:\n" +
                                    "These cards represent significant life events and spiritual lessons. Each card has a specific meaning and can indicate major life changes.\n" +
                                    "\n" +
                                    "Minor Arcana:\n" +
                                    "These cards are divided into four suits: Cups (emotions), Wands (creativity and inspiration), Swords (intellect and conflict), and Pentacles (material aspects). Each suit has cards numbered from Ace to 10, along with four court cards (Page, Knight, Queen, King).\n"
                        )
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier
                        .padding(15.dp)
                        .absoluteOffset(0.dp, (-15).dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
                Text(
                    "Conducting a Reading\n", color = EggShelly,
                    fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                    fontSize = 20.sp,
                    letterSpacing = 0.15.em,
                    textAlign = TextAlign.Center,
                )
                Text(
                    buildAnnotatedString {
                        append(
                            "Cleansing and Focusing\n" +

                                    "Before a reading, take a moment to clear your mind.\n" + "\n" +
                                    "Asking a Question:\n" +
                                    "Formulate a clear and specific question in your mind. Tarot works best with focused and open-ended questions.\n"
                        )
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier.padding(15.dp)
                )



                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
                Text(
                    "Interpreting the Cards\n", color = EggShelly,
                    fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.15.em,

                    )
                Text(
                    buildAnnotatedString {
                        append(

                            "Consider the symbolism, imagery, and traditional meanings of the cards in relation to your question. \n" +
                                    "\n" + "Intuition and Personal Connection:\n" +

                                    "Trust your intuition. Your feelings and impressions while looking at the cards are important. Your personal connection with the deck will enhance the accuracy of your readings.\n"
                        )
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier.padding(15.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
                Text(
                    "Additional Tips\n", color = EggShelly,
                    fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.15.em,

                    )
                Text(
                    buildAnnotatedString {
                        append(
                            "Practice Regularly:\n" +

                                    "Tarot is a skill that improves with practice. Regularly work with your deck to strengthen your connection and interpretation skills.\n" +
                                    "\n" + "Keep a Journal:\n" +

                                    "Record your readings and observations in a journal. This helps track your progress and understand patterns over time.\n" +
                                    "\n" + "Respect the Cards:\n" +

                                    "Treat your tarot deck with respect. Some people like to store their cards in a special cloth or box to maintain their energy.\n"
                        )
                    },
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier.padding(15.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        .padding(top = 5.dp, bottom = 10.dp)

                )
            }
        }

    }


}


@Composable
fun CardDetailScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    nameShort: String

) {
    mainViewModel.fetchCardDetails(nameShort)

    rememberScrollState()
    val lazyColumnState = rememberLazyListState()
    val tarotCardState by mainViewModel.tarotCardState.collectAsState()
    Log.d(
        "DisplayDaily",
        "Switches Screens to DisplayDailyResultScreen, randomCardState: ${tarotCardState}"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alchemy),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxSize()
                .scale(2.0f)
                .alpha(0.35f)
                .padding(16.dp)
                .absoluteOffset(x = 20.dp, y = (-20).dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .zIndex(2f)
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 25.dp, bottom = 25.dp)
                    .absoluteOffset(x = 0.dp, y = 10.dp),
            ) {
                Button(
                    onClick = { navController.navigate(Screens.MajorArcana.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = EggShelly,
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        tint = EggShelly,
                        modifier = Modifier
                            .size(50.dp, 50.dp)
                    )
                }
                Text(
                    "Card Details", textAlign = TextAlign.Center, color = EggShelly,
                    fontFamily = FontFamily(Font(R.font.almendra_regular, FontWeight.Light)),
                    fontSize = 24.sp,
                    letterSpacing = 0.15.em,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 15.dp)
                )
            }

            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 75.dp, start = 25.dp, end = 25.dp)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        tarotCardState.let { tarotCard ->
                            Text(
                                text = " ${tarotCard.name}",
                                color = White,
                                fontSize = 24.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.artifika_regular,
                                        FontWeight.Light
                                    )
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(10.dp)
                            )
                            Box(

                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .size(500.dp, 300.dp)
                                    .padding(top = 20.dp, bottom = 0.dp, start = 95.dp, end = 95.dp)
                                    .background(color = Color.White, RoundedCornerShape(20.dp))

                            ) {
                                AsyncImage(
                                    model = "https://sacred-texts.com/tarot/pkt/img/${tarotCardState.nameShort}.jpg",
                                    contentDescription = tarotCardState.desc,
                                    modifier = Modifier
                                        .fillMaxWidth()

                                        .clip(shape = RoundedCornerShape(10.dp))
                                        .padding(
                                            top = 10.dp,
                                            bottom = 10.dp,
                                            start = 10.dp,
                                            end = 10.dp
                                        )
                                        .zIndex(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Box(
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(Black, PitchBlack)
                                            ), RoundedCornerShape(20.dp)
                                        )
                                        .border(1.dp, DarkGray, RoundedCornerShape(20.dp)),
                                ) {
                                    Text(
                                        text = " ${tarotCard.meaningUp}",
                                        color = White,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(20.dp))

                                Box(
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.verticalGradient(
                                                colors = listOf(Black, PitchBlack)
                                            ), RoundedCornerShape(20.dp)
                                        )
                                        .border(1.dp, DarkGray, RoundedCornerShape(20.dp)),
                                ) {
                                    Text(
                                        text = " ${tarotCard.desc}",
                                        color = White,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(20.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))


                    }
                }
                // Add more components to display card details
            }
        }
    }
}