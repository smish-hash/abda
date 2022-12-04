package com.smish.abda.data.model.movie

enum class Type(val value: String){
    HOME("home"),
    MOVIE("movie"),
    SERIES("series"),
    EPISODE("episode"),
}

fun getAllTypes(): List<Type>{
    return listOf(Type.HOME, Type.MOVIE, Type.SERIES, Type.EPISODE)
}

fun getType(value: String): Type? {
    val map = Type.values().associateBy(Type::value)
    return map[value]
}