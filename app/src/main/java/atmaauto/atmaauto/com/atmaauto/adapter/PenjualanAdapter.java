package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailPenjualanController;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.MyViewHolder>{
    private Context context;
    private List<TransaksiPenjualan> mList;
    private List<TransaksiPenjualan> mListfilter;

    public PenjualanAdapter (Context context,List<TransaksiPenjualan>mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }
    @Override
    public PenjualanAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaksi_adapter, viewGroup, false);
        return new PenjualanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final TransaksiPenjualan transaksiPenjualan = mListfilter.get(i);
        myViewHolder.tanggal.setText(transaksiPenjualan.getTanggalTransaksi());
        myViewHolder.namakonsumen.setText("ID Konsumen : "+transaksiPenjualan.getIdKonsumen());
        myViewHolder.totalharga.setText("Total Harga : "+transaksiPenjualan.getTotal());
        if(transaksiPenjualan.getStatus()==0)
        {
            myViewHolder.statuspenjualan.setText("Ordered");
            myViewHolder.statuspenjualan.setBackgroundColor(Color.parseColor("#f62d30"));
        }else if(transaksiPenjualan.getStatus()==1){
            myViewHolder.statuspenjualan.setText("Process");
            myViewHolder.statuspenjualan.setBackgroundColor(Color.parseColor("#f62d30"));
        }else if(transaksiPenjualan.getStatus()==2){
            myViewHolder.statuspenjualan.setText("Finished");
            myViewHolder.statuspenjualan.setBackgroundColor(Color.parseColor("#f62d30"));
        }else if(transaksiPenjualan.getStatus()==3){
            myViewHolder.statuspenjualan.setText("Paid");
            myViewHolder.statuspenjualan.setBackgroundColor(Color.parseColor("#f62d30"));
        }

        myViewHolder.edittransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transaksiPenjualan.getStatus()==0)
                {
                    Intent intent = new Intent(context, DetailPenjualanController.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idtransaksi",transaksiPenjualan.getIdTransaksi());
                    intent.putExtra("tanggal",transaksiPenjualan.getTanggalTransaksi());
                    intent.putExtra("status",transaksiPenjualan.getStatus());
                    intent.putExtra("subtotal",transaksiPenjualan.getSubtotal());
                    intent.putExtra("total",transaksiPenjualan.getTotal());
                    intent.putExtra("diskon",transaksiPenjualan.getDiskon());
                    if(transaksiPenjualan.getJenisTransaksi().equalsIgnoreCase("SP"))
                    {
                        intent.putExtra("jenis","Penjualan Sparepart");
                    }else if(transaksiPenjualan.getJenisTransaksi().equalsIgnoreCase("SV"))
                    {
                        intent.putExtra("jenis","Penjualan Jasa");
                    }else
                    {
                        intent.putExtra("jenis","Penjualan Jasa dan Sparepart");
                    }
                    context.startActivity(intent);
                }else if(transaksiPenjualan.getStatus()==1)
                {
                    Toast.makeText(context, "Can't be edited, already on process !", Toast.LENGTH_SHORT).show();
                }else if(transaksiPenjualan.getStatus()==2)
                {
                    Toast.makeText(context, "Transaction has been done, please meet the cashier to pay !", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Transaction has been paid and finished", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myViewHolder.deletetransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(transaksiPenjualan.getStatus()==0)
                {
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit.Builder builder=new Retrofit.
                            Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                            addConverterFactory(GsonConverterFactory.create(gson));
                    Retrofit retrofit=builder.build();
                    ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                    Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletetransaksi(transaksiPenjualan.getIdTransaksi());
                    responseBodyCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            mListfilter.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i,getItemCount());
                            Toast.makeText(context, "berhasil!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(context, "network error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                {
                    Toast.makeText(context, "Transaction is on process!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return mListfilter.size();
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<TransaksiPenjualan> filteredList = new ArrayList<>();
                    for (TransaksiPenjualan obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getJenisTransaksi().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    mListfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListfilter = (ArrayList<TransaksiPenjualan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tanggal,namakonsumen,totalharga,statuspenjualan;
        public ImageView edittransaksi,deletetransaksi;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            namakonsumen=itemView.findViewById(R.id.namakonsumen);
            totalharga=itemView.findViewById(R.id.totalharga);
            tanggal=itemView.findViewById(R.id.tanggal);
            statuspenjualan=itemView.findViewById(R.id.statuspenjualan);
            edittransaksi=itemView.findViewById(R.id.picedit);
            deletetransaksi=itemView.findViewById(R.id.picdel);

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
