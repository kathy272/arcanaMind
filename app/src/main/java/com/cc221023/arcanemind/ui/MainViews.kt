package com.cc221023.arcanemind.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221023.arcanemind.R
import com.cc221023.arcanemind.ui.theme.Black
import com.cc221023.arcanemind.ui.theme.EggShelly
import com.cc221023.arcanemind.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    Scaffold(      bottomBar = {BottomNavigationBar(navController, state.value.selectedScreen)}){


        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screens.Home.route
        ) {
            composable(Screens.Home.route) {
                mainViewModel.selectScreen(Screens.Home)
                HomeScreen(mainViewModel)
            }

        }
    }
}
@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screens){
    Box(

        modifier = Modifier

            .fillMaxWidth()
           // .drawWithContent {

//                val linearGradient = Brush.verticalGradient(
//                    startY = 0f,
//                    endY = 30f,
//                    colors = listOf(
//                        Gray.copy(alpha = 0f),
//                        Gray.copy(alpha = 0.2f),
//                        Gray.copy(alpha = 0.5f),
//                        Gray.copy(alpha = 0.9f)
//                    )
//                )
//                drawRect(
//                    brush = linearGradient,
//                    topLeft = Offset(0f, 0f), // Adjust the offset as needed
//                    size = Size(size.width, 10f)
//                )
           // }
    ) {
        BottomNavigation(
            backgroundColor = Black,
            contentColor = Black,
            modifier = Modifier
                .shadow(50.dp)

            ,


        ) {
            NavigationBarItem(
           colors = NavigationBarItemDefaults.colors(

               selectedIconColor = EggShelly, //ändern schwarz
                unselectedIconColor = Black,
                indicatorColor = Black, //ändern eggshelly
           ),
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                modifier = Modifier
                    .shadow(50.dp)
                    .padding(10.dp)
                    //.background(color = EggShelly, shape = RoundedCornerShape(25.dp)
                        ,

                icon = {
                    Icon(
                    imageVector= ImageVector.vectorResource(id = R.drawable.cardsicon), contentDescription = null,
                    modifier = Modifier
                        .size(70.dp, 70.dp)

                       )  }

            )



            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor = EggShelly,
                    unselectedIconColor = Black,
                    indicatorColor = Black,
                ),
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                modifier = Modifier
                    .shadow(50.dp)
                    .padding(10.dp),
                icon = {
                    Icon(
                        imageVector= ImageVector.vectorResource(id = R.drawable.homeicon), contentDescription = null,
                    modifier = Modifier
                        .size(70.dp, 70.dp)

                    )  })
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor = EggShelly,
                    unselectedIconColor = Black,
                    indicatorColor = Black,
                ),
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                modifier = Modifier
                    .shadow(50.dp)
                    .padding(10.dp)
,                icon = {
                    Icon(
                        imageVector= ImageVector.vectorResource(id = R.drawable.profileicon), contentDescription = null,
                        modifier = Modifier
                            .size(70.dp, 70.dp)

                    )  })
        }
    }
}
@Composable
fun HomeScreen(mainViewModel: MainViewModel) {

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
            buildAnnotatedString { append("Hello, stranger!\n")
                                 },
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.almendra_bold, FontWeight.Light)),
        )
        Text(
            buildAnnotatedString { append("Have an arcane day!")
                                 },
            fontSize = 20.sp,
            color = Color(0xFFA9A9A9),
            fontFamily = FontFamily(Font(R.font.asap_regular, FontWeight.Light)),
            modifier = Modifier
            .absoluteOffset(x = 0.dp, y =(-25).dp),
        )
        Row (
            //horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            ){
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(25.dp),

                modifier = Modifier
                  //  .padding(top = 20.dp, bottom = 20.dp, start = 65.dp, end = 65.dp)
                    .padding(top = 20.dp, bottom = 20.dp, start = 65.dp, end = 65.dp)
                    .size(260.dp, 300.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EggShelly,
                    contentColor = Black

                ))
            {
Column (
    modifier = Modifier
    .fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
   // verticalArrangement = Arrangement.Center,
){


    Text(
        "Daily card", textAlign = TextAlign.Center,


        fontSize = 24.sp,

        letterSpacing = 0.15.em,
        fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
        modifier = Modifier
            .padding(top = 20.dp)
        // .absoluteOffset(y = (-100).dp),
    )
    Spacer(modifier = Modifier.height(90.dp))
    Image(painter = painterResource(id = R.drawable.tarotcards), contentDescription = "tarot cards", modifier = Modifier
        .scale(3.0f)

        //.absoluteOffset(x = (-7).dp, y = (4).dp)
    )
}

            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .size(60.dp, 300.dp),


        ){
          Text(
              "Tarot helps us look within ourselves to understand our emotions, the reasoning behind our words and conduct, and the source of our conflicts. \n" +
                  "~ Benebell Wen", textAlign = TextAlign.Center,

          fontFamily = FontFamily(Font(R.font.artifika_regular, FontWeight.Light)),
              color = White,
                fontSize = 16.sp,
              lineHeight = 30.sp,

          ) }
    }

    }
}