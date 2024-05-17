package com.example.tour_guide_app.RecyclerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tour_guide_app.R;
import com.example.tour_guide_app.GetUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Get_users_recy extends RecyclerView.Adapter<Get_users_recy.MyViewHolder> {

    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;
    private static final String fsts ="0";
    Activity act;



    public Get_users_recy(Context applicationContext, JSONArray jsonArray, Activity a) {
        this.context = applicationContext;
        this.array = jsonArray;
        this.act = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.get_users_list, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);

            holder.tv1.setText(jsonObject.getString("u_id"));
            holder.tv2.setText(jsonObject.getString("name"));
            holder.tv3.setText(jsonObject.getString("username"));
            holder.tv4.setText(jsonObject.getString("address"));
            holder.tv5.setText(jsonObject.getString("mobile"));
            holder.tv6.setText(jsonObject.getString("email"));

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
        TextView tv1,tv2,tv3,tv4,tv5,tv6;
        CardView cv;
        Button b1;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.u_id);
            tv2 = (TextView) itemView.findViewById(R.id.name);
            tv3 = (TextView) itemView.findViewById(R.id.username);
            tv4 = (TextView) itemView.findViewById(R.id.address);
            tv5 = (TextView) itemView.findViewById(R.id.mobile);
            tv6 = (TextView) itemView.findViewById(R.id.email);

            cv=(CardView)itemView.findViewById(R.id.card_view) ;

        }
    }
}
