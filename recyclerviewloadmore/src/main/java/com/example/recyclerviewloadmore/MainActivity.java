package com.example.recyclerviewloadmore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerLayoutManager layoutManager = new RecyclerLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                Log.i("bonc",newState+"");
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Log.i("bonc",newState+"");
//                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
//                    if (lastVisiblePosition >= layoutManager.getItemCount() -1) {
//                        Log.i("bonc","123");
//                    }
//
//                }
//            }
//
//        });

    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19"};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.recycler_view_item, parent, false);
            Log.i("bonc","onCreateViewHolder");
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(data[position]);
            Log.i("bonc","onBindViewHolder---");
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }


}

