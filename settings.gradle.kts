pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Lelenime V2"
include(":app")
include(":core:local:user_pref")
include(":feature:anime_image:data:remote:waifu_im")
include(":feature:anime_image:data:repository")
include(":feature:anime_image:domain:model")
include(":feature:anime_image:domain:usecases")
include(":feature:anime_image:ui:component")
include(":feature:anime_image:ui:screen")
