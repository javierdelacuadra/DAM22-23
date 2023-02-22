package ui.controllers

import domain.modelo.Mascota
import domain.modelo.Persona
import domain.servicios.ServiciosMascota
import io.github.palexdev.materialfx.controls.MFXButton
import io.github.palexdev.materialfx.controls.MFXTableColumn
import io.github.palexdev.materialfx.controls.MFXTableView
import io.github.palexdev.materialfx.controls.MFXTextField
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell
import javafx.fxml.FXML
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import ui.common.BaseScreenController
import utils.Trither


private const val COMPLETA_TODOS_LOS_CAMPOS = "Completa bien todos los campos (la edad es numérica, estamos grandes)"

class ListMascotasController : BaseScreenController() {
    @FXML lateinit var btnClearSelection: MFXButton
    @FXML lateinit var btnEdit: MFXButton
    @FXML lateinit var btnDelete: MFXButton
    @FXML lateinit var btnAdd: MFXButton
    @FXML lateinit var txtType: MFXTextField
    @FXML lateinit var txtName: MFXTextField
    @FXML lateinit var txtAge: MFXTextField
    @FXML lateinit var tblPets: MFXTableView<Mascota>
    @FXML lateinit var colId: MFXTableColumn<Mascota>
    @FXML lateinit var colName: MFXTableColumn<Mascota>
    @FXML lateinit var colAge: MFXTableColumn<Mascota>
    @FXML lateinit var colType: MFXTableColumn<Mascota>

val serviciosMascota: ServiciosMascota = ServiciosMascota
    private var idPetSelected: Int? = null
    @OptIn(DelicateCoroutinesApi::class)
    override fun principalCargado() {
        colId.setRowCellFactory { _: Mascota? -> MFXTableRowCell<Mascota, Any> { mas: Mascota -> mas.id.toString() } }
        colName.setRowCellFactory { _: Mascota? -> MFXTableRowCell<Mascota, Any> { mas: Mascota -> mas.name } }
        colAge.setRowCellFactory { _: Mascota? -> MFXTableRowCell<Mascota, Any> { mas: Mascota -> mas.age.toString() } }
        colType.setRowCellFactory { _: Mascota? -> MFXTableRowCell<Mascota, Any> { mas: Mascota -> mas.type } }

        changeButtonsVisibility(false)

        GlobalScope.launch(Dispatchers.JavaFx) {
            serviciosMascota.cargarMascotasUserLogged().collect { trither ->
                when (trither) {
                    is Trither.Success -> {
                        getPrincipalController()!!.showLoading(false)
                        trither.data?.let { mascotas ->
                            tblPets.items.addAll(mascotas)
                        }
                    }

                    is Trither.Error -> {
                        getPrincipalController()!!.showLoading(false)
                        getPrincipalController()!!.showAlertError(trither.message ?: "Error")
                    }

                    is Trither.Loading -> {
                        getPrincipalController()!!.showLoading(true)
                    }
                }
            }

            tblPets.selectionModel.selectionProperty().addListener { observableValue, _, newValue ->
                if (newValue != null && !observableValue.getValue().isEmpty()) {
                    newValue.values.stream().findFirst().orElse(null)?.let { mascota ->
                        txtName.text = mascota.name
                        txtAge.text = mascota.age.toString()
                        txtType.text = mascota.type
                        idPetSelected = mascota.id

                        changeButtonsVisibility(isPetSelected = true)
                    } ?: changeButtonsVisibility(false)
                }
            }
        }
    }

    private fun changeButtonsVisibility(isPetSelected: Boolean = false) {
        btnEdit.isVisible = isPetSelected
        btnDelete.isVisible = isPetSelected
        btnAdd.isVisible = !isPetSelected
        btnClearSelection.isVisible = isPetSelected
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun deletePet() {
        btnDelete.isDisable = true
        GlobalScope.launch(Dispatchers.JavaFx) {
            idPetSelected?.let {
                serviciosMascota.deleteMascota(it).collect { trither ->
                    when (trither) {
                        is Trither.Success -> {
                            getPrincipalController()!!.showLoading(false)
                            trither.data?.let { mascota ->
                                tblPets.items.remove(mascota)
                            }
                            clearSelection()
                            getPrincipalController()!!.showAlertInformation("Mascota eliminada correctamente")
                        }

                        is Trither.Error -> {
                            getPrincipalController()!!.showLoading(false)
                            getPrincipalController()!!.showAlertError(trither.message ?: "Error")
                        }

                        is Trither.Loading -> {
                            getPrincipalController()!!.showLoading(true)
                        }
                    }
                }
            }?: getPrincipalController()!!.showAlertError("Selecciona una mascota")
        }
        btnDelete.isDisable = false
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun editPet() {
        btnEdit.isDisable = true
        try {
            val mascota = Mascota(id = idPetSelected?:0, name = txtName.text, age = txtAge.text.toInt(), type = txtType.text, persona = Persona(id = getPrincipalController()!!.userLoggedIn!!.id))
            if (mascota.id < 1 || mascota.name.isEmpty() || mascota.type.isEmpty()) {
                getPrincipalController()!!.showAlertError("Completa bien todos los campos / Selecciona una pet")
            }
            else{
                GlobalScope.launch(Dispatchers.JavaFx) {
                    serviciosMascota.updateMascota(mascota).collect { trither ->
                        when (trither) {
                            is Trither.Success -> {
                                getPrincipalController()!!.showLoading(false)
                                trither.data?.let { mascota ->
                                    tblPets.items.set(tblPets.items.indexOf(mascota), mascota)
                                }
                                getPrincipalController()!!.showAlertInformation("Mascota actualizada  correctamente")
                            }
                            is Trither.Error -> {
                                getPrincipalController()!!.showLoading(false)
                                getPrincipalController()!!.showAlertError(trither.message ?: "Error")
                            }
                            is Trither.Loading -> {
                                getPrincipalController()!!.showLoading(true)
                            }
                        }
                    }
                }
            }
        } catch (e: NumberFormatException) {
            getPrincipalController()!!.showAlertError(COMPLETA_TODOS_LOS_CAMPOS)
        } finally {
            btnEdit.isDisable = false
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addPet() {
        btnAdd.isDisable = true
        try {
            val mascota = Mascota(name = txtName.text, age = txtAge.text.toInt(), type = txtType.text, persona = Persona(id = getPrincipalController()!!.userLoggedIn!!.id))
            if (mascota.name.isEmpty() || mascota.type.isEmpty() || mascota.age < 0) {
                getPrincipalController()!!.showAlertError(COMPLETA_TODOS_LOS_CAMPOS)
            }
            else{
                GlobalScope.launch(Dispatchers.JavaFx) {
                    serviciosMascota.addMascota(mascota).collect { trither ->
                        when (trither) {
                            is Trither.Success -> {
                                getPrincipalController()!!.showLoading(false)
                                trither.data?.let { mascota ->
                                    tblPets.items.add(mascota)
                                }
                                getPrincipalController()!!.showAlertInformation("Mascota creada correctamente")
                            }
                            is Trither.Error -> {
                                getPrincipalController()!!.showLoading(false)
                                getPrincipalController()!!.showAlertError(trither.message ?: "Error")
                            }
                            is Trither.Loading -> {
                                getPrincipalController()!!.showLoading(true)
                            }
                        }
                    }
                }
            }
        } catch (e: NumberFormatException) {
            getPrincipalController()!!.showAlertError(COMPLETA_TODOS_LOS_CAMPOS)
        } finally {
            btnAdd.isDisable = false
        }
    }

    fun clearSelection() {
        tblPets.selectionModel.clearSelection()
        changeButtonsVisibility(false)
        txtAge.text = ""
        txtName.text = ""
        txtType.text = ""
        idPetSelected = null
    }

}
