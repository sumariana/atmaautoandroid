package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen_response;
import atmaauto.atmaauto.com.atmaauto.models.Motor_data;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiKonsumen {
    String JSONURL = "http://10.53.10.176:8000/";

    @GET("api/konsumens")
    Call<Konsumen_data>tampilkonsumen();

    @GET("api/motor_konsumens")
    Call<MotorKonsumen_data>tampilmotorkonsumen();

    @GET("api/motors")
    Call<Motor_data>getlistmotor();

    @DELETE("api/motor_konsumens/delete/{id}")
    Call<ResponseBody>deletemotorkonsumen(@Path("id") Integer id);

    @POST("api/motor_konsumens/store")
    @FormUrlEncoded
    Call<MotorKonsumen_response>tambahMotorKonsumen(@Field("Id_Konsumen") Integer Id_Konsumen,
                                               @Field("Id_Motor") Integer Id_Motor,
                                               @Field("Plat_Kendaraan") String Plat_Kendaraan);
}
