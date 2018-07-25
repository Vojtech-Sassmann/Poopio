package cz.tyckouni.poopio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cz.tyckouni.poopio.base.entities.Poop;

public class OverviewActivity extends AppCompatActivity {
    private static final String TAG = "OverviewActivity";
    public static final String EXTRA_POOP = "cz.tyckouni.poopio.extra.POOP";

    private List<Poop> mPoopList = new LinkedList<>();
    private int mCounter = 0;
    private RecyclerView mRecyclerView;
    private PoopListAdapter mAdapter;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Random random = new Random();
        for (long i = 0L; i < 20; i++) {
            mPoopList.add(new Poop(i, random.nextInt(101), random.nextInt(101)));
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mFab = findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPoopIntent = new Intent(getApplicationContext(), EditPoopActivity.class);
                // TODO add for result
                startActivity(newPoopIntent);
            }
        });

        mAdapter = new PoopListAdapter(this, mPoopList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
