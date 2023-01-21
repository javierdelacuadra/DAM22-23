package ui.pantallas.addcarpeta;

import jakarta.inject.Inject;
import model.Carpeta;
import servicios.ServiciosCarpetas;

import java.util.concurrent.atomic.AtomicReference;

public class AddCarpetaViewModel {

    private final ServiciosCarpetas serviciosCarpetas;

    @Inject
    public AddCarpetaViewModel(ServiciosCarpetas serviciosCarpetas) {
        this.serviciosCarpetas = serviciosCarpetas;
    }

    public String addCarpeta(Carpeta carpeta) {
        AtomicReference<String> result = new AtomicReference<>("");
        serviciosCarpetas.addCarpeta(carpeta).subscribe(
                either -> {
                    if (either.isRight()) {
                        result.set("Carpeta creada");
                    } else {
                        String error = either.getLeft();
                        result.set(error);
                    }
                }
        );
        return result.get();
    }
}
