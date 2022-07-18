package com.example.workoutprogramapp

class Item(
    val id: String,
    val imageUrl: String,
    val name: String,
    val time: String
) {
    constructor() : this("", "", "","") {

    }
}