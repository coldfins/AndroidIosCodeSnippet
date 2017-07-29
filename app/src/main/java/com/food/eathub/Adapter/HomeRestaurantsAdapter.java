package com.food.eathub.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.eathub.Model.GetRestaurantAndCuisineModel;
import com.food.eathub.Model.GradientOverImageDrawable;
import com.food.eathub.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HomeRestaurantsAdapter extends
		RecyclerView.Adapter<HomeRestaurantsAdapter.PersonViewHolder> {

	ArrayList<GetRestaurantAndCuisineModel> rests;
	Context ctx;
	Typeface font;
	ImageLoader imageLoader;
	String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday" };
	double lati, longi;

	public HomeRestaurantsAdapter(ArrayList<GetRestaurantAndCuisineModel> list,
			Context ctx, Double lati, Double longi) {
		this.rests = list;
		this.ctx = ctx;
		this.lati = lati;
		this.longi = longi;
		font = Typeface.createFromAsset(ctx.getAssets(),
				"fontawesome-webfont.ttf");
		imageLoader = ImageLoader.getInstance();

	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return rests.size();
	}

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
		personViewHolder.restName.setText(rests.get(i).getRestaurant().getRestaurantName());
		String cuisine = null;
		for (int j = 0; j < rests.get(i).getCuisines().size(); j++) {
			if (cuisine != null) {
				cuisine = cuisine + "," + rests.get(i).getCuisines().get(j).getCuisineName();
			} else {
				cuisine = rests.get(i).getCuisines().get(j).getCuisineName();
			}
		}

		personViewHolder.restdetail.setText(cuisine);
		personViewHolder.minorderfee.setText(rests.get(i).getRestaurant().getMinimumOrderAmount() + " Min. order");

		if (rests.get(i).getRestaurant().getDeliveryFee() == 0) {
			personViewHolder.deliveryfeeicon.setVisibility(View.GONE);
			personViewHolder.deliveryfeeamount.setText("Free delivery");
		} else {
			personViewHolder.deliveryfeeicon.setVisibility(View.VISIBLE);
			personViewHolder.deliveryfeeamount.setText(rests.get(i).getRestaurant().getMinimumOrderAmount() + " Delivery fee ");
		}

		if (rests.get(i).getRestaurant().getOnlinePaymentAvailable()) {
			personViewHolder.onlinepaymentavail.setText("Online payment available");
		} else {
			personViewHolder.onlinepaymentavail.setVisibility(View.GONE);
		}

		personViewHolder.restRating.setText(String.format("%.1f", rests.get(i).getRestaurant().getFavouriteRate()) + " Ratings");

		personViewHolder.rbRestaurantReview.setText(rests.get(i).getTotalReviews() + " Review");

		personViewHolder.resttvRestaurantId.setText(String.valueOf(rests.get(i).getRestaurant().getRestaurantId()));
		personViewHolder.tvRestaurantkm.setText(distance(lati, longi, rests.get(i).getRestaurant().getLatitude(), rests.get(i).getRestaurant().getLongitude()) + " Km.");
		personViewHolder.tvrestAddress.setText(rests.get(i).getRestaurant().getAddress());
		Calendar calendar1 = Calendar.getInstance();
		Calendar aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-0400"));
		String ss = calendar1.get(Calendar.YEAR) + "-" + (calendar1.get(Calendar.MONTH) + 1) + "-" + calendar1.get(Calendar.DAY_OF_MONTH) + " "
				+ calendar1.get(Calendar.HOUR_OF_DAY) + ":" + calendar1.get(Calendar.MINUTE) + ":" + calendar1.get(Calendar.SECOND);

		try {
			SimpleDateFormat current = new SimpleDateFormat("HH:mm:ss", Locale.US);
			Date currentTime = current.parse(current.format(calendar1.getTime()).toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startDate = format.parse(rests.get(i).getWorkingHours().get(calendar1.getTime().getDay()).getOpenTime());
			Date endDate = format.parse(rests.get(i).getWorkingHours().get(calendar1.getTime().getDay()).getCloseTime());

			if (currentTime.getHours() < startDate.getHours() || currentTime.getHours() > endDate.getHours()) {
				personViewHolder.tvRestOpenOrClosed.setText("Closed now");
			} else {
				personViewHolder.tvRestOpenOrClosed.setText("Open now");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DisplayImageOptions dio = new DisplayImageOptions.Builder().displayer(
			new BitmapDisplayer() {
				@Override
				public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
					int gradientStartColor = Color.argb(0, 0, 0, 0);
					int gradientEndColor = Color.argb(175, 0, 0, 0);
					GradientOverImageDrawable gradientDrawable = new GradientOverImageDrawable(
							ctx.getResources(), bitmap);
					gradientDrawable.setGradientColors(gradientStartColor,
							gradientEndColor);
					imageAware.setImageDrawable(gradientDrawable);
				}

			}).build();

		ImageLoader.getInstance().displayImage(rests.get(i).getRestaurant().getRestaurantImage(), personViewHolder.restimage, dio);
	}

	@Override
	public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurants_item_layout, viewGroup, false);
		PersonViewHolder pvh = new PersonViewHolder(v);
		return pvh;
	}

	public class PersonViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		ImageView restimage, deliveryfeeicon, minordericon;
		TextView restName, restdetail, tvRestaurantkm, resttvRestaurantId,
				deliveryfeeamount, minorderfee, onlinepaymentavail, rbRestaurantReview;
		TextView restRating, tvRestOpenOrClosed, tvrestAddress;

		public PersonViewHolder(View arg0) {
			super(arg0);
			cv = (CardView) itemView.findViewById(R.id.cvRestaurant);
			restName = (TextView) itemView.findViewById(R.id.tvRestaurantName);
			restdetail = (TextView) itemView.findViewById(R.id.tvRestaurantCuisineDetail);
			tvRestaurantkm = (TextView) itemView.findViewById(R.id.tvRestaurantkm);
			rbRestaurantReview = (TextView) itemView.findViewById(R.id.rbRestaurantReview);
			restimage = (ImageView) itemView.findViewById(R.id.ivRestaurantImage);
			restRating = (TextView) itemView.findViewById(R.id.rbRestaurantFavoriteRating);
			tvRestOpenOrClosed = (TextView) itemView.findViewById(R.id.tvRestOpenOrClosed);
			resttvRestaurantId = (TextView) itemView.findViewById(R.id.tvRestaurantId);
			tvrestAddress = (TextView) itemView.findViewById(R.id.tvrestAddress);
			deliveryfeeicon = (ImageView) itemView.findViewById(R.id.ivDeliveryFeeRupee);
			minordericon = (ImageView) itemView.findViewById(R.id.ivMinOrderRupee);
			deliveryfeeamount = (TextView) itemView.findViewById(R.id.tvDeliveryFeeAmount);
			minorderfee = (TextView) itemView.findViewById(R.id.tvMinOrderAmount);
			onlinepaymentavail = (TextView) itemView.findViewById(R.id.tvOnlinePaymentAvailable);
		}
	}

	public static String distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		String d = String.format("%.0f", Math.ceil(dist));
		return (d);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
