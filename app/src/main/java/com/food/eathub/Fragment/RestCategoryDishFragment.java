/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.food.eathub.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.food.eathub.Adapter.MenuCategoryRestaurantsAdapter;
import com.food.eathub.Model.AllMenuOfRestModel;
import com.food.eathub.R;

import java.util.ArrayList;

public class RestCategoryDishFragment extends Fragment {
	private int position;
	private static int catId;
	static RecyclerView rvdish;
	@SuppressWarnings("rawtypes")
	RecyclerView.Adapter rvadapter;
	View vv;
	RecyclerView.LayoutManager rvlmanager;
	int currentapiVersion;
	CardView cvDishNODataCard;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_categorydish_item, container, false);
		currentapiVersion = android.os.Build.VERSION.SDK_INT;

		vv = v;
		position = getArguments().getInt("position");
		catId = getArguments().getInt("catId");
		int restId = getArguments().getInt("restId");
		cvDishNODataCard = (CardView) v.findViewById(R.id.cvDishNODataCard);
		@SuppressWarnings("unchecked")
		ArrayList<AllMenuOfRestModel> menu = (ArrayList<AllMenuOfRestModel>) getArguments().getSerializable("menu");
		rvdish = (RecyclerView) v.findViewById(R.id.rvRestaurantsDishBycategory);
		rvdish.setHasFixedSize(true);
		rvlmanager = new LinearLayoutManager(v.getContext());
		rvdish.setLayoutManager(rvlmanager);

		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).getCategory().getMenuCategoryId().equals(catId)) {
				if (menu.get(i).getDishes().isEmpty()) {
					cvDishNODataCard.setVisibility(View.VISIBLE);
					rvdish.setVisibility(View.GONE);
				} else {
					cvDishNODataCard.setVisibility(View.GONE);
					rvdish.setVisibility(View.VISIBLE);
					rvadapter = new MenuCategoryRestaurantsAdapter(menu.get(i).getDishes(), v.getContext(), menu.get(i).getCategory().getMenuCategoryName(), restId);
					rvdish.setAdapter(rvadapter);
				}
			}
		}
		return v;
	}

}