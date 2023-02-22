package ui.screens.principal;

import jakarta.inject.Inject;
import serivces.impl.LoginServicesImpl;

public class PrincipalViewModel {

    private LoginServicesImpl loginServices;

    @Inject
    public PrincipalViewModel(LoginServicesImpl loginServices){
        this.loginServices = loginServices;
    }

    public void closeProgram(){
        loginServices.closeProgram();
    }



}
