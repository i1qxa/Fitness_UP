package org.fitnessup.wjtt.data.remote

import kotlinx.serialization.Serializable
import org.fitnessup.wjtt.data.remote.Hit
import org.fitnessup.wjtt.data.remote.Links

@Serializable
data class RecipeResponseBody(
    val from:Int?,
    val to:Int?,
    val count:Int?,
    val _links: Links?,
    val hits:List<Hit>?,
)
