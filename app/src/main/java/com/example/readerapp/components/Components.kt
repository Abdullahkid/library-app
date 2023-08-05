package com.example.readerapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.readerapp.R
import com.example.readerapp.navigation.ReaderScreens
import com.example.readerapp.screens.home.MBook
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EmailInput(
    modifier : Modifier = Modifier,
    emailState: MutableState<String>,
    labelId :String = "Email",
    enabled:Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
){
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier,
    valueState: MutableState<String>,
    labelId:String,
    enabled:Boolean,
    isSingleLine:Boolean =true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = labelId) },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )
}

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    label :String
){
    Surface(modifier = modifier.padding(start = 5.dp,top = 1.dp)) {
        Column {
            Text(
                text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title:String,
    showProfile: Boolean = true,
    navController : NavController,
    onNavigate : () -> Unit ={}
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile){
                    Image(
                        painter = painterResource(id = R.drawable.reader),
                        contentDescription = "App Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .size(40.dp)
//                                .scale(0.6f)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.width(150.dp))
            }
        },
        navigationIcon = {
            if (!showProfile){
                IconButton(onClick = {onNavigate.invoke()}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack ,
                        contentDescription = "Arrow Back"
                    )
                }
            }
        },
        actions = {
            if (showProfile){
                IconButton(onClick = {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreens.LoginScreen.name)
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Logout",
                        modifier = Modifier.size(20.dp),
                        alpha = 0.4F
                    )
                }
            }else{
                Row() {
                    
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(),
        modifier = Modifier.shadow(10.dp)
    )
}

@Composable
fun FABcontent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color(0xFF6385AF)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a book",
            tint = Color.White
        )
    }
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(modifier = Modifier
        .height(60.dp)
        .padding(6.dp),
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 6.dp,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable._2x32),
                contentDescription = "Star outline",
                modifier = Modifier
                    .padding(3.dp)
                    .size(18.dp)
            )
            Divider(modifier = Modifier.width(22.dp))
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun RoundedButton(
    label:String = "Reading",
    radius: Int = 29,
    onPress:()->Unit = {}
){
    Surface(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomEndPercent = radius,
                    topStartPercent = radius
                )
            ),
        color = Color(0xFF92CBDF)
    ){
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Composable
fun ListCard(
    book : MBook ,
    onPressDetails:(String)->Unit = {}
){
    val context = LocalContext.current
    //context gives the info regarding everything
    // of the drawing or about the composable
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    //displayMetrics gets the information about where we are displaying our apps.
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp
    Card(
        shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable {
                onPressDetails.invoke(book.googleBookId.toString())
            }
    ){
        Column(
            modifier = Modifier
                .padding()
                .width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
                        .data(book.photoUrl)
                        .size(Size.ORIGINAL)
                        .build()),
                    contentDescription = "book image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(topStartPercent = 30))
                )
                //Spacer(modifier = Modifier.width(50.dp))
                Column(modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                    BookRating(score = 3.5)
                }
            }
            book.title?.let {
                Text(
                    text = it,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            book.author?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            val isStartedReading = remember{
                mutableStateOf(false)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom) {
                isStartedReading.value = book.startReading != null
                RoundedButton(label =if (isStartedReading.value) "Reading" else "Not Yet",radius = 70)
            }
        }
    }
}

