package com.example.readerapp.repository

import android.util.Log
import com.example.readerapp.data.DataOrException
import com.example.readerapp.screens.home.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBook:Query
){
    suspend fun getAllBooksFromDatabase():DataOrException<List<MBook>,Boolean,Exception>{
        val dataOrException = DataOrException<List<MBook>,Boolean,Exception>()
        try {
            dataOrException.loading = true
            dataOrException.data = queryBook.get()
                .await()
                .documents
                .map {documentSnapshot ->
                    documentSnapshot.toObject(MBook::class.java)!!
                }
            //Converting database data into MBook object
            if (!dataOrException.data.isNullOrEmpty())dataOrException.loading = false
        }catch (exception:FirebaseFirestoreException){
            Log.d("ERROR","${dataOrException.e}")
            dataOrException.e = exception
        }
        Log.d("Fire","${dataOrException.data}")
        return dataOrException
    }
}
