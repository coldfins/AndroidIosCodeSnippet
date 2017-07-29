package com.medical.adapter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements
		Filterable {
	private static final String LOG_TAG = "Google Places Autocomplete";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";

	private static final String OUT_JSON = "/json";
	// AIzaSyBTBBGPjqhYQ07WHOKiq06KWMT-vOST-oE usethis

	private static final String API_KEY = "AIzaSyBTBBGPjqhYQ07WHOKiq06KWMT-vOST-oE";

	// AIzaSyCSLKlI8-g3CeT9ccm1o9wTd5ViLOcsATA
	private ArrayList resultList;

	public GooglePlacesAutocompleteAdapter(Context context,
			int textViewResourceId) {

		super(context, textViewResourceId);
		// Toast.makeText(getActivity(), "Adapter", Toast.LENGTH_SHORT).show();

	}

	public int getCount() {

		return resultList.size();

	}

	public String getItem(int index) {

		return (String) resultList.get(index);

	}

	public Filter getFilter() {

		Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				FilterResults filterResults = new FilterResults();

				if (constraint != null) {

					// Retrieve the autocomplete results.

					resultList = autocomplete(constraint.toString());
					// Toast.makeText(getActivity(), constraint.toString(),
					// Toast.LENGTH_SHORT).show();

					// Assign the data to the FilterResults

					filterResults.values = resultList;

					filterResults.count = resultList.size();

				}

				return filterResults;

			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				if (results != null && results.count > 0) {

					notifyDataSetChanged();

				} else {

					notifyDataSetInvalidated();

				}

			}

		};

		return filter;

	}

	public static ArrayList autocomplete(String input) {
		ArrayList resultList = null;
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();

		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?key=" + API_KEY);
			sb.append("&vicinity=(cities)");
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];

			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}

		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
			resultList = new ArrayList(predsJsonArray.length());

			for (int i = 0; i < predsJsonArray.length(); i++) {
				System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
				System.out.println("============================================================");
				resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}
		return resultList;
	}

}
