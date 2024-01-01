import java.io.File
import java.time.LocalDate

data class Atleta(
    var id: Int,
    var nombre: String,
    var edad: Int,
    var deporte: String,
    var esProfesional: Boolean,
    var altura: Double
) {
    companion object {
        private const val ARCHIVO = "atletas.txt"

        fun leerAtletas(): MutableList<Atleta> {
            val archivo = File(ARCHIVO)
            if (!archivo.exists()) return mutableListOf()

            return archivo.readLines().map { line ->
                val parts = line.split(",")
                Atleta(
                    id = parts[0].toInt(),
                    nombre = parts[1],
                    edad = parts[2].toInt(),
                    deporte = parts[3],
                    esProfesional = parts[4].toBoolean(),
                    altura = parts[5].toDouble()
                )
            }.toMutableList()
        }

        fun escribirAtletas(atletas: List<Atleta>) {
            val archivo = File(ARCHIVO)
            archivo.writeText(atletas.joinToString("\n") { atleta ->
                "${atleta.id},"+
                "${atleta.nombre}," +
                        "${atleta.edad}," +
                        "${atleta.deporte}," +
                        "${atleta.esProfesional}," +
                        "${atleta.altura}"
            })
        }

        // CRUD Operations
        fun crearAtleta(atleta: Atleta) {
            val atletas = leerAtletas()
            atletas.add(atleta)
            escribirAtletas(atletas)
        }

        fun mostrarAtletas() {
            leerAtletas().forEach { println(it) }
        }



        fun actualizarAtleta(id: Int, propiedad: String, nuevoValor: String) {
            val atletas = leerAtletas()
            val atletaActualizado = atletas.find { it.id == id }
            var id= atletaActualizado?.id?.toInt()
            atletaActualizado?.let {
                when (propiedad) {
                    "id" -> it.id = nuevoValor.toInt()
                    "nombre" -> it.nombre = nuevoValor
                    "edad" -> it.edad = nuevoValor.toInt()
                    "deporte" -> it.deporte= nuevoValor
                    "esProfesional" -> it.esProfesional = nuevoValor.toBoolean()
                    "altura" -> it.altura = nuevoValor.toDouble()

                }
                escribirAtletas(atletas)
                if (id != null) {
                    actualizarAtletaDeporte(atletaActualizado,id)
                }
            }
        }

        fun actualizarAtletaDeporte(atletaActualizado: Atleta, id: Int) {
            var deportes = Deporte.leerDeportes()

            // Eliminar el atleta de su deporte actual si es diferente
            deportes.forEach { deporte ->
                val indiceAtleta = deporte.atletas.indexOfFirst { atleta ->
                    atleta.id == id
                }

                if (indiceAtleta != -1) {
                    if (deporte.atletas[indiceAtleta].deporte != atletaActualizado.deporte) {
                        deporte.atletas.removeAt(indiceAtleta)
                    }
                }
            }

            // Agregar el atleta al nuevo deporte
            val deporteDestino = deportes.find { it.nombre == atletaActualizado.deporte }
            deporteDestino?.atletas?.add(atletaActualizado)

            Deporte.escribirDeportes(deportes)
        }


        fun eliminarAtleta(id: Int) {
            var deportes = Deporte.leerDeportes()
            val atletas = leerAtletas()
            val atletaEliminado = atletas.removeIf { it.id == id }

            if (atletaEliminado) {
                deportes.forEach { deporte ->
                    deporte.atletas.removeIf { atleta -> atleta.id == id }
                }
                escribirAtletas(atletas)
                Deporte.escribirDeportes(deportes)
            }
        }

        fun fromString(data: String): Atleta? {
            // Primero, elimina el prefijo "Atleta(" y el sufijo ")"
            val cleanData = data.removePrefix("Atleta(").removeSuffix(")")

            // Divide la cadena en partes basándote en las comas y los espacios
            val parts = cleanData.split(", ").map { it.split("=")[1] }

            // Asegúrate de que tienes todas las partes necesarias
            if (parts.size < 5) {
                println("Datos de entrada no válidos. Se esperan 5 partes, pero se encontraron ${parts.size}.")
                return null
            }

            return try {
                Atleta(
                    id = parts[0].toInt(),
                    nombre = parts[1],
                    edad = parts[2].toInt(),
                    deporte = parts[3],
                    esProfesional = parts[4].toBoolean(),
                    altura = parts[5].toDouble()
                )
            } catch (e: Exception) {
                println("Error al procesar los datos de entrada: ${e.message}")
                null
            }
        }

        fun buscarPorId(id: Int): Atleta? {
            var atletas=leerAtletas()
            atletas.forEach(){
                    atleta->
                if (atleta.id==id){
                    return atleta
                }
            }
            return null
        }


    }
    fun toStringDeporte(): String {
        return "Nombre: $nombre, Edad: $edad"
    }


}
