package com.example.readerapp.screens.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.components.ReaderAppBar
import com.example.readerapp.screens.home.HomeViewModel
import com.example.readerapp.screens.home.MBook
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeViewModel
){
    var book : List<MBook>
    var currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            ReaderAppBar(
                title ="Book Stats",
                showProfile = false,
                navController = navController
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            book = if(!viewModel.data.value.data.isNullOrEmpty()){
                viewModel.data.value.data!!.filter {mBook ->
                    mBook.userId == currentUser?.uid

                }
            }else{
                emptyList()
            }
            Column {
                Row {
                    Box(modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp)){
                        Icon(
                            imageVector = Icons.Sharp.Person,
                            contentDescription = "person"
                        )
                    }
                    Text(
                        text = "Hi, ${
                        currentUser?.email.toString().split("@")[0]
                            .uppercase(Locale.getDefault())
                        }"
                    )
                }
            }
        }
    }
}