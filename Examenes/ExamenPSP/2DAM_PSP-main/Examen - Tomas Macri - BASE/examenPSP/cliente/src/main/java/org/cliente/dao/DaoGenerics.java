package org.cliente.dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import retrofit2.Response;

public interface DaoGenerics {
    <T> Single<Either<String, T>> safeSingleApicall(Single<T> call);

    Single<Either<String, Response<Object>>> safeSingleDeleteApicall(Single<Response<Object>> call);
}
