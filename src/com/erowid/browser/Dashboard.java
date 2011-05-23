package com.erowid.browser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Dashboard extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dashboard);
        
        
        Spinner quickPick = (Spinner)this.findViewById(R.id.input_quickpick);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.quicknames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        quickPick.setAdapter(adapter);
        
        quickPick.setOnItemSelectedListener(new QuickPickSelectedListener());
        
        OnEditorActionListener oeal = new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				Dashboard.this.searchClick(arg0);
				return true;
			}
		};
		((TextView)findViewById(R.id.input_search)).setOnEditorActionListener(oeal);
		
    }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		 Spinner quickPick = (Spinner)this.findViewById(R.id.input_quickpick);
		 quickPick.setSelection(0);
		super.onResume();
	}

	public void searchClick(View target) {
    	// Execute a search with the search browser
    	EditText _search = (EditText)this.findViewById(R.id.input_search);
    	if(_search.getText() == null || _search.getText().equals("")){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setTitle(R.string.error_no_search_title);
    		builder.setMessage(R.string.error_no_search_text);
    		builder.setNeutralButton(R.string.ok, null);
    		builder.show();
    		return;
    	}
    	Intent i = new Intent(Dashboard.this,ContentBrowser.class);
    	i.putExtra(ContentBrowser.kUrl, 
    			String.format(getString(R.string.search_url_template), _search.getText()));
    	startActivity(i);
    }
    public class QuickPickSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
        	String url = parent.getResources().getStringArray(R.array.quickurls)[pos];
        	if(url == null || url.equals(""))
        		return;
        	
        	Intent i = new Intent(Dashboard.this,ContentBrowser.class);
        	i.putExtra(ContentBrowser.kUrl, url);
        	startActivity(i);
        }

        public void onNothingSelected(AdapterView<?> parent) {
          // Do nothing.
        }
    }
}