package converter

enum class Unit (val val1: Double, val short: String, val single: String, val multi: String, val type: Int, val a: String, val aa: String) {
    REF(1.0,"???","???","???",0, "???", "???"),
    METER(1.0, "m", "meter", "meters", 0, "", ""),
    KILOMETER(1000.0, "km", "kilometer", "kilometers", 0, "", "" ),
    MILLIMETER(0.001, "mm", "millimeter", "millimeters", 0, "", ""),
    CENTIMETER(0.01,"cm", "centimeter", "centimeters", 0, "", "" ),
    MILE(1609.35,"mi", "mile", "miles", 0, "", "" ),
    YARD(0.9144, "yd", "yard", "yards", 0, "", "" ),
    FOOT(0.3048, "ft", "foot", "feet", 0, "", "" ),
    INCH(0.0254, "in", "inch", "inches", 0, "", "" ),
    GRAM(1.0, "g", "gram", "grams", 1, "", "" ),
    KILOGRAM(1000.0, "kg", "kilogram", "kilograms", 1, "", "" ),
    MILLIGRAM(0.001, "mg", "milligram", "milligrams", 1, "", "" ),
    POUND(453.592, "lb", "pound", "pounds", 1, "", "" ),
    OUNCE(28.3495, "oz", "ounce", "ounces", 1, "", ""),
    CELSIUS(1.0, "celsius", "degree Celsius", "degrees Celsius", 2, "c", "dc" ),
    FAHRENHEIT(1.0, "fahrenheit", "degree Fahrenheit", "degrees Fahrenheit", 2, "f", "df" ),
    KELVIN(1.0, "k", "kelvin", "kelvins", 2, "k", "" );

}
data class Values (var num: Double = 0.0, var from: String = "", var to: String = "" )
fun convert(ot: String, to: String, num: Double = v.num): Double {
    return when {
        ot == "c" && to == "f" -> num * 1.8 + 32
        ot == "c" && to == "k" -> num + 273.15
        ot == "f" && to == "c" -> (num - 32) * 5/9
        ot == "f" && to == "k" -> (num + 459.67) * 5/9
        ot == "k" && to == "c" -> num - 273.15
        ot == "k" && to == "f" -> num * 1.8 - 459.67
        else -> num
    }
}
val v = Values()

fun main() {
    var input = ""
    while (true) {
        println("Enter what you want to convert (or exit):")
        input = readln().lowercase()
        if (input == "exit") break
        val values = input.split(" ")
        var fromU = Unit.REF
        var toU = Unit.REF

        try { v.num = values[0].toDouble(); if (values.size !in 4..6) values.size / 0 }
        catch (e: Exception) { println("Parse Error"); continue }

        v.from = if (values[1] == "degree" || values[1] == "degrees") "${values[1]} ${values[2].capitalize()}" else values[1]
        v.to = if (values[values.size -2] == "degree" || values[values.size -2] == "degrees") {
            "${values[values.size -2]} ${values.last().capitalize()}" } else values.last()

        for (i in Unit.values()) {
            if (v.from in listOf(i.short, i.single, i.multi, i.a, i.aa)) { fromU = i }
            if (v.to in listOf(i.short, i.single, i.multi, i.a, i.aa)) { toU = i }
        }
        if (v.from in listOf(fromU.short, fromU.single, fromU.multi, fromU.a, fromU.aa) &&
            v.to in listOf(toU.short, toU.single, toU.multi, toU.a, toU.aa) && fromU.type == toU.type) {
            if (fromU.type != 2 && v.num < 0) {
                println("${if (fromU.type == 0) "Length" else "Weight"} shouldn't be negative.")
                continue }
            val convert = if (toU.type == 2) { convert(fromU.a, toU.a) }
                          else { (v.num * fromU.val1) / toU.val1 }
            println("${v.num} ${if (v.num == 1.0) fromU.single else fromU.multi} is " +
                    "$convert ${if (convert == 1.0) toU.single else toU.multi}")
        } else { println("Conversion from ${fromU.multi} to ${toU.multi} is impossible")}
    }
}
