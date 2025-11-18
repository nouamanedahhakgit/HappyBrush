package com.example.mywordsapp;

import android.os.Bundle;
import android.content.res.AssetManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    WordAdapter adapter;
    List<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordAdapter(words);
        recyclerView.setAdapter(adapter);
        
        loadWords();
    }

    private void loadWords() {
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("words.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) words.add(line);
            }
            br.close();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class WordAdapter extends RecyclerView.Adapter<WordAdapter.VH> {
        List<String> items;
        WordAdapter(List<String> items) { this.items = items; }
        
        @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
            return new VH(v);
        }
        
        @Override public void onBindViewHolder(VH holder, int position) {
            holder.text.setText(items.get(position));
        }
        
        @Override public int getItemCount() { return items.size(); }
        
        static class VH extends RecyclerView.ViewHolder {
            TextView text;
            VH(View v) { super(v); text = v.findViewById(R.id.textWord); }
        }
    }
}
