class State(x_values: String, o_values: String) {
    val x: Set<Int> = x_values.map { it.toString().toInt() }.toSet()
    val o: Set<Int> = o_values.map { it.toString().toInt() }.toSet()
    private fun check(values: Set<Int>): Boolean? {
        if (values.containsAll(setOf(1, 2, 3)) || values.containsAll(setOf(4, 5, 6)) ||
                values.containsAll(setOf(7, 8, 9)) || values.containsAll(setOf(1, 4, 7)) ||
                values.containsAll(setOf(2, 5, 8)) || values.containsAll(setOf(3, 6, 9)) ||
                values.containsAll(setOf(1, 5, 9)) || values.containsAll(setOf(3, 5, 7)))
            return true
        return null
    }

    fun check_winner(): Char? {
        val x_result = check(this.x)
        val o_result = check(this.o)
        if (x_result != null)
            return 'x'
        if (o_result != null)
            return 'o'
        return null
    }
}


fun main(args: Array<String>) {
    val a = State("123", "45")
    val b = State("45", "369")
//    println(a.x)
//    println(a.o)
    println(a.check_winner())
    println(b.check_winner())
    println("Hello world")
}
