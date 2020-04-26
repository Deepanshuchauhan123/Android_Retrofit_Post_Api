package com.example.retrofit_working_api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<post> posts;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts=new ArrayList<>();
         adapter=new RecyclerAdapter(posts,this);

        recyclerView = (RecyclerView)findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter( adapter );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://quiz-restapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder jsonPlaceHolder= retrofit.create(JsonPlaceHolder.class);
        Call<List<post>>call = jsonPlaceHolder.getPosts();

        call.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this, " Response failed "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Jai Sai Baba", Toast.LENGTH_SHORT).show();
                posts.addAll(response.body());

                adapter.notifyDataSetChanged();
              /*  List<post> posts =  response.body();

                for(post post : posts)
                {
                    String content ="";
                    content +="ID: "+post.getId() +"\n";
                    content +="Title: "+post.getTitle() +"\n";
                    content +="Standard: "+post.getStd() +"\n\n";
                    textViewResult.append(content);
                }
                */
            }
            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {
           // textViewResult.setText(t.getMessage());
            }
        });

    }

    private class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

        List<post>posts;
        Context context;


        public RecyclerAdapter(List<post> posts,Context context) {
            this.posts = posts;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.id.setText(posts.get(position).getId());
            holder.title.setText(posts.get(position).getTitle());
            holder.std.setText(posts.get(position).getStd());
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView id,title,std;
            public MyViewHolder(@NonNull View itemView) {

                super(itemView);
                id=(TextView)itemView.findViewById(R.id.id);
                title=(TextView)itemView.findViewById(R.id.title);
                std=(TextView)itemView.findViewById(R.id.std);
            }
        }
    }
}


/*
<androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <TextView
        android:id="@+id/text_view_result"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#000"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
    </androidx.core.widget.NestedScrollView>
 */