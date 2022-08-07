package edu.wit.mobileapp.fit_it_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    public class ListItem {
        public String shirt;
        public String sweatshirt;
        public String shorts;
        public String pants;
        public String shoes;
    }

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

        List<ListItem> list = new ArrayList<ListItem>();

        // recommend sizes here, get values through a bundle maybe ?
        ListItem item = new ListItem();
        item.shirt = "Medium";
//        item.sweatshirt = "Large";
//        item.shorts = "Small";
//        item.pants = "Small";
//        item.shoes = "Small";
        list.add(item);

        // create ListItemAdapter
        WebViewAdapter adapter = new WebViewAdapter(getContext(), 0, list);

        // assign ListItemAdapter to ListView
        ListView listView = (ListView) rootView.findViewById(R.id.ListView1);
        listView.setAdapter(adapter);

        return rootView;
    }
}
