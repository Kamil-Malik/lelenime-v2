package com.lelestacia.lelenimev2.feature.anime_image.domain.model.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WaifuImage(
    val artist: WaifuArtist?,
    val byteSize: Int,
    val dominantColor: String,
    val extension: String,
    val favorites: Int,
    val height: Int,
    val imageId: Int,
    val isNsfw: Boolean,
    val likedAt: String?,
    val previewUrl: String,
    val signature: String,
    val source: String,
    val tags: List<WaifuTag>,
    val uploadedAt: String,
    val url: String,
    val width: Int
): Parcelable

val WaifuImageVariantOne = WaifuImage(
    artist = WaifuArtist(
        artistId = 547,
        deviantArt = null,
        name = "hhl",
        patreon = null,
        pixiv = "https://www.pixiv.net/users/65003497",
        twitter = "https://twitter.com/HHL80Hg"
    ),
    byteSize = 433829,
    extension = ".jpg",
    dominantColor = "#f2dcf0",
    favorites = 2,
    height = 2500,
    imageId = 7637,
    isNsfw = false,
    likedAt = null,
    previewUrl = "https://www.waifu.im/preview/7637/",
    signature = "80d9d3be2918a841",
    source = "https://www.pixiv.net/en/artworks/97618171",
    tags = listOf(
        WaifuTag(
            description = "A female anime/manga character.",
            isNsfw = false,
            name = "waifu",
            tagId = 12
        ),
        WaifuTag(
            description = "Girls with large breasts",
            isNsfw = false,
            name = "oppai",
            tagId = 7,
        )
    ),
    uploadedAt = "2022-04-14T13:51:23.149474+02:00",
    url = "https://cdn.waifu.im/7637.jpg",
    width = 1518,
)

val WaifuImageVariantTwo = WaifuImage(
    artist = WaifuArtist(
        artistId = 938,
        deviantArt = null,
        name = "YUNA",
        patreon = null,
        pixiv = "https://www.pixiv.net/member.php?id=7036095",
        twitter = "https://twitter.com/yuna_pang",
    ),
    byteSize = 8973448,
    extension = ".jpg",
    dominantColor = "#c19f96",
    favorites = 0,
    height = 6354,
    imageId = 8037,
    isNsfw = false,
    likedAt = null,
    previewUrl = "https://www.waifu.im/preview/8037/",
    signature = "ffd81dc1a5e38b41",
    source = "https://www.pixiv.net/artworks/103633783",
    tags = listOf(
        WaifuTag(
            description = "A female anime/manga character.",
            isNsfw = false,
            name = "waifu",
            tagId = 12,
        ),
    ),
    uploadedAt = "2022-12-15T17:33:58.502555+01:00",
    url = "https://cdn.waifu.im/8037.jpg",
    width = 4500,
)

val WaifuImageVariantThree = WaifuImage(
    artist = null,
    byteSize = 569388,
    extension = ".jpeg",
    dominantColor = "#cdc4d1",
    favorites = 1,
    height = 2000,
    imageId = 4138,
    isNsfw = false,
    likedAt = null,
    previewUrl = "https://www.waifu.im/preview/4138/",
    signature = "1d08481426df6517",
    source = "https://reddit.com/ngewn8/",
    tags = listOf(
        WaifuTag(
            description = "A female anime/manga character.",
            isNsfw = false,
            name = "waifu",
            tagId = 12,
        ),
    ),
    uploadedAt = "2021-11-02T12:16:19.048684+01:00",
    url = "https://cdn.waifu.im/4138.jpeg",
    width = 1500,
)

val WaifuImageVariantFour = WaifuImage(
    artist = WaifuArtist(
        artistId = 127,
        deviantArt = null,
        name = "FALL",
        patreon = null,
        pixiv = "https://www.pixiv.net/users/9952456",
        twitter = "https://twitter.com/_F_all__",
    ),
    byteSize = 571942,
    extension = ".jpg",
    dominantColor = "#352b3a",
    favorites = 2,
    height = 2359,
    imageId = 7617,
    isNsfw = false,
    likedAt = null,
    previewUrl = "https://www.waifu.im/preview/7617/",
    signature = "89455e6bc583b33f",
    source = "https://www.pixiv.net/en/artworks/97388579",
    tags = listOf(
        WaifuTag(
            description = "A female anime/manga character.",
            isNsfw = false,
            name = "waifu",
            tagId = 12,
        ),
        WaifuTag(
            description = "Girls with large breasts",
            isNsfw = false,
            name = "oppai",
            tagId = 7,
        )
    ),
    uploadedAt = "2022-04-03T17:37:35.250351+02:00",
    url = "https://cdn.waifu.im/7617.jpg",
    width = 1500,
)
