package com.timlam.domain.models

data class Spot(val position: Position, val mark: String)

fun List<Spot>.findMarkOfPosition(position: Position) =
    this.first { it.position == position }.mark
