package com.cc221023.arcanemind.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221023.arcanemind.R

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221023.arcanemind.ui.theme.Black
import com.cc221023.arcanemind.ui.theme.EggShelly
import com.cc221023.arcanemind.ui.theme.White

import java.text.SimpleDateFormat
import java.util.Date

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
    ) {
        BottomNavigation(
            backgroundColor = Black,
            contentColor = EggShelly,
            modifier = Modifier
                .shadow(50.dp)
        ) {
            NavigationBarItem(
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                icon = {
                    Icon(
                    imageVector= ImageVector.vectorResource(id = R.drawable.cardsicon), contentDescription = null) })



            NavigationBarItem(
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                icon = {
                    Icon(
                        imageVector= ImageVector.vectorResource(id = R.drawable.homeicon), contentDescription = null) })
            NavigationBarItem(
                selected = (selectedScreen == Screens.Home),
                onClick = { navController.navigate(Screens.Home.route) },
                icon = {
                    Icon(
                        imageVector= ImageVector.vectorResource(id = R.drawable.profileicon), contentDescription = null) })
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
        Column (

            horizontalAlignment = Alignment.CenterHorizontally,


            ){
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(25.dp),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 65.dp, end = 65.dp)
                    .size(30.dp, 300.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EggShelly,
                    contentColor = Black

                ))
            {

                Text(
                    "Daily card",textAlign = TextAlign.Center,


                    fontSize = 24.sp,

                    letterSpacing = 0.15.em,
                    fontFamily = FontFamily(Font(R.font.asap_bold, FontWeight.Light)),
                    modifier = Modifier
                        .absoluteOffset( y =(-100).dp)
                    ,
                )
                Image(painter = painterResource(id = R.drawable.tarotcard), contentDescription = "tarot cards", modifier = Modifier
                    .scale(7.3f)

                    .absoluteOffset(x = (-7).dp, y = (4).dp)
                )

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
              lineHeight = 24.sp,

          ) }
    }

    }
}