package com.badr.guide.model

import java.io.Serializable

data class Model(
    var description: String,
    var img: String, var actionimage: Int
) : Serializable
