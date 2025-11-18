import os
import urllib.request
from pathlib import Path

PROJECT = "MyWordsApp"
PACKAGE = "com.example.mywordsapp"

def create_project():
    ROOT = Path(PROJECT)
    if ROOT.exists():
        import shutil
        shutil.rmtree(ROOT)
    
    # Create directories
    (ROOT / "app/src/main/java/com/example/mywordsapp").mkdir(parents=True)
    (ROOT / "app/src/main/res/layout").mkdir(parents=True)
    (ROOT / "app/src/main/assets").mkdir(parents=True)
    
    # Download words
    print("Downloading word list...")
    words_url = "https://raw.githubusercontent.com/first20hours/google-10000-english/master/20k.txt"
    try:
        response = urllib.request.urlopen(words_url)
        words = response.read().decode('utf-8').splitlines()[:1000]
        (ROOT / "app/src/main/assets/words.txt").write_text("\n".join(words))
        print(f"✓ Downloaded {len(words)} words")
    except:
        # Fallback words
        words = [f"Word {i+1}" for i in range(1000)]
        (ROOT / "app/src/main/assets/words.txt").write_text("\n".join(words))
        print("✓ Created sample words")
    
    # Create build.gradle (app)
    (ROOT / "app/build.gradle").write_text("""
plugins {
    id 'com.android.application'
}

android {
    compileSdk 33
    namespace 'com.example.mywordsapp'

    defaultConfig {
        applicationId "com.example.mywordsapp"
        minSdk 21
        targetSdk 33
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.recyclerview:recyclerview:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
}
""")

    # Create build.gradle (project)
    (ROOT / "build.gradle").write_text("""
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.0.2'
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
""")

    # settings.gradle
    (ROOT / "settings.gradle").write_text("""
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MyWordsApp"
include ':app'
""")

    # AndroidManifest.xml
    (ROOT / "app/src/main/AndroidManifest.xml").write_text("""<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.mywordsapp">

    <application
        android:allowBackup="true"
        android:label="My Words App"
        android:theme="@style/Theme.AppCompat.Light">
        <activity android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
""")

    # MainActivity.java
    (ROOT / "app/src/main/java/com/example/mywordsapp/MainActivity.java").write_text("""package com.example.mywordsapp;

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
""")

    # Layout files
    (ROOT / "app/src/main/res/layout/activity_main.xml").write_text("""<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
""")

    (ROOT / "app/src/main/res/layout/item_word.xml").write_text("""<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/textWord"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp"
    android:textSize="18sp" />
""")

    # gradle.properties
    (ROOT / "gradle.properties").write_text("""
org.gradle.jvmargs=-Xmx1024m
android.useAndroidX=true
""")

    print("✓ Android project created successfully!")
    return True

if __name__ == "__main__":
    create_project()