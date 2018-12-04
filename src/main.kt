// 1 2 3
// 8 0 4
// 7 6 5

fun strToIntSet(value: String): Set<Int> {
    return value.map { it.toString().toInt() }.toSet()
}

open class State(x_values: String, o_values: String) {
    var x: Set<Int> = strToIntSet(x_values)
    var o: Set<Int> = strToIntSet(o_values)

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

    fun get_center(): Char? {
        if (x.contains(0))
            return 'x'
        if (o.contains(0))
            return 'o'
        return null
    }


    private fun rotate(x_set: Set<Int>, o_set: Set<Int>): Pair<Set<Int>, Set<Int>> {
        fun rotate_point(point: Int): Int = if (point > 0) ((point + 1) % 8) + 1 else 0
        return Pair(x_set.map { rotate_point(it) }.toSet(), o_set.map { rotate_point(it) }.toSet())
    }


    private fun mirror(x_set: Set<Int>, o_set: Set<Int>): Pair<Set<Int>, Set<Int>> {
        fun mirror_one(set: Set<Int>): Set<Int> {
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
        if (this.get_center() != second.get_center())
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

    override fun toString() = "(x=$x, o=$o ${check_winner()})"
}

class NextTurn(x_values: String = "", o_values: String = "") : State(x_values, o_values) {
    fun init_moves() {
        // определяем все свободные ячейки
    }

    fun get_empty_cells(): Set<Int> {
        return setOf(0, 1, 2, 3, 4, 5, 6, 7, 8).subtract(this.x).subtract(this.o)
    }

    var moves_variants = init_moves()

    fun copy(add_val: Int?): NextTurn {

        val next = NextTurn(x_values = this.x.joinToString(separator = ""),
                o_values = this.o.joinToString(separator = ""))
        if (add_val != null)
            if (next.o.size < next.x.size)
                next.o += add_val
            else
                next.x += add_val
        return next
    }

}


class AllTurns {
    fun generate_all_turns(): MutableList<MutableList<NextTurn>> {
        val result = mutableListOf(mutableListOf(NextTurn()))

        for (i in 1..9) { // Номер хода
            val prev = result.last()
            val next: MutableList<NextTurn> = mutableListOf<NextTurn>()
            for (state in prev) {
                if (state.check_winner() == null) {
                    state.get_empty_cells().forEach {
                        // определяем чей ход
                        val new_state = state.copy(add_val = it)

                        var flag = true
                        for (item in next) {
                            if (item == new_state)
                                flag = false
                        }
                        if (flag)
                            next.add(new_state)

                    }
                }
            }
            result.add(next)
        }
        return result
    }

    var turns = generate_all_turns()
}

fun main(args: Array<String>) {
    val turns = AllTurns()
    println(turns.turns)
}
