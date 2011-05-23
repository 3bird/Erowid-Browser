package com.erowid.browser;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
		_browser.setWebViewClient(new SecureWebViewClient());
		_browser.loadUrl(extras.getString(kUrl));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// Clear any cache
		this.getApplicationContext().deleteDatabase("webview.db");
		this.getApplicationContext().deleteDatabase("webviewCache.db");

		deleteRecursive(getCacheDir());
		
		super.onPause();
	}

	private void deleteRecursive(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File temp = new File(dir, children[i]);
				if (temp.isDirectory()) {
					deleteRecursive(temp);
				} else {
					boolean b = temp.delete();
					if (b == false) {
						Log.d("DeleteRecursive", "DELETE FAIL");
					}
				}
			}

			dir.delete();
		}
	}

	private class SecureWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			_browser.loadUrl(url);
			return true;
		}
	}
}