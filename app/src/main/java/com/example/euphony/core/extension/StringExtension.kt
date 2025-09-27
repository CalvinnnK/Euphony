package com.example.euphony.core.extension


fun String?.isNotEmptyOrBlank(): Boolean = if (this == null) false else !(isEmpty() || isBlank())
