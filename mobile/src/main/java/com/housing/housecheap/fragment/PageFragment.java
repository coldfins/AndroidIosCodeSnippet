package com.housing.housecheap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.housing.housecheap.utility.GetUrl;
import com.housing.housecheap.R;

import java.util.ArrayList;

import com.housing.housecheap.adapter.PageFragmentWishList;
import com.housing.housecheap.model.PropertiesList;

public class PageFragment extends Fragment {
	public static final String ARG_PAGE = "ARG_PAGE";
	public static RecyclerView wishRecyclerView;
	@SuppressWarnings("rawtypes")
	public static RecyclerView.Adapter wishPropertyAdapter;
	public static ArrayList<PropertiesList> propertyList1 = new ArrayList<PropertiesList>();
	GetUrl url = new GetUrl();
	RecyclerView.LayoutManager propertyLayoutManager;
	String title;
	int pos;
	public static int getFlag = 0;
	LinearLayout lvWishProperty;

	public static ArrayList<PropertiesList> propertyList = new ArrayList<PropertiesList>();

	public PageFragment() {

	}

//	public static PageFragment newInstance(int page) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_PAGE, page);
//		PageFragment fragment = new PageFragment();
//		fragment.setArguments(args);
//		// Toast.makeText(ctx, page+" ", Toast.LENGTH_SHORT).show();
//		return fragment;
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pagefragment_view, container, false);
		setView(view);
		return view;
	}

	@SuppressWarnings("unchecked")
	public void setView(View rootView) {
		pos = getArguments().getInt("position");
		lvWishProperty = (LinearLayout) rootView.findViewById(R.id.lvWishhProperty);
		propertyList = (ArrayList<PropertiesList>) getArguments().getSerializable("propertyy");
		wishRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvWishProjects);

		propertyLayoutManager = new LinearLayoutManager(getActivity());
		wishRecyclerView.setHasFixedSize(true);
		wishRecyclerView.setLayoutManager(propertyLayoutManager);

		setWishListData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onResume() {
		super.onResume();

	}

	public void setWishListData() {
		if (pos == 0) {
			propertyList1.clear();
			propertyList1.addAll(propertyList);

			if (propertyList1.size() == 0) {
				lvWishProperty.setVisibility(View.VISIBLE);

			} else {
				lvWishProperty.setVisibility(View.GONE);
				wishPropertyAdapter = new PageFragmentWishList(getActivity(), propertyList1);
				wishRecyclerView.setAdapter(wishPropertyAdapter);
				for (int i=0;i<propertyList1.size();i++)
				{
					Log.d("Property",propertyList1.get(i).getPropertyFor());
				}
				Log.d(pos+" pos", propertyList1.size() + "");
			}

		} else if (pos == 1) {
			propertyList1.clear();
			for (int i = 0; i < propertyList.size(); i++) {
				if ("sell".equals(propertyList.get(i).getPropertyFor())) {
					propertyList1.add(propertyList.get(i));
				}
			}
			if (propertyList1.size() == 0) {
				lvWishProperty.setVisibility(View.VISIBLE);
			} else {
				wishPropertyAdapter = new PageFragmentWishList(getActivity(), propertyList1);
				wishRecyclerView.setAdapter(wishPropertyAdapter);
			}

		} else if (pos == 2) {
			propertyList1.clear();
			for (int i = 0; i < propertyList.size(); i++) {
				if ("rent".equals(propertyList.get(i).getPropertyFor())) {
					propertyList1.add(propertyList.get(i));
				}
			}

			if (propertyList1.size() == 0) {
				lvWishProperty.setVisibility(View.VISIBLE);
			} else {
				wishPropertyAdapter = new PageFragmentWishList(getActivity(), propertyList1);
				wishRecyclerView.setAdapter(wishPropertyAdapter);
			}
		} else if (pos == 3) {
			propertyList1.clear();
			for (int i = 0; i < propertyList.size(); i++) {
				if ("pg".equals(propertyList.get(i).getPropertyFor())) {
					propertyList1.add(propertyList.get(i));
				}
			}

			if (propertyList1.size() == 0) {
				lvWishProperty.setVisibility(View.VISIBLE);

			} else {
				wishPropertyAdapter = new PageFragmentWishList(getActivity(), propertyList1);
				wishRecyclerView.setAdapter(wishPropertyAdapter);
				Log.d(pos+" pos", propertyList1.size() + "");
			}
		}

	}

}
