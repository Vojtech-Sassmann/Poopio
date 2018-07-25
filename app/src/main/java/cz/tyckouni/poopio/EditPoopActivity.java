package cz.tyckouni.poopio;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPanelView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditPoopActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, ColorPickerDialogListener, DatePickerDialog.OnDateSetListener {

    private String mTypeSpinnerLabel = "";
    private int mPoopColor = Color.parseColor("#411035");

    private ColorPanelView mPoopColorPanelView;
    private TextView mDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poop);

        Spinner typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.poop_types, android.R.layout.simple_spinner_item);

        if (typeSpinner != null) {
            typeSpinner.setOnItemSelectedListener(this);
            typeSpinner.setAdapter(adapter);
        }

        mPoopColorPanelView = findViewById(R.id.color_value);
        mPoopColorPanelView.setColor(R.color.defaultPoopColor);

        mDateView = findViewById(R.id.date_value);

        setDate(Calendar.getInstance());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mTypeSpinnerLabel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mTypeSpinnerLabel = parent.getItemAtPosition(0).toString();
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        Toast.makeText(this, "Selected Color: #" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
        mPoopColorPanelView.setColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        mDateView.setText(dateFormat.format(calendar.getTime()));

    }

    public void selectColor(View view) {
        ColorPickerDialog.newBuilder()
                .setShowAlphaSlider(true)
                .setPresets(getResources().getIntArray(R.array.defaultPoopColors))
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setColor(mPoopColorPanelView.getColor())
                .show(this);
    }

    public void pickDate(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }
}
