package com.example.readerapp.screens.update

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.readerapp.R
import com.example.readerapp.components.InputField
import com.example.readerapp.components.RoundedButton
import com.example.readerapp.data.DataOrException
import com.example.readerapp.navigation.ReaderScreens
import com.example.readerapp.screens.home.HomeViewModel
import com.example.readerapp.screens.home.MBook
import com.example.readerapp.utils.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookUpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: HomeViewModel = hiltViewModel(),
){
//produceState function is part of state management lib used in kotlin,
//the function here is used to create a state that can hold three peices of info.
//a list of MBook objects , a loading flag (boolean) and an exception in case there is an error.
    val bookInfo =
        produceState<DataOrException<List<MBook>,Boolean,Exception>>(
            initialValue = DataOrException(
                data = emptyList(),
                loading = true,
                e = Exception("")
            )
        ){
                value = viewModel.data.value
        }.value

    Column(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
        //.verticalScroll(rememberScrollState())
    ) {
        Log.d("INFO","BookUpdateScreen:${viewModel.data.value.data.toString()}")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 7.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Log.d("INFO","BookUpdateScreen: ${viewModel.data.value.data.toString()}")
        if (bookInfo.loading == true){
            Log.d("LOAD","bookInfo: ${bookInfo.data} ")
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFF56F7E4))
            bookInfo.loading = false
        }else{
            Surface(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                shadowElevation = 4.dp){
                ShowBookUpdate(
                    bookInfo = viewModel.data.value,
                    bookItemId = bookItemId
                )
            }
            ShowSimpleForm(
                book = viewModel.data.value.data?.first { mBook ->
                    mBook.googleBookId == bookItemId
                }!!,
                navController
            )
        }
    }
}

fun ifOrNot(book: MBook,): String {
    Log.d("notes","${book.notes.toString().isNullOrEmpty()}")
    if (!book.notes.isNullOrEmpty()){
        return book.notes.toString()
    }
    return "No thoughts available"
}
@Composable
fun ShowSimpleForm(book: MBook, navController: NavController) {
    val notesText = remember{
        mutableStateOf("")
    }
    val isStartReading = remember{
        mutableStateOf(false)
    }
    val isReadingfinished = remember{
        mutableStateOf(false)
    }
    val ratingVal = remember{
        mutableStateOf(0)
    }
    val context = LocalContext.current

    var confirm by remember{
        mutableStateOf(false)
    }

    // if (book.notes.toString().isNullOrEmpty()) "No thoughts available.")else{book.notes.toString()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        SimpleForm(defaultValue = ifOrNot(book = book)) { note ->
            notesText.value = note
        }
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = { isStartReading.value = true },
                enabled = book.startReading == null
            ) {
                if (book.startReading == null) {
                    if (!isStartReading.value) {
                        Text(text = "Started Reading")
                    } else {
                        Text(
                            text = "Started Reading",
                            modifier = Modifier.alpha(0.6f),
                            color = Color.Red.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    Text(text = "Started on: ${formatDate(book.startReading!!)}")
                }
            }
            TextButton(
                onClick = { isReadingfinished.value = true },
                enabled = book.finishReading == null
            ) {
                if (book.finishReading == null) {
                    if (!isReadingfinished.value) {
                        Text(text = "Mark as Read")
                    } else {
                        Text(
                            text = "Mark as Read",
                            modifier = Modifier.alpha(0.6f),
                            color = Color.Red.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    Text(text = "Finished on: ${formatDate(book.finishReading!!)}")
                }
            }
        }
        Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))
        book.rating?.toInt().let {
            RatingBar(rating = it!!){rating->
                ratingVal.value = rating
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            val changeNotes = book.notes != notesText.value
            val changeRating = book.rating?.toInt() != ratingVal.value
            val isFinishedTimeStamp = if (isReadingfinished.value) Timestamp.now() else book.finishReading
            val isStartedTimeStamp = if (isStartReading.value) Timestamp.now() else book.startReading

            val bookUpdate = changeNotes|| changeRating || isStartReading.value || isReadingfinished.value
            val bookToUpdate = hashMapOf(
                "finishReading" to isFinishedTimeStamp,
                "startReading" to isStartedTimeStamp,
                "rating" to ratingVal.value,
                "notes" to notesText.value
            ).toMap()

            RoundedButton(label = "Update"){
                if (bookUpdate){
                    FirebaseFirestore.getInstance()
                        .collection("books")
                        .document(book.id!!)
                        .update(bookToUpdate)
                        .addOnCompleteListener{
                            showToast(context =context , msg = "Book Updated Successfully!")
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                            //Log.d("","ShowSimpleForm: ${task.result}")
                        }.addOnFailureListener{task->
                            Log.d("Error","Error updating document:", task)
                        }
                }
            }
            RoundedButton(label = "Delete"){
                confirm = true
            }
        }
    }
    if (confirm){
        AlertDialog(
            onDismissRequest = { confirm = false },
            confirmButton = {
                Row (modifier = Modifier.padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom){
                    Button(onClick = {
                        FirebaseFirestore.getInstance()
                            .collection("books")
                            .document(book.id!!)
                            .delete()
                            .addOnCompleteListener{
                                if (it.isSuccessful){
                                    confirm = false
                                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                                }
                            }
                    }) {
                        Text(text = "Yes")
                    }
                    Button(onClick = { confirm = false }) {
                        Text(text = "No")
                    }
                }
            },
            title = { Text(
                text = "Are you sure you want to delete this book",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )},
            shape = RoundedCornerShape(10.dp)
        )
    }else{
        confirm = false
    }
}
fun showToast(context:Context,msg:String) {
    Toast.makeText(context,msg,Toast.LENGTH_LONG)
        .show()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading:Boolean = false,
    defaultValue: String =  "Great Book!",
    onSearch:(String)->Unit
) {
    Column() {
        val textFieldValue = rememberSaveable { mutableStateOf(defaultValue) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue.value){
            textFieldValue.value.trim().isNotEmpty()
        }
        InputField(
            modifier = modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(3.dp)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = textFieldValue,
            labelId = "Enter your thoughts",
            enabled = true,
            onAction = KeyboardActions{
                if (!valid)return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                keyboardController?.hide()
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating : Int,
    onPressRating: (Int)-> Unit
){
    var ratingState by remember{
        mutableStateOf(rating)
    }
    var selected by remember{
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        targetValue = if (selected)42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy), label = ""
    )
    Row (
        modifier = Modifier
            .width(200.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        for (i in 1..5){
            Icon(painter = painterResource(
                id = R.drawable.star_32),
                contentDescription = "rating",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressRating(i)
                                ratingState = i
                            }

                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i<= ratingState) Color(0xFFFFD700)else Color(0xFFA2ADB1)
            )
        }
    }

}

@Composable
fun ShowBookUpdate(bookInfo: DataOrException<List<MBook>,
        Boolean, Exception>,bookItemId: String) {
    Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        if (bookInfo.data != null){
            Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
                CardListItem(book = bookInfo.data!!.first{ mBook ->
                        mBook.googleBookId == bookItemId
                          },onPressDetails = {}
                )
            }
        }
    }
}
@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable { },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(book.photoUrl.toString())
                        .size(width = 450, height = 700).build()
                ), contentDescription = "Book image",
                modifier = Modifier
                    .height(140.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = book.title.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.author.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.publishDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
