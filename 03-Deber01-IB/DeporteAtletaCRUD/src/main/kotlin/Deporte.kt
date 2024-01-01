import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.io.File

class Deporte(
    var nombre: String,
    var nivelCompetitivo: Boolean,
    var fechaFundacion: LocalDate,
    var popularidadGlobal: Double,
    var atletas: MutableList<Atleta> = mutableListOf()
) {
    companion object {
        private const val ARCHIVO = "deportes.txt"
        private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        fun leerDeportes(): MutableList<Deporte> {
            val archivo = File(ARCHIVO)
            if (!archivo.exists()) return mutableListOf()

            return archivo.readLines().mapNotNull { line ->
                val parts = line.split("|")
                if (parts.size < 2) return@mapNotNull null

                val deporteInfo = parts[0].split(",")
                if (deporteInfo.size < 4) return@mapNotNull null

                val atletas = try {
                    if (parts.size > 1) parts[1].split(";").mapNotNull { Atleta.fromString(it) }.toMutableList()
                    else mutableListOf()
                } catch (e: Exception) {
                    mutableListOf()
                }

                try {
                    Deporte(
                        nombre = deporteInfo[0],
                        nivelCompetitivo = deporteInfo[1].toBoolean(),
                        fechaFundacion = LocalDate.parse(deporteInfo[2], formatter),
                        popularidadGlobal = deporteInfo[3].toDouble(),
                        atletas = atletas
                    )
                } catch (e: Exception) {
                    null
                }
            }.toMutableList()
        }


        fun escribirDeportes(deportes: List<Deporte>) {
            val archivo = File(ARCHIVO)
            archivo.writeText(deportes.joinToString("\n") { deporte ->
                val deporteData = "${deporte.nombre}," +
                        "${deporte.nivelCompetitivo}," +
                        "${deporte.fechaFundacion.format(formatter)}," +
                        "${deporte.popularidadGlobal}"
                val atletasData = deporte.atletas.joinToString(";") { it.toString() }
                "$deporteData|$atletasData"
            })
        }


        // CRUD Operations
        fun crearDeporte(deporte: Deporte) {
            val deportes = leerDeportes()
            deportes.add(deporte)
            escribirDeportes(deportes)
        }

        fun mostrarDeportes() {
            leerDeportes().forEachIndexed() {index, deporte ->
                val indice=index+1
                println("$indice. "+deporte.nombre)
                deporte.atletas.forEach { atleta ->
                    println("   -"+atleta.toStringDeporte())
                }
            }
        }

        fun ingresarDeporteAtleta(nombre: String, deporteActualizado: Deporte) {
            val deportes = leerDeportes()
            val index = deportes.indexOfFirst { it.nombre == nombre }
            if (index != -1) {
                deportes[index] = deporteActualizado
                escribirDeportes(deportes)
            }
        }

        fun actualizarDeporte(nombreDeporte: String, propiedad: String, nuevoValor: String) {
            val deportes = leerDeportes()
            val atletas = Atleta.leerAtletas()
            val deporte = deportes.find { it.nombre == nombreDeporte }

            deporte?.let { deporteActualizado ->
                when (propiedad) {
                    "nombre" -> {
                        // Actualizar el nombre en los atletas que practican este deporte
                        atletas.filter { it.deporte == nombreDeporte }
                            .forEach { it.deporte = nuevoValor }

                        deporteActualizado.nombre = nuevoValor
                    }
                    "nivelCompetitivo" -> deporteActualizado.nivelCompetitivo = nuevoValor.toBoolean()
                    "fechaFundacion" -> deporteActualizado.fechaFundacion = LocalDate.parse(nuevoValor)
                    "popularidadGlobal" -> deporteActualizado.popularidadGlobal = nuevoValor.toDouble()
                    // Añade más casos si hay más propiedades
                }

                escribirDeportes(deportes)
                Atleta.escribirAtletas(atletas)
            }
        }


        fun eliminarDeporte(nombre: String) {
            val deportes = leerDeportes()
            val atletas = Atleta.leerAtletas()

            // Eliminar el deporte
            if (deportes.removeIf { it.nombre == nombre }) {
                escribirDeportes(deportes)

                // Actualizar los atletas que pertenecían a ese deporte
                atletas.filter { it.deporte == nombre }
                    .forEach { it.deporte = "No Asignado" }

                Atleta.escribirAtletas(atletas)
            }
        }

    }

}