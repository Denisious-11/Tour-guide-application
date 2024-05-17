package com.example.tour_guide_app.RecyclerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tour_guide_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_places_recy extends RecyclerView.Adapter<Show_places_recy.MyViewHolder> {

    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;
    private static final String fsts ="0";
    Activity act;



    public Show_places_recy(Context applicationContext, JSONArray jsonArray, Activity a) {
        this.context = applicationContext;
        this.array = jsonArray;
        this.act = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_places_list, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);

            holder.tv1.setText(jsonObject.getString("p_id"));
            holder.tv2.setText(jsonObject.getString("pname"));
            holder.tv3.setText(jsonObject.getString("rating"));
            holder.tv4.setText(jsonObject.getString("o_time"));
            holder.tv5.setText(jsonObject.getString("c_time"));
            holder.tv6.setText(jsonObject.getString("amount"));
            holder.tv7.setText(jsonObject.getString("link"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        CardView cv;
        Button b1;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.p_id);
            tv2 = (TextView) itemView.findViewById(R.id.pname);
            tv3 = (TextView) itemView.findViewById(R.id.rating);
            tv4 = (TextView) itemView.findViewById(R.id.o_time);
            tv5 = (TextView) itemView.findViewById(R.id.c_time);
            tv6 = (TextView) itemView.findViewById(R.id.amount);
            tv7 = (TextView) itemView.findViewById(R.id.link);

            cv=(CardView)itemView.findViewById(R.id.card_view) ;

        }
    }
}
