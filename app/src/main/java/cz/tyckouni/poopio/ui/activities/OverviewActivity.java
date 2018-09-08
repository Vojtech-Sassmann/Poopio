package cz.tyckouni.poopio.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import cz.tyckouni.poopio.R;
import cz.tyckouni.poopio.base.entities.Poop;

public class OverviewActivity extends AppCompatActivity {
    private static final String TAG = "OverviewActivity";

    public static final int NEW_POOP_REQUEST = 1;
    public static final String EXTRA_POOP = "cz.tyckouni.poopio.extra.POOP";

    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    private LinkedList<Poop> mPoopList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private PoopListAdapter mAdapter;
    private ProgressBar mLoadingProgressBar;

    private boolean pressedBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overview);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_view);
        mLoadingProgressBar = findViewById(R.id.loading_progressBar);

        mAdapter = new PoopListAdapter(this, mPoopList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpPoopsData();
    }

    @Override
    public void onBackPressed() {
        if (pressedBack) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        Toast.makeText(OverviewActivity.this, getString(R.string.exit_toast_text),
                Toast.LENGTH_LONG)
                .show();

        pressedBack = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent goToLoginIntent = new Intent(OverviewActivity.this, MainActivity.class);
            startActivity(goToLoginIntent);

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_credits) {
            Intent goCreditsIntent = new Intent(OverviewActivity.this, CreditsActivity.class);
            startActivity(goCreditsIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_POOP_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(OverviewActivity.this, R.string.newPoopCreated_toastText,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void addPoop(View view) {
        Intent newPoopIntent = new Intent(getApplicationContext(), PoopCreationActivity.class);
        startActivityForResult(newPoopIntent, NEW_POOP_REQUEST);
    }

    private void setUpPoopsData() {
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userPoopsReference = mDatabase.child("poops").child(mUser.getUid());

        userPoopsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mLoadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mLoadingProgressBar.setVisibility(View.GONE);
                //TODO?
            }
        });

        userPoopsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Poop poop = dataSnapshot.getValue(Poop.class);
                mPoopList.addFirst(poop);
                mRecyclerView.getAdapter().notifyItemInserted(0);

                mRecyclerView.smoothScrollToPosition(0);

                Log.d(TAG, "onChildAdded: added '" + poop + "'");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Poop poop = dataSnapshot.getValue(Poop.class);
                int poopIndex = mPoopList.indexOf(poop);
                mPoopList.set(poopIndex, poop);
                mRecyclerView.getAdapter().notifyItemChanged(poopIndex);

                Log.d(TAG, "onChildChanged: changed '" + poop +"'");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Poop poop = dataSnapshot.getValue(Poop.class);
                int poopIndex = mPoopList.indexOf(poop);
                mPoopList.remove(poopIndex);
                mRecyclerView.getAdapter().notifyItemRemoved(poopIndex);

                Log.d(TAG, "onChildRemoved: removed '" + poop + "' at position: " + poopIndex);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // TODO
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                new AlertDialog.Builder(OverviewActivity.this)
                        .setTitle(R.string.connectionerror_dialog_title)
                        .setMessage(databaseError.getMessage())
                        .setPositiveButton(getString(R.string.connectionalert_dialog_okbutton),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO
                                    }
                                })
                        .show();
            }
        });
    }
}
