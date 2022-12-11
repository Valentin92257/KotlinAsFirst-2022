@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import lesson4.task1.mean

// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> = grades.toList().groupBy(
    keySelector = { it.second },
    valueTransform = { it.first }
).toMap()


/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean = a.all {
    b[it.key] == it.value
}

/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((b1, c) in b) {
        if (a[b1] == c) a.remove(b1)
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяющихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.intersect(b.toSet()).toList()


/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val mapC = (mapB + mapA).toMutableMap()
    for ((key1, value1) in mapA) {
        for ((key2, value2) in mapB) {
            if (key1 == key2 && value1 != value2) mapC[key1] = "$value1, $value2"
        }
    }
    return mapC
}

/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> = stockPrices.groupBy(
    keySelector = { it.first },
    valueTransform = { it.second }
).toMap().mapValues { mean(it.value) }

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    val a = stuff.filter { it.value.first == kind }.map { it.value.second to it.key }.toMap()
    return if (a.isNotEmpty()) a[a.minOf { it.key }] else null
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
    chars.map { it.toString().lowercase() }.containsAll(word.lowercase().split("").toSet() - "")

/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> = TODO()

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean = TODO()

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> = TODO()

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val map = mutableMapOf<Int, Int>()
    for ((index, i) in list.withIndex()) {
        if (map.containsKey(number - i)) return Pair(map[number - i]!!, index)
        else map[i] = index
    }
    return Pair(-1, -1)
}

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val trl = treasures.toList()
    val t: Array<Array<String>> = Array(treasures.size + 1) { Array(capacity + 1) { "" } }
    val a: Array<IntArray> = Array(treasures.size + 1) { IntArray(capacity + 1) { 0 } }
    for (i in 0..treasures.size) {
        for (j in 0..capacity) {
            if (i == 0 || j == 0) {
                a[i][j] = 0
            } else {
                if (trl[i - 1].second.first > j) {
                    a[i][j] = a[i - 1][j]
                    t[i][j] = t[i - 1][j]
                } else {
                    val p = a[i - 1][j]
                    val tp = t[i - 1][j]
                    a[i][j] = maxOf(p, trl[i - 1].second.second + a[i - 1][j - trl[i - 1].second.first])
                    if (p < trl[i - 1].second.second + a[i - 1][j - trl[i - 1].second.first]) {
                        t[i][j] = trl[i - 1].first + " " + t[i - 1][j - trl[i - 1].second.first]
                    } else {
                        t[i][j] = tp
                    }
                }
            }
        }
    }
    return t[treasures.size][capacity].split(" ").toSet() - ""
}

/**
 * Во входном списке `movers` перечислены компании, которые занимаются
 * перевозкой домашних животных. Каждая компания описана в следующем
 * формате:
 *
 * имя компании: вид животного - стоимость,
 *
 * Пример входных данных:
 * SuperCats: кот - 100000,
 * FastAndCheap: кот - 25000, собака - 30000, шиншилла - 5000,
 * Lux: кот - 1000000, собака - 1000000, крыса - 1000000,
 * корова - 1000000, бегемот - 1000000,
 *
 * Также на вход вам подается еще два параметра: список домашних
 * животных, которых необходимо перевезти, `pets` и максимальная
 * сумма, которую хозяева готовы потратить на перевозку, `limit`.
 *
 * Вам необходимо имена всех компаний, которые могут перевезти
 * указанных животных в пределах заданной суммы.
 *
 * Например, для указанного списка компаний и параметров
 * - `pets=["кот", "собака"] money = 20000000` вернуть ["Lux", "FastAndCheap"]
 * - `pet=["кот"] money = 25000` вернуть ["FastAndCheap"]
 * - `pet=["бегемот"] money = 500000` вернуть []
 *
 * При нарушении формата входных данных следует выбросить
 * IllegalFormatException.
 *
 * Имя функции и тип результата функции предложить самостоятельно;
 * в задании указан тип Collection<Any>, то есть коллекция объектов
 * произвольного типа, можно (и нужно) изменить как вид коллекции,
 * так и тип её элементов.
 *
 * Кроме функции, следует написать тесты,
 * подтверждающие её работоспособность.
 */
fun petsDelivery(movers: List<String>, pets: List<String>, limit: Int): List<String> {
    var sps: Pair<String, Map<String, Int>>
    var flag = 0
    var price = limit
    val result = mutableListOf<String>()
    for (i in movers) {
        if (!("$i,").matches(
                Regex(
                    """[a-z]+:( [a-z]+ - \d+,)+""",
                    RegexOption.IGNORE_CASE
                )
            )
        ) throw IllegalArgumentException()//IllegalFormatException()
        sps = Pair(
            i.split(": ")[0],
            i.split(": ")[1].split(", ").associate { it.split(" - ")[0] to it.split(" - ")[1].toInt() }
        )
        for (b in pets) {
            if (!sps.second.containsKey(b)) {
                flag = 1
                break
            } else {
                price -= sps.second[b]!!
            }
        }
        if (flag == 0 && price >= 0) {
            result.add(sps.first)
        }
    }
    return result
}

/*
* Имеется старый добрый телефон с клавиатурой вида:
*                  ABC(2)   DEF(3)
*       GHI(4)     JKL(5)   MNO(6)
*       PQRS(7)    TUV(8)   WXYZ(9)
*
* Дан список имен в телефонном справочнике.
* Для быстрого доступа к необходимому контакту в строке поиска
* телефонного справочника можно вводить цифры, соответствующие
* буквам искомого имени. Например, для поиска контакта с именем
* Maxim необходимо ввести последовательность “62946”. Для списка
* имен и цифровой последовательности необходимо вывести список
* контактов, имена которых можно получить, введя в телефонный
* справочник данную последовательность.
*
* Имя функции и тип результата функции предложить самостоятельно;
* в задании указан тип Collection<Any>,
* то есть коллекция объектов произвольного типа,
* можно (и нужно) изменить как вид коллекции,
* так и тип её элементов.
*
* Кроме функции, следует написать тесты,
* подтверждающие её работоспособность.
*/
fun namesInPhone(names: List<String>, digits: String): List<String> {
    val phone = mutableMapOf<String, String>(
        "0" to "",
        "1" to "",
        "2" to " A B C",
        "3" to " D E F",
        "4" to "G H I",
        "5" to "J K L",
        "6" to "M N O",
        "7" to "P Q R S",
        "8" to "T U V",
        "9" to "W X Y Z"
    )
    val result = mutableListOf<String>()
    var flag: Int
    var words = ""
    for (i in digits.split("").toMutableSet()) {
        words += phone[i] + " "
    }
    words.trim().toSet()
    for (i in names) {
        flag = 0
        for (b in i.uppercase().split("").toMutableSet() - "") {
            if (!words.contains(b)) flag = 1
            break
        }
        if (flag == 0) result.add(i)
    }
    return result
}


/*
* На вход функции подается список строк в формате
* “Иванов Петр: улица Ленина, 41, кв. 2”
* Каждая строка начинается с фамилии и имени человека (разделенных
* одним пробелом), далее через запятую пробел(ы) следует адрес:
* название улицы (может состоять из нескольких слов через
* один пробел),
* номер дома (целое число) и номер квартиры
* (с префиксом “кв.”; целое число).
*
* На вход также подается имя человека.
*
* Вернуть список людей, которые являются соседями
* указанного человека
* (соседями считаются люди, которые живут в одном доме).
*
* Имя функции и тип результата функции предложить самостоятельно;
* в задании указан тип Collection<Any>,
* то есть коллекция объектов произвольного типа,
* можно (и нужно) изменить как вид коллекции,
* так и тип её элементов.
*
* При нарушении формата входной строки,
* бросить IllegalArgumentException
*
* Кроме функции, следует написать тесты,
* подтверждающие её работоспособность.
*/
fun neighbours(addresses: List<String>, person: String): List<String> {
    val a = addresses.map {
        it.split(": ")[0] to Pair(
            it.split(": ")[1].split(", кв. ")[0],
            it.split(": ")[1].split(", кв. ")[1]
        )
    }
    val result = mutableListOf<String>()
    for (i in a) {
        if (person.split(": ")[1].split(", кв. ")[0] == i.second.first) {
            if (i.first != person.split(": ")[0] || i.second.second != person.split(": ")[1].split(", кв. ")[1]) {
                result.add(i.first)
            }
        }
    }
    return result
}

/**
 * Во входной строке `taxes` содержится информация о прогрессивном
 * налогообложении для физических лиц в зависимости от их годового
 * дохода в следующем формате:
 *
 * 20000$ = 0%, 40000$ = 5%, 60000$ = 10%, other = 25%
 *
 * Данный список значит, что доход, полученный человеком
 * облагается налогом по следующей формуле:
 * - доход 0..20000$ облагается налогом 0%
 * - доход 20001..40000$ облагается налогом 5%
 * - доход 40001..60000$ облагается налогом 10%
 * - доход 60001 и выше облагается налогом 25%
 *
 * Например, если человек заработал за год 100000$, то
 * его налог будет составляться следующим образом:
 * 20000 * 0% (первые 20000 не облагаются налогом)
 * + 20000 * 5% (следующие 20000 облагаются налогом 5%)
 * + 20000 * 10% (следующие 20000 облагаются налогом 10%)
 * + 40000 * 25% (весь остальной доход облагается налогом 25%)
 * = 13000
 *
 * Все границы дохода во входной строке расположены в
 * порядке возрастания.
 *
 * Также на вход подается число `money` - заработанная человеком
 * за год сумма. Вам необходимо посчитать какой процент от своего
 * дохода человеку придется заплатить в качестве налога. Для
 * приведенного примера ответ составит 13%: сумма налога 13000
 * составляет 13% от общего дохода 100000.
 * При нарушении формата входных данных следует выбросить
 * IllegalArgumentException.
 *
 * Имя функции и тип результата функции предложить самостоятельно;
 * в задании указан тип Any, то есть объект произвольного типа,
 * можно (и нужно) изменить его на более подходящий тип.
 *
 * Кроме функции, следует написать тесты,
 * подтверждающие её работоспособность.
 */
fun tax(taxes: String, money: Int): Double {
    if (!taxes.matches(Regex("""(\d+\$ = \d+%, )+other = \d+%"""))) throw IllegalArgumentException()
    val a = taxes.split(", ").map { Pair(it.split(" = ")[0].replace("$", ""), it.split(" = ")[1].replace("%", "")) }
    var b = money
    var result = 0
    var sum = 0
    for ((first, second) in a) {
        if (first == "other" && b > 0) {
            result += b * second.toInt() / 100
            break
        }
        if (b - first.toInt() + sum >= 0) {
            b -= first.toInt()
            b += sum
            result += (first.toInt() - sum) * second.toInt() / 100
            sum = first.toInt()
        } else {
            result += b * second.toInt() / 100
            break
        }

    }

    return result / (money.toDouble() / 100)
}
