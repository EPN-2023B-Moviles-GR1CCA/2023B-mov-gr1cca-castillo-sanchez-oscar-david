package com.example.gr1accdlrc2023b

class BBaseDatosMemoria {

    companion object {
        val arreglosBEntrenador= arrayListOf<BEntrenador>()
        init {
            arreglosBEntrenador
            .add(
                BEntrenador(1,"Oscar", "a@a.com")
            )
            arreglosBEntrenador
                .add(
                    BEntrenador(2,"Jeimmy", "b@b.bcom")
                )
            arreglosBEntrenador
                .add(
                    BEntrenador(3,"Juan", "c@c.com")
                )

        }
    }
}