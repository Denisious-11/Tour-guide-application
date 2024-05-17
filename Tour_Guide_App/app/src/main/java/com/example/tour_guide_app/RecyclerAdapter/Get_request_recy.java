package com.example.tour_guide_app.RecyclerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.tour_guide_app.UserHome;
import com.example.tour_guide_app.ViewPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Get_request_recy extends RecyclerView.Adapter<Get_request_recy.MyViewHolder> {

    private static final String TAG = "RecyclerNews";
    private final Context context;
    private final JSONArray array;
    private static final String fsts ="0";
    Activity act;



    public Get_request_recy(Context applicationContext, JSONArray jsonArray, Activity a) {
        this.context = applicationContext;
        this.array = jsonArray;
        this.act = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.get_request_list, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            JSONObject jsonObject = array.getJSONObject(position);

            holder.tv1.setText(jsonObject.getString("r_id"));
            holder.tv2.setText(jsonObject.getString("sender_username"));


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
        TextView tv1,tv2;
        CardView cv;
        Button b1,b2;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.r_id);
            tv2 = (TextView) itemView.findViewById(R.id.s_username);

            cv=(CardView)itemView.findViewById(R.id.card_view) ;

            b1 =(Button)itemView.findViewById(R.id.accept);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String r_id=tv1.getText().toString();


                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/accept_request/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if(dat.equals("yes"))
                                {
                                    Toast.makeText(context,"Accept Successfully",Toast.LENGTH_SHORT).show();

                                    Intent i1= new Intent(context, UserHome.class);
                                    context.startActivity(i1);

                                }
                                else {
                                    Toast.makeText(context, "Error Occured! ", Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.getMessage());
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> m=new HashMap<>();
                            m.put("r_id",r_id);

                            return m;
                        }
                    };
                    requestQueue.add(requ);
                }
            });

            b2 =(Button)itemView.findViewById(R.id.reject);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String r_id=tv1.getText().toString();


                    RequestQueue requestQueue= Volley.newRequestQueue(context);
                    StringRequest requ=new StringRequest(Request.Method.POST, "http://192.168.29.134:8000/reject_request/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {

                            try {
                                JSONObject o = new JSONObject(response);
                                String dat = o.getString("msg");
                                if(dat.equals("yes"))
                                {
                                    Toast.makeText(context,"Rejected Successfully",Toast.LENGTH_SHORT).show();

                                    Intent i1= new Intent(context, UserHome.class);
                                    context.startActivity(i1);

                                }
                                else {
                                    Toast.makeText(context, "Error Occured! ", Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.getMessage());
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> m=new HashMap<>();
                            m.put("r_id",r_id);

                            return m;
                        }
                    };
                    requestQueue.add(requ);
                }
            });

        }
    }
}
