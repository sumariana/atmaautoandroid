package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.DetailJasa;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JasaCartAdapter extends RecyclerView.Adapter<JasaCartAdapter.MyViewHolder> {
    private Context context;
    public int x;
    private List<DetailJasa> mList;
    private List<DetailJasa> mListfilter;

    public JasaCartAdapter(Context context,List<DetailJasa> mList,final int x){
        this.x=x;
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public JasaCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.servis_adapter, viewGroup, false);
        return new JasaCartAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull JasaCartAdapter.MyViewHolder myViewHolder, final int i) {
        final DetailJasa detailJasa = mListfilter.get(i);
        myViewHolder.namaservis.setText(detailJasa.getNamaJasa());
        myViewHolder.hargaservis.setText("Harga  : "+detailJasa.getSubtotalDetailJasa());
        if(x==1)
        {
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailJasa.getIdDetailJasa()==null)
                    {
                        Double harga = detailJasa.getSubtotalDetailJasa();
                        Intent intent = new Intent("custom-message-jasa");
                        intent.putExtra("harga",harga);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        mListfilter.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i,getItemCount());
                    }else{
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit.Builder builder=new Retrofit.
                                Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                                addConverterFactory(GsonConverterFactory.create(gson));
                        Retrofit retrofit=builder.build();
                        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                        Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletedetailjasa(detailJasa.getIdDetailJasa());
                        responseBodyCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200)
                                {
                                    Double harga = detailJasa.getSubtotalDetailJasa();
                                    Intent intent = new Intent("custom-message-jasa");
                                    intent.putExtra("harga",harga);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                    mListfilter.remove(i);
                                    notifyItemRemoved(i);
                                    notifyItemRangeChanged(i,getItemCount());
                                    Log.d( "sucess: ",response.message());
                                }else
                                    Log.d( "gagal: ",response.message());
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }else if(x==0)
        {
            myViewHolder.delete.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return mListfilter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namaservis,hargaservis;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //kotak=itemView.findViewById(R.id.listSparepartAdmin);
            namaservis=itemView.findViewById(R.id.namaservis);
            hargaservis=itemView.findViewById(R.id.hargaservis);
            delete=itemView.findViewById(R.id.deletebuttonadapter);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){}
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
