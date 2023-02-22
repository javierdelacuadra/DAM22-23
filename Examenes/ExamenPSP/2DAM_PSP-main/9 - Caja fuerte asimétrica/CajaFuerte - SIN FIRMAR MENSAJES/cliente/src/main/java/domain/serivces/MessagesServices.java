package domain.serivces;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.Message;
import retrofit2.Response;

import java.util.List;

public interface MessagesServices {
    Single<Either<String, Message>> update(Message message);

    Single<Either<String, Response<Object>>> delete(int id);

    Single<Either<String, Message>> add(Message message);

    List<Message> decrypt(List<Message> messages);
}
