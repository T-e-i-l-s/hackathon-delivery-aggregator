pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ProdDraft"
include(":app")
include(":sources")
include(":sources:core")
include(":sources:common")
include(":sources:feature")
include(":sources:core:utils")
include(":sources:core:uikit")
include(":sources:core:preferences")
include(":sources:feature:auth")
include(":sources:feature:navigation")
include(":sources:feature:main")
include(":sources:core:serialization")
include(":sources:common:error_ui")
include(":sources:common:network")
include(":sources:common:database")
include(":sources:common:auth")
