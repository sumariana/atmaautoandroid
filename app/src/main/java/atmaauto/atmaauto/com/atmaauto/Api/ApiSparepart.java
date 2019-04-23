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
import retrofit2.http.Query;

public interface ApiSparepart {
    String JSONURL = "https://atmauto.jasonfw.com/";
    @GET("api/spareparts")
    Call<Sparepart_data>tampilkatalog();
}
