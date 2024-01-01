import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(){
    menuPrincipal()
}

fun menuPrincipal() {
        var continuar= true

        try {
            while (continuar) {
                println("\n--- Menú CRUD Deporte y Atleta ---")
                println("1. Crear Deporte")
                println("2. Mostrar Deportes")
                println("3. Actualizar Deporte")
                println("4. Eliminar Deporte")
                println("5. Crear Atleta")
                println("6. Mostrar Atletas")
                println("7. Actualizar Atleta")
                println("8. Eliminar Atleta")
                println("9. Salir")
                print("Elige una opción: ")

                when (readLine()!!.toInt()) {
                    1 -> crearDeporte()
                    2 -> Deporte.mostrarDeportes()
                    3 -> actualizarDeporte()
                    4 -> eliminarDeporte()
                    5 -> crearAtleta()
                    6 -> Atleta.mostrarAtletas()
                    7 -> actualizarAtleta()
                    8 -> eliminarAtleta()
                    9 -> break
                    else -> println("Opción no válida. Por favor, intenta de nuevo.")
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}. Intente de nuevo.")
            menuPrincipal()
        }


}

fun crearDeporte() {
    println("Ingrese el nombre del deporte:")
    val nombre = readLine()!!
    println("¿Es un deporte de nivel competitivo? (true/false):")
    val nivelCompetitivo = readLine()!!.toBoolean()
    println("Ingrese la fecha de fundación (dd-MM-yyyy):")
    val fechaFundacion = LocalDate.parse(readLine()!!, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    println("Ingrese la popularidad global (número decimal):")
    val popularidadGlobal = readLine()!!.toDouble()


    val nuevoDeporte = Deporte(nombre, nivelCompetitivo, fechaFundacion, popularidadGlobal)
    Deporte.crearDeporte(nuevoDeporte)
    println("Deporte agregado con éxito.")
}

fun actualizarDeporte() {
    val deportes = Deporte.leerDeportes()
    println("Seleccione un deporte para editar:")
    deportes.forEachIndexed { index, deporte ->
        println("${index + 1}. ${deporte.nombre}")
    }
    val indiceDeporte = readLine()!!.toInt() - 1
    val deporteSeleccionado = deportes[indiceDeporte]

    println("Seleccione la propiedad a actualizar:")
    println("1. Nombre")
    println("2. Nivel Competitivo")
    println("3. Fecha de Fundación")
    println("4. Popularidad Global")
    val opcionPropiedad = readLine()!!.toInt()

    println("Ingrese el nuevo valor para la propiedad:")
    val nuevoValor = readLine()!!

    val propiedad = when (opcionPropiedad) {
        1 -> "nombre"
        2 -> "nivelCompetitivo"
        3 -> "fechaFundacion"
        4 -> "popularidadGlobal"
        else -> {
            println("Opción no válida")
            return
        }
    }

    Deporte.actualizarDeporte(deporteSeleccionado.nombre, propiedad, nuevoValor)
    println("Deporte actualizado con éxito.")
}


fun eliminarDeporte() {
    println("Ingrese el nombre del deporte a eliminar:")
    val nombre = readLine()!!
    Deporte.eliminarDeporte(nombre)
    println("Deporte eliminado con éxito.")
}

fun crearAtleta() {
    val deportes = Deporte.leerDeportes()
    if (deportes.isEmpty()) {
        println("No hay deportes disponibles. Por favor, crea un deporte primero.")
        return
    }

    println("Seleccione un deporte para agregar un atleta:")
    deportes.forEachIndexed { index, deporte ->
        println("${index + 1}. ${deporte.nombre}")
    }

    val indiceDeporte = readLine()!!.toInt() - 1
    if (indiceDeporte !in deportes.indices) {
        println("Selección inválida.")
        return
    }

    val deporteSeleccionado = deportes[indiceDeporte]
    println("Ingrese el nro de cedula del atleta:")
    val id = readLine()!!.toInt()
    println("Ingrese el nombre del atleta:")
    val nombre = readLine()!!
    println("Ingrese la edad del atleta:")
    val edad = readLine()!!.toInt()
    println("¿El atleta es profesional? (true/false):")
    val esProfesional = readLine()!!.toBoolean()
    println("Ingrese la altura del atleta (en metros):")
    val altura = readLine()!!.toDouble()

    val nuevoAtleta = Atleta(id,nombre, edad, deporteSeleccionado.nombre, esProfesional, altura)
    deporteSeleccionado.atletas.add(nuevoAtleta)
    Atleta.crearAtleta(nuevoAtleta)
    Deporte.ingresarDeporteAtleta(deporteSeleccionado.nombre, deporteSeleccionado)

    println("Atleta agregado con éxito al deporte ${deporteSeleccionado.nombre}.")
}


fun actualizarAtleta() {
    val atletas = Atleta.leerAtletas()
    println("Ingrese el id del atleta:")
    val id = readLine()!!.toInt()


    if(Atleta.buscarPorId(id)==null){
        println("No existe Competidor con ese Id")
    }

    println("Seleccione la propiedad a actualizar:")
    println("1. id")
    println("2. nombre")
    println("3. edad")
    println("4. deporte")
    println("5. esProfesional")
    val opcionPropiedad = readLine()!!.toInt()
    var nuevoValor: String
    if(opcionPropiedad!=4){
        println("Valor Antiguo:")

        println("Ingrese el nuevo valor para la propiedad:")
        nuevoValor = readLine()!!

    }else{
        val deportes = Deporte.leerDeportes()
        deportes.forEachIndexed { index, deporte ->
            println("${index + 1}. ${deporte.nombre}")
        }
        var indiceDeporte = readLine()!!.toInt() - 1
        nuevoValor = deportes[indiceDeporte].nombre

    }



    var propiedad=when (opcionPropiedad) {
        1 -> "id"
        2 -> "nombre"
        3 -> "edad"
        4 -> "deporte"
        5 -> "esProfessional"
        else -> {
            println("Opción no válida")
            return
        }
    }
    Atleta.actualizarAtleta(id, propiedad , nuevoValor)
    println("Atleta actualizado con éxito.")
}

fun eliminarAtleta() {
    println("Ingrese el id del atleta a eliminar:")
    val id = readLine()!!.toInt()
    Atleta.eliminarAtleta(id)
    println("Atleta eliminado con éxito.")
}
