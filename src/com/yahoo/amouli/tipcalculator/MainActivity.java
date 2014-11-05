package com.yahoo.amouli.tipcalculator;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private EditText et; // The widget that stores the amount
	private TextView tv; // The widget displaying the Tip
	private Button selectedBtn; // The button that is currently selected.
	double  tipPercent; // The percent to be used in computation.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Save a pointer to the view objects.
        et = (EditText) findViewById(R.id.etEntAmt);
        tv = (TextView) findViewById(R.id.txTipVal);
        tipPercent = 0.0;
        // Register a editor action listener
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Compute a new tip value and set it.
				setTipValue();  
				return true;
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void btnClicked(View v) {
    		
    	Button bt = (Button) v;
    	
    	// Set the background color on the button that was selected, and
    	// reset the background to default if a button is in selected state.
    	bt.setBackgroundColor(Color.BLUE);
    	if (selectedBtn != null) {
    		selectedBtn.setBackgroundResource(android.R.drawable.btn_default);
    	}
    	selectedBtn = bt;
    	
    	// Determine the button that was pressed. 
    	String btnText = bt.getText().toString();
    	if (btnText.equals(getString(R.string.btn_10_string))) {
    		tipPercent = 0.1;
    	}
    	else if (btnText.equals(getString(R.string.btn_15_string))) {
    		tipPercent = 0.15;
    	}
    	else if (btnText.equals(getString(R.string.btn_20_string))) {
    		tipPercent = 0.2;
    	}
    	
    	setTipValue();
    	
    }
    
    private void setTipValue() {
    	
    	// Read the total amount and convert it double.
    	String val = et.getText().toString();
    	Double d = 0.0;
    	try {
    		 d = Double.parseDouble(val);
    	}
    	catch (NumberFormatException e) {
    		
    	}
    	// Compute the tip.
    	Double tip = tipPercent * d;
    	
    	// Set the tip in the textview field.
    	String fmtString = String.format("$%6.2f", tip);
    	tv.setText(fmtString);
    	
    }
    	
   
}
