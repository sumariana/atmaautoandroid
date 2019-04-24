package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLogin {
    String JSONURL = "http://10.53.10.176:8000/";

    @POST("api/pegawais/mobileauthenticate")
    @FormUrlEncoded
    Call<LoginResponse>loginPegawai(@Field ("Username") String username,
                               @Field ("Password") String password);

}
