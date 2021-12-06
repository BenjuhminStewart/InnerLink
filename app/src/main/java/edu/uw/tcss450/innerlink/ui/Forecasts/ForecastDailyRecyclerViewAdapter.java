package edu.uw.tcss450.innerlink.ui.Forecasts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentForecastCardBinding;

/**
 * Allows the user to scroll through the list of daily forecasts.
 */
public class ForecastDailyRecyclerViewAdapter extends RecyclerView.Adapter<ForecastDailyRecyclerViewAdapter.ForecastDailyViewHolder> {
    //Store all of the Forecasts to present
    private final List<Forecast> mForecasts;

    public ForecastDailyRecyclerViewAdapter(List<Forecast> forecasts) {
        mForecasts = forecasts;
    }

    @NonNull
    @Override
    public ForecastDailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastDailyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_forecast_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDailyViewHolder holder, int position) {
        holder.setForecast(mForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    /**
     * Objects from this class represent an Individual column View from the List
     * of rows in the Forecast Daily Recycler View.
     */
    public class ForecastDailyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentForecastCardBinding binding;
        private Forecast mForecast;

        public ForecastDailyViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentForecastCardBinding.bind(view);
        }

        void setForecast(final Forecast forecast) {
            mForecast = forecast;
            String[] date = forecast.getTime().split(" ");
            binding.textTime.setText(date[0]);

            String condition = forecast.getCondition();
            if (condition.equalsIgnoreCase("Clouds")) {
                binding.imageCondition.setImageResource(R.drawable.ic_cloud_24dp);
            } else if (condition.equalsIgnoreCase("Rain")) {
                binding.imageCondition.setImageResource(R.drawable.ic_rain_24dp);
            } else if (condition.equalsIgnoreCase("Snow")) {
                binding.imageCondition.setImageResource(R.drawable.ic_snow_24dp);
            } else {
                binding.imageCondition.setImageResource(R.drawable.ic_sunny_24dp);
            }

            binding.textTemperature.setText(forecast.getTemperature());
        }
    }
}
