package com.esgi.mgb.utils


import org.springframework.data.geo.Circle
import org.springframework.data.geo.Point
import java.lang.Math
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin


fun Point.isInRangeOf(rangeCircular: Circle): Boolean {
	val pk = 180f / Math.PI

	val x1 = this.x / pk
	val y1 = this.y / pk
	val x2 = rangeCircular.center.x / pk
	val y2 = rangeCircular.center.y / pk

	val t1 = cos(x1) * cos(y1) * cos(x2) * cos(y2)
	val t2 = cos(x1) * sin(y1) * cos(x2) * sin(y2)
	val t3 = sin(x1) * sin(x2)
	val meterRange = acos(t1 + t2 + t3) * 6366000

	return rangeCircular.radius.value >= meterRange
}