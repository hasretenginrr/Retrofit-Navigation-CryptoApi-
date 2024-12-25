package com.example.navigationjetpackcompose.Model

data class ProductResponse(
    val products: List<ProductModel>
)

data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String>,
    val brand: String,
    val dimensions: Dimensions,
    val warrantyInformation: String,
    val reviews: List<Review>,
    val returnPolicy: String,
    val availabilityStatus: String,

)

data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)

