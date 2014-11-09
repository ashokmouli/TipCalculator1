package com.yahoo.amouli.tipcalculator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private EditText et; // The widget that stores the amount
	private TextView tv; // The widget displaying the Tip
	private EditText etSplitBy; // The widget containing the split by value.
	private EditText etTipPercent; // The widget containing custom percent value.
	private RadioGroup rgPercentGroup;
	private double  tipPercent; // The percent to be used in computation.
	private int splitBy; // Split the tip by this number.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Save a pointer to the view objects.
        et = (EditText) findViewById(R.id.etEntAmt);
        tv = (TextView) findViewById(R.id.txTipVal);
        etSplitBy = (EditText) findViewById(R.id.etSplitVal);
        etTipPercent = (EditText) findViewById(R.id.etTipPercent);
        rgPercentGroup = (RadioGroup) findViewById(R.id.btnGroup);
        tipPercent = 0.0;
        // Register a editor action listener for amount
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Compute a new tip value and set it.
				setTipValue();  
				return true;
			}
		});
        etTipPercent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Read the value from the widget and convert it to tip percent.
				String val = etTipPercent.getText().toString();
				int percentVal;
		    	try {
		    		 percentVal = Integer.parseInt(val);
		    		 tipPercent = percentVal/100.0;
		    	}
		    	catch (NumberFormatException e) {
		    		tipPercent = 1;
		    	}
		    	
		    	// Uncheck the radio button.
		    	rgPercentGroup.clearCheck();
		    	
				// Compute a new tip value and set it.
				setTipValue();
				return false;
			}
		});
        
        // Register an editor action listener for split by.
        etSplitBy.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// Read the value from the widget and convert it to Split value.
				String val = etSplitBy.getText().toString();
		    	try {
		    		 splitBy = Integer.parseInt(val);
		    	}
		    	catch (NumberFormatException e) {
		    		splitBy = 1;
		    	}
				// Compute a new tip value and set it.
				setTipValue();
				return false;
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
    
    private void readTipPercent() {
    	File fileDir = this.getFilesDir();
    	File tipCalcFile = new File(fileDir, "tipCalc.txt");
    	try {
    		ArrayList<String> items = new ArrayList<String>(FileUtils.readLines(tipCalcFile));
    		String percentVal = items.get(0);
        	etTipPercent.setText(percentVal);
    	}
    	catch (IOException e) {
    		etTipPercent.setText("");
    		
    	}
 
    }
    
    private void writeState() {
    	File fileDir = getFilesDir();
    	File tipCalcFile = new File(fileDir, "tipCalc.txt");
    	String percentVal = etTipPercent.getText().toString();
    	ArrayList<String> items = new ArrayList<String>();
    	items.add(percentVal);
    	try {
			FileUtils.writeLines(tipCalcFile, items);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    
    public void btnClicked(View v) {
    	
    	// This method is called when the radio button is clicked.
    		
    	RadioButton bt = (RadioButton) v;
    	
    	
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
    	// Computes the Tip and sets it in the text view.
    	
    	
    	// Read the total amount and convert it double.
    	String val = et.getText().toString();
    	Double d = 0.0;
    	try {
    		 d = Double.parseDouble(val);
    	}
    	catch (NumberFormatException e) {
    		
    	}
    	
    	// Read the split by value.
    	val = etSplitBy.getText().toString();
    	try {
    		splitBy = Integer.parseInt(val);
    	}
    	catch (NumberFormatException e) {
    		// Set splitBy to 1 if there is an error parsing.
    		splitBy = 1;
    	}
    	// Compute the tip.
    	Double tip = tipPercent * d / splitBy;
    	
    	// Set the tip in the textview field.
    	String fmtString = String.format("$%6.2f", tip);
    	tv.setText(fmtString);
    	
    }
    	
   
}
