package com.example.readerapp.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.readerapp.components.InputField
import com.example.readerapp.components.ReaderAppBar
import com.example.readerapp.navigation.ReaderScreens


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(
    navController: NavController,
    viewModel: BookViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Search Books",
                navController = navController,
                showProfile = false,
                onNavigate = { navController.popBackStack() }
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            Column {
                SearchField(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    viewModel = viewModel
                ){search->
                    viewModel.searchBooks(query = search)
                }
                Spacer(modifier = Modifier.height(13.dp))
                //ScrollableColumn(navController, viewModel)
                BookList(navController,viewModel)
            }
        }
    }
}

@Composable
fun BookList(
    navController: NavController,
    viewModel: BookViewModel = hiltViewModel()
) {
    val listOfBooks = viewModel.list
    if (viewModel.isLoading){
        LinearProgressIndicator()
    }else{
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            items(listOfBooks){book->
                BookRow(book,navController)
            }
        }
    }
}

@Composable
fun BookRow(
    book: com.example.readerapp.model.Item,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(ReaderScreens.DetailScreen.name +"/${book.id}")
            }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(7.dp)
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ){
            val imageUrl = if(book.volumeInfo.imageLinks.thumbnail.isEmpty()){
                "http://books.google.com/books/content?id=1igDDgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api"
            }else{
                book.volumeInfo.imageLinks.thumbnail
            }
            Image(
                painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(Size.ORIGINAL)
                    .build()
                ),
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
                contentDescription = "book image")
            Column {
                Text(
                    text = book.volumeInfo.title.toString(),
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Author:${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    //fontStyle = FontStyle(25,100),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Publisher:${book.volumeInfo.publisher}",
                    overflow = TextOverflow.Clip,
                    //fontStyle = FontStyle(25,100),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Date:${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    //fontStyle = FontStyle(25,100),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "Categories:${book.volumeInfo.categories }",
                    overflow = TextOverflow.Clip,
                    //fontStyle = FontStyle(25,100),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchField(
    viewModel: BookViewModel,
    modifier: Modifier = Modifier,
    loading:Boolean = false,
    hint :String = "Search",
    onSearch :(String) -> Unit = {}
){

    Column() {
        val searchQueryState = rememberSaveable{ mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }
        
        InputField(
            modifier = Modifier ,
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid)return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

