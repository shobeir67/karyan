package ir.shobeir.avayekaryan.navGragh

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.shobeir.avayekaryan.ui.theme.AvayeKaryanTheme
import ir.shobeir.avayekaryan.ui.theme.shabnamMedium


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun AvaApp() {
    AvayeKaryanTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            val navController = rememberNavController()
                         Scaffold(
                    bottomBar = { BottomBar(navController = navController) }
                ) {
                    BaladomNavGraph(
                        navController = navController,
                    )
                }
        }
    }

}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Home,
        Screen.Gallery,
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth().padding(3.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun  AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background =
        if (selected) Color.Blue.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {


//            //* if menu title : Report means we will show badge
//            if(screen.title=="Report"){ // with badge
//                BadgedBox(badge = { Badge { Text("6") } }) {
//                    Icon(
//                        painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
//                        contentDescription = "icon",
//                        tint = contentColor
//                    )
//                }
//
//            }
//            else{

                Icon(
                    painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                    contentDescription = "icon",
                    tint = contentColor
                )

//            }

            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    fontFamily = shabnamMedium,
                    color = contentColor,
                    fontSize = 14.sp
                )
            }
        }
    }
}



