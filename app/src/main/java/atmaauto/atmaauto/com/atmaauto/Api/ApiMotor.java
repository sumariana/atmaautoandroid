package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiMotor {

    @DELETE("api/motors/delete/{id}")
    Call<ResponseBody>deletemotor(@Path("id") Integer id);

    @POST("api/motors/store")
    @FormUrlEncoded
    Call<ResponseBody>tambahmotor(@Field("Merk") String Merk,
                                     @Field("Tipe") String Tipe);

    @PATCH("api/motors/update/{id}")
    @FormUrlEncoded
    Call<ResponseBody>editmotor(@Path("id") Integer id,
                                   @Field("Merk") String Merk,
                                   @Field("Tipe") String Tipe);
}
