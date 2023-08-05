package com.example.readerapp.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.readerapp.components.FABcontent
import com.example.readerapp.components.ListCard
import com.example.readerapp.components.ReaderAppBar
import com.example.readerapp.components.TitleSection
import com.example.readerapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderHomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel() // we can also write viewModel()
){
    Scaffold(
        topBar = { ReaderAppBar(
            title = "A.Reader",
            navController = navController
        ) },
        floatingActionButton = {
            FABcontent{
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
            //Home Content
        HomeContent(navController = navController, homeViewModel = homeViewModel)
        }
    }
}



@Composable
fun HomeContent(navController: NavController, homeViewModel : HomeViewModel){
    var listOfBooks by remember {
        mutableStateOf(emptyList<MBook>())
    }
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!homeViewModel.data.value.data.isNullOrEmpty()){
        Log.d("BOOKS","HomeContent : ${homeViewModel.data.value.data}")

        listOfBooks = homeViewModel.data.value.data!!.toList()
            .filter {mBook ->
                mBook.userId == currentUser?.uid
        }
        Log.d("Books","HomeContent : ${listOfBooks.toString()}")
    }



    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if(!email.isNullOrEmpty()){
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    } else {
        "N/A"
    }
    Column(
        modifier = Modifier.padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(modifier = Modifier.align(Alignment.Start)) {
            TitleSection(label = "Your reading \n activity right now.")

            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.secondaryContainer
                )
                if (currentUserName != null) {
                    Text(
                        text = currentUserName,
                        modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Red,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
                Divider()
            }
        }
        ReadingRightNowArea(
            books = listOfBooks,
            navController = navController
        )
        TitleSection(label = "Reading List")

        BookListArea(
            listOfBooks = listOfBooks,
            navController = navController
        )

    }
}

@Composable
fun BookListArea(
    listOfBooks: List<MBook>,
    navController: NavController) {
    val addedBooks = listOfBooks.filter {mBook ->
        mBook.startReading == null && mBook.finishReading == null

    }
    HorizontalScrollableComponent(addedBooks){
        Log.d("TAG","BookListArea:$it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: HomeViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit
) {
//    val scrollState = rememberScrollState()
    if (viewModel.data.value.loading== true){
        LinearProgressIndicator()
    }else{
        if (listOfBooks.isNullOrEmpty() ){
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No books found.Add a Book",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
        }else{
            LazyRow{
                items(listOfBooks){mbook->
                    ListCard(book = mbook){
                        onCardPressed(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(
    books : List<MBook>,
    navController : NavController
){
    val readingNowList = books.filter {mBook ->
        mBook.startReading != null && mBook.finishReading == null

    }
    HorizontalScrollableComponent(readingNowList){
        Log.d("TAG","BookListArea:$it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}



