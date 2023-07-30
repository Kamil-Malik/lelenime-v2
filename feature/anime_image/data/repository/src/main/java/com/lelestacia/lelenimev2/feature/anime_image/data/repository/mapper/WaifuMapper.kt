package com.lelestacia.lelenimev2.feature.anime_image.data.repository.mapper

import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response.WaifuArtistDto
import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response.WaifuImageDto
import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response.WaifuTagDto
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuArtist
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuTag

fun WaifuArtistDto.asWaifuArtist(): WaifuArtist {
    return WaifuArtist(
        artistId = artistId,
        deviantArt = deviantArt,
        name = name,
        patreon = patreon,
        pixiv = pixiv,
        twitter = twitter
    )
}

fun WaifuTagDto.asWaifuTag(): WaifuTag {
    return WaifuTag(
        description = description,
        isNsfw = isNsfw,
        name = name,
        tagId = tagId
    )
}

fun WaifuImageDto.asWaifuImage(): WaifuImage {
    return WaifuImage(
        artist = artist?.asWaifuArtist(),
        byteSize = byteSize,
        dominantColor = dominantColor,
        extension = extension,
        favorites = favorites,
        height = height,
        imageId = imageId,
        isNsfw = isNsfw,
        likedAt = likedAt,
        previewUrl = previewUrl,
        signature = signature,
        source = source,
        tags = tags.map(WaifuTagDto::asWaifuTag),
        uploadedAt = uploadedAt,
        url = url,
        width = width
    )
}