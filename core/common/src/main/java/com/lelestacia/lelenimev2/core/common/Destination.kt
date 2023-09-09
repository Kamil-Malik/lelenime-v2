package com.lelestacia.lelenimev2.core.common

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

    data object WaifuImageDetail : Destination(route = "anime_image/detail/{image_json}") {
        fun createRoute(imageJson: String): String {
            return this.route.replace(
                oldValue = "{image_json}",
                newValue = imageJson
            )
        }
    }

    data object PopularAnime : Destination("anime/popular")
    data object AiringAnime : Destination("anime/airing")
    data object UpcomingAnime : Destination("anime/upcoming")

    data object Setting : Destination("setting")
    data object Info : Destination("info")
}