package com.example.readerapp.screens.description

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.screens.home.HomeViewModel

@Composable
fun Description(navController: NavController){
//    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
//        Row(modifier = Modifier.fillMaxWidth()) {
//            Icon(
//                modifier = Modifier.clickable {
//                    navController.navigateUp()
//                },
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back"
//            )
//            Text(
//                text = "About this eBook",
//                style = MaterialTheme.typography.bodyLarge,
//                fontFamily = FontFamily.Default,
//                fontWeight = FontWeight.SemiBold
//            )
//            Text(
//                text = homeViewModel.item.volumeInfo.description,
//                modifier = Modifier.padding(8.dp).fillMaxWidth().height(100.dp),
//                style = MaterialTheme.typography.bodyMedium,
//                fontFamily = FontFamily.Default
//            )
//            Text(
//                text = buildAnnotatedString {
//                    append("Categories-: " + homeViewModel.item.volumeInfo.categories)
//                    addStyle(
//                        style = SpanStyle(textDecoration = TextDecoration.Underline),
//                        start = 0,
//                        end = ("Categories-: " + homeViewModel.item.volumeInfo.categories).length
//                    ) },
//                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
//                fontSize = 15.sp
//            )
//            Text(
//                text = buildAnnotatedString {
//                    append("Page No.-: " + homeViewModel.item.volumeInfo.pageCount)
//                    addStyle(
//                        style = SpanStyle(textDecoration = TextDecoration.Underline),
//                        start = 0,
//                        end = ("Page No.-: " + homeViewModel.item.volumeInfo.pageCount).length
//                    ) },
//                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
//                fontSize = 15.sp
//            )
//            Text(
//                text = buildAnnotatedString {
//                    append("Year-: " + homeViewModel.item.volumeInfo.publishedDate)
//                    addStyle(
//                        style = SpanStyle(textDecoration = TextDecoration.Underline),
//                        start = 0,
//                        end = ("Year-: " + homeViewModel.item.volumeInfo.publishedDate).length
//                    ) },
//                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
//                fontSize = 15.sp
//            )
//            Text(
//                text = buildAnnotatedString {
//                    append("Publisher-: " + homeViewModel.item.volumeInfo.publisher)
//                    addStyle(
//                        style = SpanStyle(textDecoration = TextDecoration.Underline),
//                        start = 0,
//                        end = ("Publisher-: " + homeViewModel.item.volumeInfo.publisher).length
//                    ) },
//                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
//                fontSize = 15.sp
//            )
//            Text(
//                text = buildAnnotatedString {
//                    append("Language-: " + homeViewModel.item.volumeInfo.language)
//                    addStyle(
//                        style = SpanStyle(textDecoration = TextDecoration.Underline),
//                        start = 0,
//                        end = ("Language-: " + homeViewModel.item.volumeInfo.language).length
//                    ) },
//                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
//                fontSize = 15.sp
//            )
//            Text(
//                text = buildAnnotatedString {
//                    append(
//                        "ISBN 10 -: " + homeViewModel.item.volumeInfo.industryIdentifiers[0].identifier +
//                                "\n ISBN 13 -: " + homeViewModel.item.volumeInfo.industryIdentifiers[1].identifier
//                    )
//                    addStyle(
//                        style = SpanStyle(textDecoration = TextDecoration.Underline),
//                        start = 0,
//                        end = ("ISBN 10 -: " + homeViewModel.item.volumeInfo.industryIdentifiers[0].identifier +
//                                "\n ISBN 13 -: " + homeViewModel.item.volumeInfo.industryIdentifiers[1].identifier).length
//                    ) },
//                modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
//                fontSize = 15.sp
//            )
//        }
//    }
}