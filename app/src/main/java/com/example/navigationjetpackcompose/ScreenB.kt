package com.example.navigationjetpackcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationjetpackcompose.Model.ProductModel


@Composable
fun ScreenB(product: ProductModel?) {
    product?.let {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            item { Text("Ürün Adı: ${it.title}", style = TextStyle(fontSize = 20.sp)) }
            item { Text("Açıklama: ${it.description}") }
            item { Text("Kategori: ${it.category}") }
            item { Text("Fiyat: ${it.price} ₺") }
            item { Text("İndirim: ${it.discountPercentage}%") }
            item { Text("Değerlendirme: ${it.rating}/5") }
            item { Text("Marka: ${it.brand}") }
            item { Text("Stok Durumu: ${it.availabilityStatus}") }
            item { Text("İade Politikası: ${it.returnPolicy}") }
            item {
                it.reviews.forEach { review ->
                    Text(
                        "Yorum: ${review.comment}\nPuan: ${review.rating}\nTarih: ${review.date}",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    } ?: run {
        Text(
            text = "Ürün bilgisi alınamadı.",
            modifier = Modifier.fillMaxSize().padding(16.dp),
            style = TextStyle(fontSize = 18.sp)
        )
    }
}
