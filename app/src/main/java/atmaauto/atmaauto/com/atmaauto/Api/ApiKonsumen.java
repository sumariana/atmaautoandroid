package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiKonsumen {
    String JSONURL = "https://atmauto.jasonfw.com/";

    @GET("api/konsumens")
    Call<Konsumen_data>tampilkonsumen();
}
