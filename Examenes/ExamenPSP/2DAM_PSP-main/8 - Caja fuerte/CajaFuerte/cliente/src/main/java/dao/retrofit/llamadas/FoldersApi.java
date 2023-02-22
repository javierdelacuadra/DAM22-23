package dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.utils.common.AplicationConstants;
import org.utils.domain.modelo.Folder;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface FoldersApi {


    String BASE_PATH = "folders";

    @GET(BASE_PATH + AplicationConstants.PATH_PARAM_USER_NAME)
    Single<List<Folder>> getAllFolders(@Path(AplicationConstants.USER_NAME) String userName);

    @POST(BASE_PATH + AplicationConstants.PATH_ADD)
    Single<Response<Object>> addFolder(@Body Folder folder);

    @GET(BASE_PATH)
    Single<Folder> getFolder(@Query(AplicationConstants.USER_NAME) String userName, @Query(AplicationConstants.NAME_FOLDER) String nameFolder, @Query(AplicationConstants.PASSWORD) String passFolder);

    @PUT(BASE_PATH + AplicationConstants.PATH_CHANGE_PASSWORD)
    Single<Folder> changePass(@Query(AplicationConstants.USER_NAME) String userName, @Query(AplicationConstants.NAME_FOLDER) String folderName, @Query(AplicationConstants.PASSWORD) String folderPass, @Query(AplicationConstants.NEW_PASSWORD) String newPass);
}


