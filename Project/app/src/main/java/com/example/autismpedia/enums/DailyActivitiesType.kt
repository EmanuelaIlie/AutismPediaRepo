package com.example.autismpedia.enums

enum class DailyActivitiesType(val string: String) {
    NECESSARY_OBJECTS("necessary_objects"),
    STEPS("steps");

    companion object {
        fun from(value: String): DailyActivitiesType =
            DailyActivitiesType.values().find {
                it.string == value
            } ?: DailyActivitiesType.NECESSARY_OBJECTS
    }
}