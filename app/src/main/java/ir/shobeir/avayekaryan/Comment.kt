package ir.shobeir.avayekaryan

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.shobeir.avayekaryan.ui.theme.LightBlack
import ir.shobeir.avayekaryan.ui.theme.green
import ir.shobeir.avayekaryan.ui.theme.shabnamBold
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommentScreen(idPost:String) {
    val viewModelComment = hiltViewModel<CommentViewModel>()
    val context = LocalContext.current
    var status by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(key1 =true ){
        viewModelComment.status.collectLatest {
            it?.let {msg->
                status=msg
            }
        }
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(LightBlack)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var name by remember {
                mutableStateOf("")
            }
            var comment by remember {
                mutableStateOf("")
            }
            var error by remember {
                mutableStateOf("")
            }
            if (name != "" && comment !=""){
                error=""
            }
            Text(text = "نظرات خود را با ما درمیان بگذارید",
                fontWeight = FontWeight.Bold,
                fontFamily =shabnamBold, fontSize = 20.sp)
            Text(text = "نظراتی که در حدود قانون و ادب بوده و حاوی تهمت و توهین نباشد منتشر می شود",
                fontFamily =shabnamBold, fontSize = 18.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "نام...",fontFamily =shabnamBold)
                },
                textStyle = TextStyle(fontFamily = shabnamBold)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = comment,
                onValueChange = {comment = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                placeholder = {
                    Text(text = "نظرشما...",fontFamily =shabnamBold, textAlign = TextAlign.Start)
                },
                textStyle = TextStyle(fontFamily = shabnamBold)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if (name=="" && comment==""){
                    error="نام و نظر خود را بنویسید"
                }else{
                    scope.launch {
                        viewModelComment.setComment(comment,name,idPost)
                    }
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(green),

            ) {
                Text(text = "ثبت نظر",fontFamily =shabnamBold,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(10.dp))
            status?.let {
                Text(text =it, color = green,
                    fontFamily =shabnamBold)
            }
            Text(text = error,color = Color.Red,
                fontFamily =shabnamBold)
        }

    }
}