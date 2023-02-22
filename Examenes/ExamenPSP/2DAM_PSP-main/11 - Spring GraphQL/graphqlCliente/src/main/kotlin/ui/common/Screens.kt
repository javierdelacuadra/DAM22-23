package ui.common

enum class Screens(val ruta: String) {
    LOGIN("/fxml/login.fxml"), // "ui/principal/Login.fxml"
    PRINCIPAL("/fxml/principal.fxml"), // "ui/principal/Principal.fxml"
    REGISTER("/fxml/register.fxml"), // "ui/principal/Register.fxml"
    LIST_PERSONAS("/fxml/listPersonas.fxml") // "ui/principal/ListPersonas.fxml"
    ,
    MY_PROFILE("/fxml/myProfile.fxml"),
    LIST_MASCOTAS("/fxml/listMascotas.fxml")


}
//    var PRINCIPAL: Screens? = "principal.fxml"
//
//    var REGISTER: Screens? = null
//
//    var MAIN_FOLDERS: Screens? = null
//
//    var MAIN_MESSAGES: Screens? = null
//
//    private var ruta: String? = null

