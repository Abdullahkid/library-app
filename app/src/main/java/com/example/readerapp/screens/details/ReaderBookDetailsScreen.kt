package com.example.readerapp.screens.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.readerapp.R
import com.example.readerapp.components.RoundedButton
import com.example.readerapp.data.Resources
import com.example.readerapp.model.Item
import com.example.readerapp.navigation.ReaderScreens
import com.example.readerapp.screens.home.MBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
){
    val bookInfo = produceState<Resources<Item>>(initialValue = Resources.Loading()) {
        value = viewModel.getBookInfo(bookId = bookId)
    }.value

    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id
    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
            //.verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 7.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.clickable {
                    navController.navigate(ReaderScreens.SearchScreen.name)
                },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        if (bookInfo.data == null){
            LinearProgressIndicator()
        } else{
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(bookData?.imageLinks?.thumbnail)
                            .size(width = 450, height = 700).build()
                    ), contentDescription = "Book image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Column(horizontalAlignment = Alignment.Start) {
                    Row(horizontalArrangement = Arrangement.Start) {
                        bookInfo.data.volumeInfo.title.let {
                            Text(
                                text = it,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                style = MaterialTheme.typography.headlineMedium,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        var author = ""
                        bookInfo.data.volumeInfo.authors.forEachIndexed { index, s ->
                            author += "\n" + bookInfo.data.volumeInfo.authors[index]
                        }
                        Text(
                            text = author,
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Default
                        )
                        bookInfo.data.volumeInfo.publisher.let {
                            Text(
                                text = it,
                                modifier = Modifier,
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                                color = Color.Gray
                            )
                        }
                        bookInfo.data.volumeInfo.publishedDate.let {
                            Text(
                                text = it,
                                modifier = Modifier,
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = bookInfo.data?.volumeInfo?.pageCount.toString(),
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Default
                    )
                    Text(
                        text = "Pages",
                        modifier = Modifier,
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Default
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ebook),
                        modifier = Modifier.size(30.dp),
                        contentDescription = "ebook"
                    )
                    Text(
                        text = "ebook",
                        modifier = Modifier,
                        style = MaterialTheme.typography.labelMedium,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Default
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoundedButton(label = "Save"){
                    //Save this book to firestore database
                    val book = MBook(
                        title = bookData?.title,
                        author = bookData?.authors.toString(),
                        description = bookData?.description.toString(),
                        categories = bookData?.categories.toString(),
                        photoUrl = bookData?.imageLinks?.thumbnail,
                        publishDate = bookData?.publishedDate,
                        pageCount = bookData?.pageCount.toString(),
                        rating = 0.0,
                        googleBookId = googleBookId,
                        userId = FirebaseAuth.getInstance().currentUser?.uid
                    )
                    saveToFirebase(book,navController = navController)
                }
                RoundedButton(label = "Cancel"){
                    navController.navigateUp()
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "About this ebook",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "About",
                    modifier = Modifier.clickable {
                        navController.navigate(ReaderScreens.DescriptionScreen.name)
                    }
                )
            }
            val cleanDescription = HtmlCompat.fromHtml(bookInfo.data.volumeInfo.description,HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = cleanDescription,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Default
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Rating and Reviews",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "About")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 7.dp
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "",
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                )
            }
        }
    }
}


fun saveToFirebase(book: MBook,navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()){
        dbCollection.add(book)
            .addOnSuccessListener {documentRef->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId)as Map<String,Any>)
                    .addOnCompleteListener{task->
                        if (task.isSuccessful){
                            navController.navigateUp()
                        }
                    }.addOnFailureListener{
                        Log.w("Error","SaveToFirebase:Error updating document",
                            Throwable(message = it.toString())
                        )
                    }
            }
    }else{}
}


