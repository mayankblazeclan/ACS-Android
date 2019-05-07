package com.hrfid.acs.service.api.sendtolocation;



import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

public class SendToLocationDestinationService {


    public interface LocationChangeAPI {
        @FormUrlEncoded
        @PUT("api/blood/updateBloodData")
        Observable<SendToLocationDestinationData> putSendToLocationDestinationApi(@Body List<RequestBodySendToLocationDestination> requestBody);
//        Observable<SendToLocationDestinationData> postLocationChangeApi(@FieldMap Map<String, Object> fields);
    }

    public interface LocationChangeInterface {
        void onResponseSuccess(boolean isSuccess);

        void onError(int errorCode);
    }

}
