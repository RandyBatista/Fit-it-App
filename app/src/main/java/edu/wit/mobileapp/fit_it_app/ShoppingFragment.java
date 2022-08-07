package edu.wit.mobileapp.fit_it_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class ShoppingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.webview_fragment, container, false);
        Bundle bundle = this.getArguments();
        String url = "";
        String name = "";
        if (bundle != null) {
            url = bundle.getString("URL", "");
            name = bundle.getString("NAME", "");
        }

        WebView webview = rootView.findViewById(R.id.webView);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(url);
        return rootView;
    }
}
