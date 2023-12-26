import java.util.*

fun main() {
    println("Hola mundo")

    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Oscar"

    // Mutables (Re asignar)
    var mutable: String = "David"
    mutable = "Oscar"

    // Duck Typing
    var ejemploVariable = " Oscar Castillo "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()

    // Variable primitiva
    val nombreProfesor: String = "Oscar Castillo"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    // Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        "C" -> println("Casado")
        "S" -> println("Soltero")
        else -> println("No sabemos")
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    // Llamadas a función con diferentes parámetros
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo = 10.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00)
    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    // Creación e invocación de instancias de Suma
    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // ARREGLOS
    // Tipos de Arreglos
    // Arreglo Estático
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println(arregloEstatico)

    // Arreglo Dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach { valorActual: Int ->
        println("Valor actual: $valorActual")
    }
    arregloDinamico.forEach { println(it) }

    arregloEstatico.forEachIndexed { indice, valorActual ->
        println("Valor $valorActual Indice: $indice")
    }
    println(respuestaForEach)

    // MAP -> Muta el arreglo (Cambia el arreglo)
    val respuestaMap: List<Double> = arregloDinamico.map { valorActual ->
        valorActual.toDouble() + 100.00
    }
    println(respuestaMap)

    // FILTER -> FILTRAR EL ARREGLO
    val respuestaFilter: List<Int> = arregloDinamico.filter { valorActual ->
        valorActual > 5
    }
    println(respuestaFilter)

    // OR AND
    val respuestaAny: Boolean = arregloDinamico.any { valorActual ->
        valorActual > 5
    }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico.all { valorActual ->
        valorActual > 5
    }
    println(respuestaAll)

    // REDUCE -> Valor acumulado
    val respuestaReduce: Int = arregloDinamico.reduce { acumulado, valorActual ->
        acumulado + valorActual
    }
    println(respuestaReduce)
}

// Clases y funciones
abstract class Numeros(protected val numeroUno: Int, protected val numeroDos: Int) {
    init {
        println("Inicializando")
    }
}

class Suma(uno: Int, dos: Int) : Numeros(uno, dos) {
    // Constructores adicionales
    constructor(uno: Int?, dos: Int) : this(uno ?: 0, dos)
    constructor(uno: Int, dos: Int?) : this(uno, dos ?: 0)
    constructor(uno: Int?, dos: Int?) : this(uno ?: 0, dos ?: 0)

    fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object {
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int = num * num
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
        }
    }
}

fun calcularSueldo(sueldo: Double, tasa: Double = 12.00, bonoEspecial: Double? = null): Double {
    return if (bonoEspecial == null) {
        sueldo * (100 / tasa)
    } else {
        sueldo * (100 / tasa) + bonoEspecial
    }
}
