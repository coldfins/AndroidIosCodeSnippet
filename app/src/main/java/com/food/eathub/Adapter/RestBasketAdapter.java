package com.food.eathub.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.food.eathub.Activity.AddToBasketActivity;
import com.food.eathub.Model.BasketModel;
import com.food.eathub.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class RestBasketAdapter extends RecyclerView.Adapter<RestBasketAdapter.PersonViewHolder> {

	ArrayList<BasketModel> bds;
	Context ctx;
	Typeface font;
	ImageLoader imageLoader;
	int totalAmount;

	public RestBasketAdapter(ArrayList<BasketModel> basketdetails, Context ctx) {
		this.bds = basketdetails;
		this.ctx = ctx;
		font = Typeface.createFromAsset(ctx.getAssets(), "fontawesome-webfont.ttf");
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getItemCount() {
		return bds.size();
	}

	@Override
	public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {
		personViewHolder.resttvRestaurantRupeesIcon.setText("\uf156");
		personViewHolder.resttvRestaurantRupeesIcon.setTypeface(font);
		personViewHolder.restdishcatname.setText(bds.get(i).getCatname());
		personViewHolder.restdishname.setText(bds.get(i).getDishes().getDishName());
		personViewHolder.restdishdesc.setText(bds.get(i).getDishes().getDescription());
		final String vall = bds.get(i).getDishes().getDishPrice().toString();
		personViewHolder.tvTotalAmount.setText(vall);
		totalAmount += Integer.parseInt(vall);
		final AddToBasketActivity act = (AddToBasketActivity) ctx;
		act.settextintextview(String.valueOf(totalAmount), Integer.parseInt(personViewHolder.baskettotalval.getText().toString()));
		personViewHolder.basketminus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Integer.parseInt(personViewHolder.baskettotalval.getText().toString()) != 1) {
					if (Integer.parseInt(personViewHolder.baskettotalval.getText().toString()) < 11) {
						int val = Integer.parseInt(personViewHolder.baskettotalval.getText().toString());
						val = val - 1;
						totalAmount -= Integer.parseInt(vall);
						personViewHolder.baskettotalval.setText("0" + val);
						act.settextintextview(String.valueOf(totalAmount), val);
					} else {
						int val = Integer.parseInt(personViewHolder.baskettotalval.getText().toString());
						val = val - 1;
						totalAmount -= Integer.parseInt(vall);
						personViewHolder.baskettotalval.setText(String.valueOf(val));
						act.settextintextview(String.valueOf(totalAmount), val);
					}
				}
			}
		});
		personViewHolder.basketplus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Integer.parseInt(personViewHolder.baskettotalval.getText().toString()) < 9) {
					int val = Integer.parseInt(personViewHolder.baskettotalval.getText().toString());
					val = val + 1;
					totalAmount += Integer.parseInt(vall);
					personViewHolder.baskettotalval.setText("0" + val);
					act.settextintextview(String.valueOf(totalAmount), val);
				} else {
					int val = Integer.parseInt(personViewHolder.baskettotalval.getText().toString());
					val = val + 1;
					totalAmount += Integer.parseInt(vall);
					personViewHolder.baskettotalval.setText(String.valueOf(val));
					act.settextintextview(String.valueOf(totalAmount), val);
				}
			}
		});
	}

	@Override
	public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rest_basket_itemlayout, viewGroup, false);
		PersonViewHolder pvh = new PersonViewHolder(v);
		return pvh;
	}

	public class PersonViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		TextView basketminus, baskettotalval, basketplus, tvTotalAmount;
		TextView restdishcatname, restdishname, restdishdesc, resttvRestaurantRupeesIcon;

		public PersonViewHolder(View arg0) {
			super(arg0);

			cv = (CardView) itemView.findViewById(R.id.cvRestaurantBasket);
			resttvRestaurantRupeesIcon = (TextView) itemView.findViewById(R.id.tvBasketRupeeIcon);
			restdishcatname = (TextView) itemView.findViewById(R.id.tvDishBasketCategoryName);
			restdishname = (TextView) itemView.findViewById(R.id.tvDishBasketDishName);
			restdishdesc = (TextView) itemView.findViewById(R.id.tvDishBasketDishDesc);

			basketminus = (TextView) itemView.findViewById(R.id.tvminusIcon);
			basketplus = (TextView) itemView.findViewById(R.id.tvPlusIcon);
			baskettotalval = (TextView) itemView.findViewById(R.id.tvTotalVal);
			tvTotalAmount = (TextView) itemView.findViewById(R.id.tvTotalAmount);
		}
	}

}
