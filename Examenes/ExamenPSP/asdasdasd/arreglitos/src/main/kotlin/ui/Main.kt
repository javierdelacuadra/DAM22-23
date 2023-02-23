package ui

import dao.DaoAlumnos
import jakarta.enterprise.inject.se.SeContainerInitializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class Main {
    companion object {
      @JvmStatic
      fun main(args: Array<String>) {
         val container = SeContainerInitializer.newInstance().initialize()
         val daoAlumnos = container.select(DaoAlumnos::class.java).get()
           GlobalScope.launch {
                coroutineScope {
                val alumnos = daoAlumnos.getAllAlumnos()
                println(alumnos)
                }
            }
      }
   }
}