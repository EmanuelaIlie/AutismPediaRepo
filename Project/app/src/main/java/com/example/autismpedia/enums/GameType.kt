package com.example.autismpedia.enums

enum class GameType(val string: String) {
    STORY("STORY"),
    DAILY_ACTIVITIES("DAILY_ACTIVITIES"),
    DIDACTIC("DIDACTIC");

    companion object {
        fun from(value: String): GameType =
            values().find {
                it.string == value
            } ?: STORY
    }
}