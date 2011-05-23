package com.erowid.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

public class ContentBrowser extends Activity {
	public final static String kUrl = "urlToLoad";
	private WebView _browser;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.browser);
        

		_browser = (WebView) findViewById(R.id.content_view);
        
		final Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		_browser.loadUrl(extras.getString(kUrl));
    }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// Clear any cache
		this.getApplicationContext().deleteDatabase("webview.db");
		this.getApplicationContext().deleteDatabase("webviewCache.db");
		super.onPause();
	}
}