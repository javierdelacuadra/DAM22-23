package ui.controllers

import dao.PersonaDao
import domain.modelo.Mascota
import domain.modelo.Persona
import io.github.palexdev.materialfx.controls.MFXButton
import io.github.palexdev.materialfx.controls.MFXTableColumn
import io.github.palexdev.materialfx.controls.MFXTableView
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell
import javafx.fxml.FXML
import javafx.fxml.Initializable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import ui.common.BaseScreenController
import utils.Trither
import java.net.URL
import java.util.*

class ListPersonasController : Initializable, BaseScreenController() {


    @FXML private lateinit var btnClearSelection: MFXButton
    @FXML private lateinit var btnKill: MFXButton
    @FXML lateinit var colId: MFXTableColumn<Persona>

    @FXML
    private lateinit var colApellido: MFXTableColumn<Persona>
    @FXML
    private lateinit var colNombre: MFXTableColumn<Persona>

    @FXML private lateinit var colCantMasc: MFXTableColumn<Persona>

    @FXML
    private lateinit var tblPersonas: MFXTableView<Persona>

    private var idSelected: Int? = null

    private val dao = PersonaDao

    @OptIn(DelicateCoroutinesApi::class) // SonarLint on GlobalScope.launch

    override fun principalCargado() {


        GlobalScope.launch(Dispatchers.JavaFx) {
            getPrincipalController()!!.showLoading(true)
            tblPersonas.items.clear()
            dao.cargarPersonas().collect{trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.data?.let{ personas ->
                            personas.forEach { persona ->
                                persona.let {
                                    tblPersonas.items.add(
                                        Persona(
                                            id = it.id,
                                            name = it.name,
                                            surname = it.surname,
                                            mascotas = it.mascotas.map { Mascota(persona = Persona()) })
                                    )
                                }
                            }
                        }
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(trither.message)
                    }
                }
            }
            getPrincipalController()!!.showLoading(false)

        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        colId.setRowCellFactory { _: Persona? -> MFXTableRowCell<Persona, Any> { per1: Persona -> per1.id } }
        colNombre.setRowCellFactory { _: Persona? -> MFXTableRowCell<Persona, Any> { per1: Persona -> per1.name } }
        colApellido.setRowCellFactory { _: Persona? -> MFXTableRowCell<Persona, Any> { per1: Persona -> per1.surname } }
        colCantMasc.setRowCellFactory { _: Persona? -> MFXTableRowCell<Persona, Any> { per1: Persona -> per1.mascotas.size } }
        tblPersonas.isFooterVisible = false

        tblPersonas.selectionModel.selectionProperty().addListener { observableValue, _, newValue ->
            if (newValue != null && !observableValue.getValue().isEmpty()) {
                newValue.values.stream().findFirst().orElse(null)?.let { persona ->
                    idSelected = persona.id
                    btnKill.isVisible = true
                    btnClearSelection.isVisible = true
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun matar() {
        GlobalScope.launch(Dispatchers.JavaFx) {
            dao.deletePersona(idSelected!!).collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.data?.let { persona ->
                            persona.let {
                                tblPersonas.items.removeIf { persona -> persona.id == it.id }
                                getPrincipalController()!!.showAlertInformation("Admin, has matado a " + persona.name + " " + persona.surname )
                                clearSelection()
                            }
                        }
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(trither.message)
                    }
                }
            }
        }
    }

    fun clearSelection() {
        idSelected = null
        btnKill.isVisible = false
        btnClearSelection.isVisible = false
        tblPersonas.selectionModel.clearSelection()

    }


}
