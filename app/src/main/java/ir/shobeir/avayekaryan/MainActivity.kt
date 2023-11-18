package ir.shobeir.avayekaryan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import ir.shobeir.avayekaryan.home.MainScreen
import ir.shobeir.avayekaryan.navGragh.AvaApp
import ir.shobeir.avayekaryan.ui.theme.AvayeKaryanTheme
import ir.shobeir.avayekaryan.ui.theme.shabnamBold
import ir.shobeir.baladomapp.cheknet.ConnectionState
import ir.shobeir.baladomapp.cheknet.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AvayeKaryanTheme {
                val connection by connectivityState()
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        if (connection == ConnectionState.Available) {
                        AvaApp()
                } else {
                    Column(
                        Modifier.fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id = R.drawable.wifi_off_icon), contentDescription ="noNet" )
                        Text(
                            "به اینترنت دسترسی ندارید!",
                            fontSize = 25.sp,
                            fontFamily = shabnamBold,
                            fontWeight = FontWeight.W600,
                            color = Color.Red
                        )
                    }
                }
              }

            }
        }
    }
}

