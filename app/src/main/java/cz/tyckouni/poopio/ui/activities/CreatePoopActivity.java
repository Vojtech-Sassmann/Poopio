package cz.tyckouni.poopio.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import cz.tyckouni.poopio.ui.fragments.DatePickerFragment;
import cz.tyckouni.poopio.R;
import cz.tyckouni.poopio.base.entities.Poop;
import cz.tyckouni.poopio.core.dao.PoopsDao;
import cz.tyckouni.poopio.core.dao.FBPoopsDaoImpl;
import cz.tyckouni.poopio.ui.fragments.TimePickerFragment;

public class CreatePoopActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, ColorPickerDialogListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "CreatePoopActivity";

    private String mPoopType = "";

    private ColorPanelView mPoopColorPanelView;
    private TextView mDateView;
    private TextView mTime;
    private SeekBar mSizeSeekBar;
    private SeekBar mConsistencySeekBar;
    private Button mSaveButton;

    private FirebaseUser mUser;
    private PoopsDao mPoopsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poop);

        Spinner typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.poop_types, R.layout.pooptype_spinner_item);

        if (typeSpinner != null) {
            typeSpinner.setOnItemSelectedListener(this);
            typeSpinner.setAdapter(adapter);
        }

        mPoopColorPanelView = findViewById(R.id.color_value);
        mDateView = findViewById(R.id.date_value);
        mTime = findViewById(R.id.time_value);
        mConsistencySeekBar = findViewById(R.id.consistency_value);
        mSizeSeekBar = findViewById(R.id.size_value);
        mSaveButton = findViewById(R.id.save_button);

        mPoopsDao = new FBPoopsDaoImpl();

        Calendar cal = Calendar.getInstance();

        setDate(cal);
        setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    @Override
    protected void onStart() {
        super.onStart();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mPoopColorPanelView.setColor(0xFF563b2c);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPoopType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mPoopType = parent.getItemAtPosition(0).toString();
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        mPoopColorPanelView.setColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {
        // do nothing
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

        setDate(cal);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    private void setTime(int hourOfDay, int minute) {
        mTime.setText(String.format(Locale.getDefault(), "%d:%d", hourOfDay, minute));
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        mDateView.setText(dateFormat.format(calendar.getTime()));
    }

    public void selectColor(View view) {
        ColorPickerDialog.newBuilder()
                .setPresets(getResources().getIntArray(R.array.defaultPoopColors))
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setColor(mPoopColorPanelView.getColor())
                .show(this);
    }

    public void pickDate(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    public void savePoop(View view) {
        mSaveButton.setEnabled(false);

        Poop poop = new Poop();
        poop.setColor(mPoopColorPanelView.getColor());
        poop.setType(mPoopType);
        poop.setConsistency(mConsistencySeekBar.getProgress());
        poop.setSize(mSizeSeekBar.getProgress());
        poop.setDate(mDateView.getText().toString());
        poop.setTime(mTime.getText().toString());

        mPoopsDao.create(poop, mUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(TAG, "onComplete: failed to create poop, reason: " +
                            databaseError.getMessage());
                    returnReply(RESULT_CANCELED);
                } else {
                    returnReply(RESULT_OK);
                }
            }
        });

        //TODO show loading
    }

    private void returnReply(int result) {
        Intent replyIntent = new Intent();

        setResult(result, replyIntent);
        finish();
    }

    public void cancel(View view) {
        returnReply(RESULT_CANCELED);
    }

    public void pickTime(View view) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.show(getFragmentManager(), "time");
    }
}
