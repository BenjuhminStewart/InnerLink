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
 * Allows the user to scroll through the list of hourly forecasts for a location.
 */
public class ForecastHourlyRecyclerViewAdapter extends RecyclerView.Adapter<ForecastHourlyRecyclerViewAdapter.ForecastHourlyViewHolder> {
    //Store all of the Forecasts to present
    private final List<Forecast> mForecasts;

    public ForecastHourlyRecyclerViewAdapter(List<Forecast> forecasts) {
        mForecasts = forecasts;
    }

    @NonNull
    @Override
    public ForecastHourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastHourlyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_forecast_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastHourlyViewHolder holder, int position) {
        holder.setForecast(mForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    /**
     * Objects from this class represent an Individual column View from the List
     * of rows in the Forecast Hourly Recycler View.
     */
    public class ForecastHourlyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentForecastCardBinding binding;
        private Forecast mForecast;

        public ForecastHourlyViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentForecastCardBinding.bind(view);
        }

        void setForecast(final Forecast forecast) {
            mForecast = forecast;
            binding.textTime.setText(forecast.getTime());

            String condition = forecast.getCondition();

            if (condition.equalsIgnoreCase("Clouds")) {
                binding.imageCondition.setImageResource(R.drawable.ic_cloudy);
            } else if (condition.equalsIgnoreCase("Rain")) {
                binding.imageCondition.setImageResource(R.drawable.ic_rainy);
            } else if (condition.equalsIgnoreCase("Snow")) {
                binding.imageCondition.setImageResource(R.drawable.ic_snowy);
            } else if (condition.equalsIgnoreCase("Clear")) {
                binding.imageCondition.setImageResource(R.drawable.ic_sunny);
            } else if (condition.equalsIgnoreCase("Thunderstorm")) {
                binding.imageCondition.setImageResource(R.drawable.ic_thunder);
            } else if (condition.equalsIgnoreCase("Drizzle")) {
                binding.imageCondition.setImageResource(R.drawable.ic_drizzle);
            } else {
                binding.imageCondition.setImageResource(R.drawable.ic_misty);
            }

            binding.textTemperature.setText(forecast.getTemperature());
        }
    }
}
