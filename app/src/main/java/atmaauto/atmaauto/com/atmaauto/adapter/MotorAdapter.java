package atmaauto.atmaauto.com.atmaauto.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiMotor;
import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPengadaan;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailKonsumen;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahMotor;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen;
import atmaauto.atmaauto.com.atmaauto.models.Motor;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MotorAdapter extends RecyclerView.Adapter<MotorAdapter.MyViewHolder>{
    private Context context;
    private List<Motor> mList;
    private List<Motor> mListfilter;

    public MotorAdapter(Context context,List<Motor> mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }
    @Override
    public MotorAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.motor_adapter, viewGroup, false);
        return new MotorAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mListfilter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tipemotor,brandmotor;
        LinearLayout kotak;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //kotak=itemView.findViewById(R.id.listSparepartAdmin);
            tipemotor=itemView.findViewById(R.id.tipemotor);
            brandmotor=itemView.findViewById(R.id.brandmotor);
            delete=itemView.findViewById(R.id.deletebuttonadapter);
            kotak=itemView.findViewById(R.id.kotak);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){}
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<Motor> filteredList = new ArrayList<>();
                    for (Motor obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getMerk().toLowerCase().contains(charString.toLowerCase()) || obj.getTipe().contains(charSequence)) {
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
                mListfilter = (ArrayList<Motor>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void onBindViewHolder(@NonNull MotorAdapter.MyViewHolder myViewHolder, final int i) {
        final Motor motor = mListfilter.get(i);
        myViewHolder.tipemotor.setText(motor.getTipe());
        myViewHolder.brandmotor.setText(motor.getMerk());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, TambahMotor.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idmotor", motor.getIdMotor());
                intent.putExtra("merkmotor", motor.getMerk());
                intent.putExtra("tipemotor", motor.getTipe());
                intent.putExtra("editable", 1);
                context.startActivity(intent);
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiTransaksiPengadaan.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiMotor apiMotor=retrofit.create(ApiMotor.class);

                Call<ResponseBody> responseBodyCall = apiMotor.deletemotor(motor.getIdMotor());
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i,getItemCount());
                        Toast.makeText(context, "berhasil!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "network error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
