package com.sscire.ctof;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.sscire.ctof.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @author Shannon Scire
 * 18 November 2023
 * Dr. C
 * Lab : Android Fahrenheit to Celsius Converter
 *
 * This Android application takes a float number representing a temperature in Celsius
 * and converts to Fahrenheit.
 */
public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_TEMPERATURE = "com.sscire.ctof.MainActivityTemperature";
    EditText mCtoF_editText;
    Button mCtoF_convert_button;
    TextView mCtoF_value_display;

    // TAGs are created for logging purposes
    // source: CodingWithMitch
    // https://www.youtube.com/watch?v=VkCeWHa4EH0
    private static final String TAG = "MainActivity";

    // Connects this java file to the XML file
    // optional name mMainBinding, lowercase "m" means member of a class, see JavaStyle Guide
    // ViewBinding is a binding class that represents activity_main.xml, to enable one
    // to write code that can interact with views; bind xml data to java data
    ActivityMainBinding mainBinding;
    double fahrenheit = 0.0;

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    // converts value between celsius and fahrenheit
    void convertFtoCValue(){
        String valueFromDisplay = mCtoF_editText.getText().toString();

        try{
            fahrenheit = Double.parseDouble(valueFromDisplay);
        } catch(NumberFormatException e){
            Toast.makeText(this, "Could not convert "
                    + valueFromDisplay, Toast.LENGTH_SHORT);
            Log.d(TAG + ": convertValue()","Could not convert" + valueFromDisplay);
        }

        fahrenheit = Util.cToF(fahrenheit);
        updateDisplay();
    }

    private void updateDisplay() {
        String formattedFahrenheit = String.format(Locale.US, "%.2f", fahrenheit) + "\u00B0F";
        //convertedValue.setText(Double.toString(fahrenheit));
        mCtoF_value_display.setText(formattedFahrenheit);
        //convertedValue.setText(fahrenheit + "");  // optional statement
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // wire-up java code to android widgets/xml
        // inflate takes XML file and converts it to something Java can use
        // we can write our own inflaters
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = mainBinding.getRoot();
        setContentView(view);

        mCtoF_editText =  mainBinding.ctofEnteredValueEditText;
        mCtoF_convert_button = mainBinding.ctofConvertButton;
        mCtoF_value_display = mainBinding.ctofConvertedValueTextView;

        fahrenheit = getIntent().getDoubleExtra(MAIN_ACTIVITY_TEMPERATURE, 0.0);
        //updateDisplay();
        // place celsius from MainActivity into FtoC input
        mCtoF_editText.setText(decimalFormat.format(fahrenheit)+"");

        // creating an anonymous inner-class and assigning as an OnClickListener object
        mCtoF_convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // this is an anonymous inner class
                Log.d(TAG, "*****ctof onClick: Clicked Button!*****");
                //Toast.makeText(-10MainActivity.this, "IT WORKED!", Toast.LENGTH_SHORT);
                convertFtoCValue();
                toastMessage("IT WORKED!");
            }
        });

        mCtoF_convert_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = Ftoc_Activity.getIntent(getApplicationContext(), fahrenheit);
                startActivity(intent);
                return false;
            }
        });

        Log.d(TAG, "*****onCreate: Stated*****");
    }
    private void toastMessage(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // Intent Factory, a way to easily get information about
    // how to switch from one activity to another
    /**
     * An intent factory used to start this (MainActivity) activity.
     * We pass in a context and a temperature that is used in the next activity.
     * @param context the context for the current activity
     * @param temperature the current converted temperature
     * @return an intent that will allows us to start the MainActivity (this activity)
     */
    public static Intent getIntent(Context context, double temperature){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_TEMPERATURE, temperature);
        return intent;
    }
}
