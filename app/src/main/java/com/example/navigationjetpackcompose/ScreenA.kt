package com.example.navigationjetpackcompose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navigationjetpackcompose.Model.ProductModel
import com.example.navigationjetpackcompose.Model.ProductResponse
import com.example.navigationjetpackcompose.Service.ProductAPI
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://dummyjson.com/"

@Composable
fun ScreenA(navController: NavController) {
    var productModels by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var filteredProducts by remember { mutableStateOf<List<ProductModel>>(emptyList()) }
    var isError by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loadData(
            onSuccess = { models ->
                productModels = models
                filteredProducts = models
            },
            onError = { isError = true }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                filteredProducts = if (searchText.isBlank()) {
                    productModels
                } else {
                    productModels.filter { product ->
                        product.title.contains(searchText, ignoreCase = true)
                    }
                }
            },
            label = { Text("Ürün Ara") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isError -> {
                Text(
                    text = "Veri yüklenirken bir hata oluştu.",
                    modifier = Modifier.fillMaxSize(),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            productModels.isEmpty() -> {
                Text(
                    text = "Yükleniyor...",
                    modifier = Modifier.fillMaxSize(),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            else -> {
                ProductList(models = filteredProducts, navController = navController)
            }
        }
    }
}

private fun loadData(onSuccess: (List<ProductModel>) -> Unit, onError: () -> Unit) {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ProductAPI::class.java)
    val call = service.getData()

    call.enqueue(object : Callback<ProductResponse> {
        override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
            if (response.isSuccessful) {
                response.body()?.products?.let(onSuccess) ?: onError()
            } else {
                onError()
            }
        }

        override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
            onError()
        }
    })
}

@Composable
fun ProductList(models: List<ProductModel>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(models) { model ->
            ProductItem(product = model, onClick = {
                val productJson = Gson().toJson(model)
                navController.navigate("screenB/$productJson")
            })
        }
    }
}

@Composable
fun ProductItem(product: ProductModel, onClick: () -> Unit) {
    Text(
        text = "${product.title}: ${product.price} ₺",
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        style = TextStyle(fontSize = 16.sp)
    )
}
