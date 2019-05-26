package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Jasa_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiJasaService {
    @GET("api/jasas")
    Call<Jasa_data> tampilservis();

    @POST("api/jasas/store")
    @FormUrlEncoded
    Call<ResponseBody> tambahservice(@Field("Nama_Jasa") String Nama_Jasa,
                                     @Field("Harga_Jasa") String Harga_Jasa);

    @PATCH("api/jasas/update/{id}")
    @FormUrlEncoded
    Call<ResponseBody> editservice(@Path("id")Integer id,
                                   @Field("Nama_Jasa") String Nama_Jasa,
                                   @Field("Harga_Jasa") String Harga_Jasa);

    @DELETE("api/jasas/delete/{id}")
    Call<ResponseBody> deleteservice(@Path("id")Integer id);
}
