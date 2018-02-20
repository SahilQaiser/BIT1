package com.sq.bit.bit1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PdfViewer extends Activity{
	private WebView webView;
	

//	inside this goes our pdf viewer, just a toy for this test. Requires  more work to make it production ready
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pdf_papers);
		 
		webView = (WebView) findViewById(R.id.webView1);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);

		//The default value is true for API level android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 and below, 
		//and false for API level android.os.Build.VERSION_CODES.JELLY_BEAN and above.
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
	        settings.setAllowUniversalAccessFromFileURLs(true);
		
		settings.setBuiltInZoomControls(true);
		webView.setWebChromeClient(new WebChromeClient());
		loadPage();
		
	}
	private void loadPage(){
		Intent i = getIntent();
		int semester = i.getIntExtra(bit.EXTRA_FILE, 3);
		System.out.println(semester);
		switch(semester){
			case 1:
				webView.loadUrl("file:///android_asset/pdfviewer/index.html");
				break;
			case 2:
				webView.loadUrl("file:///android_asset/pdfviewer/index2.html");
				break;
			case 3:
				webView.loadUrl("file:///android_asset/pdfviewer/index3.html");
				break;
			case 4:
				webView.loadUrl("file:///android_asset/pdfviewer/index4.html");
				break;
			case 5:
				webView.loadUrl("file:///android_asset/pdfviewer/index5.html");
				break;
			case 6:
				webView.loadUrl("file:///android_asset/pdfviewer/index6.html");
				break;
		}
	}

//	reload on resume
	@Override
	protected void onResume() {
		super.onResume();
		webView.loadUrl( "javascript:window.location.reload( true )" );

	}
	
//	clear cache to ensure we have good reload
	@Override
	protected void onPause() {
		super.onPause();
		webView.clearCache(true);

	}
	

}
