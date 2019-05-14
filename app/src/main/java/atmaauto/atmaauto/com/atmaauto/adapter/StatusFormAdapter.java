package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.DetilList.StatusForm;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatusFormAdapter extends RecyclerView.Adapter<StatusFormAdapter.MyViewHolder>{

        private Context context;
        private List<DetailPengadaan> mList;
        private List<DetailPengadaan> mListfilter;

        public StatusFormAdapter(Context context,List<DetailPengadaan> mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
        }

    public StatusFormAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.status_adapter, viewGroup, false);
        return new StatusFormAdapter.MyViewHolder(itemView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namastatus;
        public EditText jumlahstatus;
        Button update;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //kotak=itemView.findViewById(R.id.listSparepartAdmin);
            namastatus=itemView.findViewById(R.id.namastatus);
            jumlahstatus=itemView.findViewById(R.id.jumlahstatus);
            update=itemView.findViewById(R.id.updatebutton);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){}
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusFormAdapter.MyViewHolder myViewHolder, final int i) {
        final DetailPengadaan detailPengadaan = mListfilter.get(i);
        myViewHolder.namastatus.setText(detailPengadaan.getNamaSparepart());
        myViewHolder.jumlahstatus.setText(detailPengadaan.getJumlah().toString());
        myViewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiSparepart.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiTransaksiPengadaan apiTransaksiPengadaan=retrofit.create(ApiTransaksiPengadaan.class);

                Call<ResponseBody> responseBodyCall = apiTransaksiPengadaan.updatejumlahpengadaan(detailPengadaan.getIdDetailPengadaan(),Integer.parseInt(myViewHolder.jumlahstatus.getText().toString()));
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==201)
                        {
                            Toast.makeText(context, "berhasil Update!", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(context, "gagal Update!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount(){
        return mListfilter.size();
    }


}
