package doctorw.classcircle.model.http;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/25.
 */

public interface HttpImpl {
    public interface OnGetDataResult {
        void getResult(Object object);
    }

    public interface HandleResponse {
        void handleResponse(String s, Call call, Response response);
    }
}
