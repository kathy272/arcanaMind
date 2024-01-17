package com.cc221023.arcanemind.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221023.arcanemind.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cc221023.arcanemind.ui.theme.Black
import com.cc221023.arcanemind.ui.theme.DarkGray
import com.cc221023.arcanemind.ui.theme.EggShelly
import com.cc221023.arcanemind.ui.theme.MidGray
import com.cc221023.arcanemind.ui.theme.PitchBlack
import com.cc221023.arcanemind.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen)}
    ){
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screens.Home.route
        ) {
            composable(Screens.Home.route) {
                mainViewModel.selectScreen(Screens.Home)
                HomeScreen(mainViewModel, navController)
            }
            composable(Screens.Info.route) {
                mainViewModel.selectScreen(Screens.Info)
                InfoScreen(mainViewModel, navController)
            }
            composable(Screens.Account.route) {
                mainViewModel.selectScreen(Screens.Account)
                AccountScreen(mainViewModel, navController)
            }

            composable(Screens.DrawDaily.route) {
                mainViewModel.selectScreen(Screens.DrawDaily)
                DrawDailyScreen(mainViewModel, navController)
            }
            composable(Screens.EditCard.route) {
                mainViewModel.selectScreen(Screens.EditCard)
                EditCardModal(mainViewModel)
            }

        }
    }
}
@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screens){
    Box(
        modifier = Modifier
            .fillMaxWidth()
        /* for shadow:
            .drawWithContent {
               val linearGradient = Brush.verticalGradient(
                    startY = 0f,
                    endY = 30f,
                    colors = listOf(
                        Gray.copy(alpha = 0f),
                        Gray.copy(alpha = 0.2f),
                        Gray.copy(alpha = 0.5f),
                        Gray.copy(alpha = 0.9f)
                    )
               )
              drawRect(
                  brush = linearGradient,
                  topLeft = Offset(0f, 0f), // Adjust the offset as needed
                  size = Size(size.width, 10f)
                )
        }
        */
    ) {
        BottomNavigation(
            backgroundColor = Black,
            contentColor = Black,
            modifier = Modifier
                .shadow(50.dp),
        ) {

            NavigationBarItem(
               colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Black,
                    unselectedIconColor = EggShelly,
                    indicatorColor = EggShelly,
               ),
                    selected = (selectedScreen == Screens.Info),
                    onClick = { navController.navigate(Screens.Info.route) },
                    modifier = Modifier
                        .shadow(50.dp)
                        .padding(10.dp),
                    icon = {
                        Icon(
                            imageVector= ImageVector.vectorResource(id = R.drawable.cardsicon),
                            contentDescription = null,
                            modifier = Modifier
                            .size(70.dp, 70.dp)
                        )
                    }
                )

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Black,
                    unselectedIconColor = EggShelly,
                    indicatorColor = EggShelly,
                ),
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                modifier = Modifier
                    .shadow(50.dp)
                    .padding(10.dp),
                icon = {
                    Icon(
                        imageVector= ImageVector.vectorResource(id = R.drawable.homeicon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp, 70.dp)
                        )
                }
            )

           NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Black,
                    unselectedIconColor = EggShelly,
                    indicatorColor = EggShelly,
                ),
                selected = (selectedScreen == Screens.Account),
                onClick = { navController.navigate(Screens.Account.route) },
                modifier = Modifier
                    .shadow(50.dp)
                    .padding(10.dp),
                icon = {
                   Icon(
                        imageVector= ImageVector.vectorResource(id = R.drawable.profileicon), contentDescription = null,
                        modifier = Modifier
                            .size(70.dp, 70.dp)
                    )
                }
           )
        }
    }
}

@Composable
fun HomeScreen(mainViewModel: MainViewModel, navController: NavHostController) {

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
                .padding(start = 25.dp, end = 25.dp, top = 50.dp, bottom = 25.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ){
            Text(
                buildAnnotatedString { append("Hello, stranger!\n") },
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
            )
            Text(
                buildAnnotatedString { append("Have an arcane day!") },
                fontSize = 20.sp,
                color = Color(0xFFA9A9A9),
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y =(-25).dp),
            )
            Column (){
                Button(
                    onClick = {
                        mainViewModel.navigateToDrawDailyScreen(navController)
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .size(700.dp, 700.dp)
                        .absoluteOffset(0.dp, (-60).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =Color.Transparent,
                        contentColor = Black
                    ))
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                            .size(260.dp, 350.dp)
                            .background(color = Black, RoundedCornerShape(20.dp))
                            .zIndex(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                                .size(260.dp, 400.dp)
                                .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                                .zIndex(1f)
                        ) {}
                        Image(
                            painter = painterResource(id = R.drawable.tarotcardsrandom),
                            contentDescription = "tarot cards",
                            modifier = Modifier
                                .scale(1.3f)
                                .padding(top = 25.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                                .absoluteOffset(x = 0.dp, y = (-85).dp)
                                .zIndex(2f)

                        )
                        //Spacer(modifier = Modifier.height(90.dp))
                        Text(
                            "Daily card",
                            fontSize = 24.sp,
                            letterSpacing = 0.15.em,
                            fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                            color = EggShelly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 255.dp, bottom = 0.dp, start = 20.dp, end = 0.dp)

                        )
                        Text(
                            "Draw a daily card to read your fortune!",
                            fontSize = 16.sp,
                            letterSpacing = 0.15.em,
                            fontFamily = FontFamily(Font(R.font.artifika_regular, FontWeight.Light)),
                            color = EggShelly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 295.dp, bottom = 0.dp, start = 20.dp, end = 0.dp)
                        )
                    }
                }
            }
            Text(
                "Tarot helps us look within ourselves to understand our emotions, the reasoning behind our words and conduct, and the source of our conflicts. \n" +
                        "~ Benebell Wen",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.artifika_regular, FontWeight.Light)),
                color = White,
                fontSize = 12.sp,
                lineHeight = 25.sp,
                modifier = Modifier
                    .absoluteOffset(0.dp, (-100).dp)
            )
        }
    }

}
@Composable
fun DrawDailyScreen(mainViewModel: MainViewModel, navController: NavHostController) {
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
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .zIndex(2f)
            .fillMaxSize()
            .padding(start = 5.dp, end = 25.dp, bottom = 25.dp)
            .absoluteOffset(x = 0.dp, y = 10.dp),
    ) {
        Button(
            onClick = { navController.navigate(Screens.Home.route)},
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
            )}
        Text(
            "Draw Daily",
            textAlign = TextAlign.Center,
            color = EggShelly,
            fontFamily = FontFamily(Font(R.font.almendra_regular, FontWeight.Light)),
            fontSize = 24.sp,
            letterSpacing = 0.15.em,
            modifier = Modifier
                .padding(start = 30.dp, top = 15.dp)
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .padding(top = 20.dp, start = 25.dp, end = 25.dp, bottom = 25.dp)
    ) {
        Text(
            text = "Draw your daily card and see how the day goes!",
            color = White,
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.almendra_regular, FontWeight.Light)),
            modifier = Modifier.padding(top = 100.dp, start = 25.dp),
            textAlign = TextAlign.Center,
            lineHeight = 30.sp
        )
        Image(
            painter = painterResource(id = R.drawable.handtarot),
            contentDescription = "Tarot Card",
            modifier = Modifier
                .fillMaxWidth()
                .scale(1.5f)
                .padding(top = 40.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp, start = 25.dp, end = 25.dp)
            // .size(60.dp, 500.dp),
        ) {
            Text(
                text = "Your deck today ",
                color = White,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.almendra_regular, FontWeight.Light)),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth()
            )

            Image(
                painter = painterResource(id = R.drawable.line),
                contentDescription = "line",
                modifier = Modifier
                    .size(400.dp, 100.dp)
                    .padding(top = 10.dp)
            )

            Box(
                modifier = Modifier
                    .padding(top= 10.dp, start = 5.dp, end = 5.dp)


            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
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
        }}
}


@Composable
fun InfoScreen(mainViewModel: MainViewModel, navController: NavHostController) {
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
                .fillMaxWidth()
                .padding(start = 25.dp, top = 50.dp, end = 20.dp, bottom = 20.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ){
            Text(
                buildAnnotatedString { append("Your tarot archive\n")
                },
                fontSize = 24.sp,
                color = White,
                fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
            )
            Text(
                buildAnnotatedString { append("Stay educated!")
                },
                fontSize = 22.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y =(-25).dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                buildAnnotatedString { append("Here you can find information about tarot and the art of reading your card draws. For a more in depth interpretation the general meaning of the deck consisting of 87 cards  is split into the minor and major arcana.")
                },
                color = White,
                textAlign = TextAlign.Justify,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(40.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Button(
                    onClick = { /*Understanding tarot - page*/ },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EggShelly,
                        contentColor = Black,
                        disabledContentColor = Black
                    ),
                )
                {
                    Text(
                        buildAnnotatedString { append("Understanding Tarot")
                        },
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = { /*Reading the cards - page*/ },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EggShelly,
                        contentColor = Black,
                        disabledContentColor = Black
                    ),
                )
                {
                    Text(
                        buildAnnotatedString { append("Reading the cards")
                        },
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(34.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    /*
                   Button(
                       onClick = { /*The minor arcana - page*/ },
                       shape = RoundedCornerShape(30.dp),
                       modifier = Modifier
                           .height(170.dp)
                           .width(160.dp)
                       ,
                       colors = ButtonDefaults.buttonColors(
                           containerColor = EggShelly,
                           contentColor = Black,
                           disabledContentColor = Black
                       ),
                   )
                   {
                       Text(
                           buildAnnotatedString { append("The minor arcana")
                           },
                           color = Black,
                           textAlign = TextAlign.Center,
                           fontSize = 20.sp,
                           fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                           modifier = Modifier
                       )
                   }
                   Button(
                       onClick = { /*The major arcana - page*/ },
                       shape = RoundedCornerShape(30.dp),
                       modifier = Modifier
                           .height(170.dp)
                           .width(160.dp)
                           .background(
                               brush = Brush.verticalGradient(
                                   colors = listOf(Black, PitchBlack)
                               )
                           ),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color.Transparent,
                           contentColor = Black,
                           disabledContentColor = Black
                       ),
                   )
                   {
                       Text(
                           buildAnnotatedString { append("The major arcana")
                           },
                           color = Black,
                           textAlign = TextAlign.Center,
                           fontSize = 20.sp,
                           fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                           modifier = Modifier
                       )
                   }
               }
               */
                Button(
                    onClick = { /*The minor arcana - page*/ },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(195.dp)
                        .width(165.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =Color.Transparent,
                        contentColor = Black
                    ))
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                            .height(190.dp)
                            .width(170.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                colors = listOf(Black, PitchBlack)
                            ), RoundedCornerShape(20.dp))
                            .zIndex(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                                .height(190.dp)
                                .width(170.dp)
                                .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                                .zIndex(1f)
                        ) {}
                        Image(
                            painter = painterResource(id = R.drawable.tarotcards),
                            contentDescription = "tarot cards",
                            modifier = Modifier
                                .scale(1.2f)
                                .absoluteOffset(x = 20.dp, y = (-18).dp)
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
                        onClick = { /*The major arcana - page*/ },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(195.dp)
                            .width(170.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =Color.Transparent,
                            contentColor = Black
                        ))
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                                .height(190.dp)
                                .width(170.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Black, PitchBlack)
                                    ), RoundedCornerShape(20.dp))
                                .zIndex(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 0.dp, bottom = 0.dp, start = 0.dp, end = 0.dp)
                                    .height(190.dp)
                                    .width(170.dp)
                                    .border(1.dp, DarkGray, RoundedCornerShape(20.dp))
                                    .zIndex(1f)
                            ) {}
                            Image(
                                painter = painterResource(id = R.drawable.fourcards),
                                contentDescription = "tarot cards",
                                modifier = Modifier
                                    .scale(1.4f)
                                    .absoluteOffset(x = 12.dp, y = (-14).dp)
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
fun AccountScreen(mainViewModel: MainViewModel, navController: NavHostController) {

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
                .padding(start = 25.dp, end = 25.dp, top = 50.dp, bottom = 25.dp)
                .absoluteOffset(x = 0.dp, y = 20.dp),
        ) {
            Text(
                buildAnnotatedString { append("Hello, stranger!\n") },
                fontSize = 24.sp,
                color = White,
                fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
            )
            Text(
                buildAnnotatedString { append("Have an arcane day!") },
                fontSize = 20.sp,
                color = MidGray,
                fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                modifier = Modifier
                    .absoluteOffset(x = 0.dp, y = (-25).dp),
            )
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            buildAnnotatedString { append("Lorem ipsum\n") },
                            fontSize = 16.sp,
                            color = MidGray,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                        )
                        Text(
                            buildAnnotatedString { append("Text") },
                            fontSize = 22.sp,
                            color = White,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                            modifier = Modifier
                                .absoluteOffset(0.dp, (-18).dp)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            buildAnnotatedString { append("Lorem ipsum\n") },
                            fontSize = 16.sp,
                            color = MidGray,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                        )
                        Text(
                            buildAnnotatedString { append("Text") },
                            fontSize = 22.sp,
                            color = White,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                            modifier = Modifier
                                .absoluteOffset(0.dp, (-18).dp)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            buildAnnotatedString { append("Lorem ipsum\n") },
                            fontSize = 16.sp,
                            color = MidGray,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                        )
                        Text(
                            buildAnnotatedString { append("Text") },
                            fontSize = 22.sp,
                            color = White,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                            modifier = Modifier
                                .absoluteOffset(0.dp, (-18).dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Image(painter = painterResource(id = R.drawable.dividercircle),
                    contentDescription = "divider",
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(3f)
                        //.padding(16.dp)
                        //.absoluteOffset(x = 20.dp, y = (-20).dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                        ) {
                                Text(
                                    buildAnnotatedString { append("Card Name") },
                                    fontSize = 22.sp,
                                    color = White,
                                    fontFamily = FontFamily(Font(R.font.almendra_regular, FontWeight.Light)),
                                    textAlign = TextAlign.Start
                                )
                                Spacer(modifier = Modifier.width(20.dp))
                                Text(
                                    buildAnnotatedString { append("14.01.2024") },
                                    fontSize = 14.sp,
                                    color = White,
                                    fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.absoluteOffset(0.dp, (-3).dp)
                                )
                        }
                        Text(
                            buildAnnotatedString { append("I need to be mindful of...\n") },
                            fontSize = 16.sp,
                            color = White,
                            textAlign = TextAlign.Start,
                            fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
                        )
                    }
                    IconButton(
                        onClick = {
                            // delete element
                        }
                    ) {
                        Icon(
                            imageVector= ImageVector.vectorResource(id = R.drawable.deletebin),
                            contentDescription = "delete button",
                            modifier = Modifier
                                .size(40.dp, 40.dp)
                                .absoluteOffset(0.dp, 2.dp),
                            tint = White
                        )
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

    AlertDialog(
        onDismissRequest = {
            // mainViewModel.closeDialog()
        },
        text = {
               // text and text fields
        },
        confirmButton = {
            Button(
                onClick = {
                    // TarotCard()
                },
                modifier = Modifier
                    .padding(bottom = 10.dp),

                ) {
                Text(text = "Confirm", color = EggShelly)
            }
        }
    )
}
