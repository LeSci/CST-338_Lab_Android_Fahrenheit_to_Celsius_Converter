package com.sscire.ctof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sscire.ctof.databinding.ActivityFtocBinding;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @author Shannon Scire
 * 18 November 2023
 * Dr. C
 * Lab : Android Fahrenheit to Celsius Converter
 *
 * This Android application takes a float number representing a temperature in Fahrenheit
 * and converts to Celsius.
 */
public class Ftoc_Activity extends AppCompatActivity {

    private static final String FTOC_ACTIVITY_TEMPERATURE = "com.sscire.ctof.FtocActivityTemperature";
    EditText mFtoC_editText;
    Button mFtoC_convert_button;
    TextView mFtoC_value_display;
    TextView mFtoC_label_textView;
    private static final String TAGfToC = "ftoc_Activity";

    ActivityFtocBinding mFtoCBinding;

    double celsius = 0.0;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    void convertCtoFValue(){
        String valueFromDisplay = mFtoC_editText.getText().toString();
        try{
            celsius = Double.parseDouble(valueFromDisplay);
        } catch(NumberFormatException e){
            Toast.makeText(this, "Could not convert "
                    + valueFromDisplay, Toast.LENGTH_SHORT);
            Log.d(TAGfToC + ": convertValue()","Could not convert" + valueFromDisplay);
        }
        celsius = Util.fToC(celsius);
        updateDisplay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftoc);
        mFtoCBinding = ActivityFtocBinding.inflate(getLayoutInflater());

        View view = mFtoCBinding.getRoot();
        setContentView(view);

        mFtoC_editText = mFtoCBinding.ftocEnteredValueEditText;
        mFtoC_convert_button = mFtoCBinding.ftocConvertButton;
        mFtoC_value_display = mFtoCBinding.ftocConvertedValueTextView;
        mFtoC_label_textView = mFtoCBinding.ftocLabelTextView;
        
        
        celsius = getIntent().getDoubleExtra(FTOC_ACTIVITY_TEMPERATURE, 0.0);
        
        //updateDisplay();
        // place celsius from MainActivity into FtoC input
        mFtoC_editText.setText(decimalFormat.format(celsius)+"");
        
        mFtoC_convert_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAGfToC, "*****onClick: Clicked Button!*****");
                convertCtoFValue();
                toastMessageFtoC("IT WORKED!");
            }
        });

        mFtoC_label_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.getIntent(getApplicationContext(), celsius);
                startActivity(intent);
            }
        });
    }

    private void updateDisplay() {
        String formattedCelsius= String.format(Locale.US, "%.2f", celsius) + "\u00B0F";
        //convertedValue.setText(Double.toString(fahrenheit));
        mFtoC_value_display.setText(formattedCelsius);
        //convertedValue.setText(fahrenheit + "");  // optional statement
    }


    private void toastMessageFtoC(String message){
        Toast.makeText(Ftoc_Activity.this, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * An intent factory used to start this (Ftoc_Activity) activity.
     * We pass in a context and a temperature that is used in the next activity
     * @param context the context for the current activity
     * @param temperature the current converted temperature
     * @return an intent that will allows us to start the Ftoc_Activity (this activity)
     */
    public static Intent getIntent(Context context, double temperature){
        Intent intent = new Intent(context, Ftoc_Activity.class);
        intent.putExtra(FTOC_ACTIVITY_TEMPERATURE, temperature);
        return intent;
    }
}