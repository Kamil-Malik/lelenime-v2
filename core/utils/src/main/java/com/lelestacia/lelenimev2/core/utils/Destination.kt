package com.lelestacia.lelenimev2.core.utils

sealed class Destination(val route: String) {
    data object WaifuImageScreen : Destination(route = "anime_image")
    data object WaifuImageOptionScreen : Destination(route = "options/{image_json}") {
        fun createRoute(imageJson: String): String {
            return this.route.replace(
                oldValue = "{image_json}",
                newValue = imageJson
            )
        }
    }
}