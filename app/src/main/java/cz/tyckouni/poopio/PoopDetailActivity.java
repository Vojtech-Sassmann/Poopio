package cz.tyckouni.poopio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cz.tyckouni.poopio.base.entities.Poop;

public class PoopDetailActivity extends AppCompatActivity {

    private TextView mValueType;
    private TextView mValueSize;
    private TextView mValueConsistency;
    private TextView mValueColor;
    private TextView mValueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poop_detail);

        Intent intent = getIntent();
        Poop poop = (Poop) intent.getSerializableExtra(OverviewActivity.EXTRA_POOP);

        initTextViews();

        updatePoopInfo(poop);
    }

    private void updatePoopInfo(Poop poop) {
        mValueType.setText(poop.getType());
        mValueSize.setText(String.valueOf(poop.getSize()));
        mValueConsistency.setText(String.valueOf(poop.getConsistency()));
        mValueColor.setText(poop.getColor());
        mValueDate.setText(poop.getDateAndTime());
    }

    private void initTextViews() {
        mValueType = findViewById(R.id.type_value);
        mValueSize = findViewById(R.id.size_value);
        mValueConsistency = findViewById(R.id.consistency_value);
        mValueColor = findViewById(R.id.color_value);
        mValueDate = findViewById(R.id.date_value);
    }
}
