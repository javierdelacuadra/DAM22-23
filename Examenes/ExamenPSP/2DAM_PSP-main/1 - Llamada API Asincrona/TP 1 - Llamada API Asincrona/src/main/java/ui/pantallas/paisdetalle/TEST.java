package ui.pantallas.paisdetalle;

import dao.common.ConstantesDAO;
import dao.retrofit.llamadas.WorldApi;
import dominio.modelo.Pais;
import dominio.modelo.PaisDetalle;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TEST {


        static List<Pais> doors = new ArrayList<>();
        public static void main(String[] args) {



getEstadosPorPais()
        .delay(1, TimeUnit.SECONDS)
        .observeOn(Schedulers.io())
        .blockingSubscribe( lists ->
                System.out.println(lists.get().get(0)));
        }

        private static Single<Either<String, List<Pais>>> getEstadosPorPais(){
            doors.add(new Pais(1, "room2", "sss"));
            doors.add(new Pais(2, "room3", "sss"));
            doors.add(new Pais(3, "room4", "sss"));

            return Single.just(doors)
                    .map(responsePaisItemList ->
                        Either.right(responsePaisItemList).mapLeft(Object::toString))
                    .subscribeOn(Schedulers.io())
                    .onErrorReturn(throwable -> Either.left(ConstantesDAO.ERROR_EN_LA_REQUEST + ConstantesDAO.BREAK + throwable.getMessage()));
        }
    }

