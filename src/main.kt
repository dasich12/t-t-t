// 1 2 3
// 8 0 4
// 7 6 5

fun strToIntSet(value: String): Set<Int> {
    return value.map { it.toString().toInt() }.toSet()
}

open class State(x_values: String, o_values: String) {
    val x: Set<Int> = strToIntSet(x_values)
    val o: Set<Int> = strToIntSet(o_values)
    private fun check(values: Set<Int>): Boolean {
        if (values.containsAll(setOf(1, 2, 3)) || values.containsAll(setOf(8, 0, 4)) ||
                values.containsAll(setOf(7, 6, 5)) || values.containsAll(setOf(1, 8, 7)) ||
                values.containsAll(setOf(2, 0, 6)) || values.containsAll(setOf(3, 4, 5)) ||
                values.containsAll(setOf(1, 0, 5)) || values.containsAll(setOf(3, 0, 7)))
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

    private fun rotate(x_set: Set<Int>, o_set: Set<Int>): Pair<Set<Int>, Set<Int>> {
        return Pair(x_set.map { ((it + 1) % 8) + 1 }.toSet(), o_set.map { ((it + 1) % 8) + 1 }.toSet())
    }


    private fun mirror(x_set: Set<Int>, o_set: Set<Int>): Pair<Set<Int>, Set<Int>> {
        fun mirror_one(set: Set<Int>): Set<Int> {
            //        2-8
            //        3-7
            //        4-6
            var new: Set<Int> = setOf()
            set.forEach {
                when (it) {
                    2 -> new += 8
                    3 -> new += 7
                    4 -> new += 6
                    6 -> new += 4
                    7 -> new += 3
                    8 -> new += 2
                    else -> new += it
                }
            }
            return new
        }
        return Pair(mirror_one(x_set), mirror_one(o_set))
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class)
            return false
        val second = other as State
        if (this.x.size != second.x.size || (this.o.size != second.o.size))
            return false

        var pair = Pair(second.x, second.o)
        for (i in 1..4) {
            if (this.x.equals(pair.first) && this.o.equals(pair.second))
                return true
            val (x_set, o_set) = rotate(pair.first, pair.second)
            pair = pair.copy(first = x_set, second = o_set)
        }

        pair = mirror(second.x, second.o)
        for (i in 1..4) {
            if (this.x.equals(pair.first) && this.o.equals(pair.second))
                return true
            val (x_set, o_set) = rotate(pair.first, pair.second)
            pair = pair.copy(first = x_set, second = o_set)
        }
        return false
    }

    override final fun hashCode() = super.hashCode()
}

class NextTurn(x_values: String, o_values: String) : State(x_values, o_values) {
    fun init_moves() {
        // определяем все свободные ячейки
    }

    var moves_variants = init_moves()
}

fun main(args: Array<String>) {
    val a = State("1", "2")

    val b = State("1", "2")
    val c = State("3", "4")
    val d = State("5", "6")
    val e = State("7", "8")

    val f = State("1", "8")
    val g = State("7", "6")
    val h = State("5", "4")
    val i = State("3", "2")

//    println(a.check_winner())
//    println(b.check_winner())
//    println(a==b)
//    println(a==c)
//    println(b==c)
    println(a == b)
    println(a == c)
    println(a == d)
    println(a == e)

    println(a == f)
    println(a == g)
    println(a == h)
    println(a == i)
}
