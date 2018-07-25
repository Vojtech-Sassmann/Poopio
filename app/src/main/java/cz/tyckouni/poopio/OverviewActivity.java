package cz.tyckouni.poopio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Random random = new Random();
        for (long i = 0L; i < 20; i++) {
            mPoopList.add(new Poop(i, random.nextInt(11), random.nextInt(11)));
        }

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new PoopListAdapter(this, mPoopList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
