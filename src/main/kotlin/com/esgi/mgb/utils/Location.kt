package com.esgi.mgb.utils


import org.springframework.data.geo.Circle
import org.springframework.data.geo.Point
import java.lang.Math


fun Point.isInRangeOf(rangeCircular: Circle): Boolean {
	val pk = 180f / Math.PI

	val x1 = this.x / pk
	val y1 = this.y / pk
	val x2 = rangeCircular.center.x / pk
	val y2 = rangeCircular.center.y / pk

	val t1 = Math.cos(x1) * Math.cos(y1) * Math.cos(x2) * Math.cos(y2)
	val t2 = Math.cos(x1) * Math.sin(y1) * Math.cos(x2) * Math.sin(y2)
	val t3 = Math.sin(x1) * Math.sin(x2)
	val meterRange = Math.acos(t1 + t2 + t3) * 6366000

	return rangeCircular.radius.value >= meterRange
}