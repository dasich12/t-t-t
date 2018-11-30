fun strToIntSet(value: String): Set<Int> {
    return value.map { it.toString().toInt() }.toSet()
}

open class State(x_values: String, o_values: String) {
    val x = strToIntSet(x_values)
    val o = strToIntSet(o_values)
    private fun check(values: Set<Int>): Boolean {
        if (values.containsAll(setOf(1, 2, 3)) || values.containsAll(setOf(4, 5, 6)) ||
                values.containsAll(setOf(7, 8, 9)) || values.containsAll(setOf(1, 4, 7)) ||
                values.containsAll(setOf(2, 5, 8)) || values.containsAll(setOf(3, 6, 9)) ||
                values.containsAll(setOf(1, 5, 9)) || values.containsAll(setOf(3, 5, 7)))
            return true
        return false
    }

    fun check_winner(): Char? {
        if (check(this.x))
            return 'x'
        if (check(this.o))
            return 'o'
        return null
    }
}

class NextTurn(x_values: String, o_values: String): State(x_values, o_values){
    fun init_moves(){
        // определяем все свободные ячейки
    }
    var moves_variants = init_moves()
}

fun main(args: Array<String>) {
    val a = State("123", "45")
    val b = State("45", "369")

    println(a.check_winner())
    println(b.check_winner())
}
