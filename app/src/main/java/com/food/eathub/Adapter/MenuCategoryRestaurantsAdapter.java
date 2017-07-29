package com.food.eathub.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.food.eathub.Activity.AddToBasketActivity;
import com.food.eathub.Activity.RestaurantCategoryActivity;
import com.food.eathub.Model.AllDishbycategoryModel;
import com.food.eathub.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MenuCategoryRestaurantsAdapter extends RecyclerView.Adapter<MenuCategoryRestaurantsAdapter.PersonViewHolder> {
	ArrayList<AllDishbycategoryModel> dishes;
	Context ctx;
	Typeface font;
	ImageLoader imageLoader;
	String catname;
	int restId;
	public MenuCategoryRestaurantsAdapter(ArrayList<AllDishbycategoryModel> dishes, Context ctx, String catname, int restId) {
		this.dishes = dishes;
		this.ctx = ctx;
		this.catname = catname;
		this.restId = restId;
		font = Typeface.createFromAsset(ctx.getAssets(), "fontawesome-webfont.ttf");
		imageLoader = ImageLoader.getInstance();

	}

	@Override
	public int getItemCount() {
		return dishes.size();
	}

	@Override
	public void onBindViewHolder(PersonViewHolder personViewHolder, final int i) {
		personViewHolder.resttvRestaurantRupeesIcon.setText("\uf156");
		personViewHolder.resttvRestaurantRupeesIcon.setTypeface(font);

		personViewHolder.restdishName.setText(dishes.get(i).getDishName());
		if (dishes.get(i).getDescription().isEmpty()) {
			personViewHolder.restdishDetail.setVisibility(View.GONE);
		} else {
			personViewHolder.restdishDetail.setText(dishes.get(i).getDescription());
		}

		personViewHolder.restdishamount.setText((float) dishes.get(i).getDishPrice() + " ");
		if (dishes.get(i).getCalories() != null) {
			personViewHolder.seeInfonutrition.setVisibility(View.VISIBLE);
			personViewHolder.seeInfonutrition.setText(dishes.get(i).getCalories() + " Calories");
		} else {
			personViewHolder.seeInfonutrition.setVisibility(View.GONE);
		}

		personViewHolder.resttvDishId.setText(String.valueOf(dishes.get(i).getDishId()));
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(dishes.get(i).getDishImage(), personViewHolder.restDishImage);
		RestaurantCategoryActivity act = (RestaurantCategoryActivity) ctx;
		personViewHolder.addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent ii = new Intent(ctx, AddToBasketActivity.class);
					ii.putExtra("dishes", dishes.get(i));
					ii.putExtra("catname", catname);
					ii.putExtra("restid", restId);
					ctx.startActivity(ii);
					((Activity) ctx).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rest_category_dishitem_layout, viewGroup, false);
		PersonViewHolder pvh = new PersonViewHolder(v);
		return pvh;
	}

	public class PersonViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		LinearLayout addbtn;
		ImageView restDishImage;
		TextView restdishName, restdishDetail, restdishamount, resttvRestaurantRupeesIcon, resttvDishId, seeInfonutrition;

		public PersonViewHolder(View arg0) {
			super(arg0);
			cv = (CardView) itemView.findViewById(R.id.cvRestaurantCategory);
			addbtn = (LinearLayout) itemView.findViewById(R.id.llDishAddButton);

			restdishName = (TextView) itemView.findViewById(R.id.tvDishName);
			restdishDetail = (TextView) itemView.findViewById(R.id.tvDishDesc);
			restdishamount = (TextView) itemView.findViewById(R.id.tvDishPrice);
			resttvRestaurantRupeesIcon = (TextView) itemView.findViewById(R.id.tvDishRupeeIcon);
			restDishImage = (ImageView) itemView.findViewById(R.id.ivDishImage);
			seeInfonutrition = (TextView) itemView.findViewById(R.id.seeInfonutrition);
			resttvRestaurantRupeesIcon = (TextView) itemView.findViewById(R.id.tvDishRupeeIcon);
			resttvDishId = (TextView) itemView.findViewById(R.id.tvDishId);
		}
	}
}
