package it.rutigliano.iremembermycrypto;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.rutigliano.iremembermycrypto.model.Crypto;


public class CryptoWalletAdapter extends RecyclerView.Adapter<CryptoWalletAdapter.ViewHolder> {
    private final ArrayList<Crypto> cryptos;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView price;
        private final TextView symbol;
        private final ImageView image;
        private final ImageView star;
        private final TextView priceChange24h;
        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.crypto_name);
            price = view.findViewById(R.id.crypto_price);
            symbol = view.findViewById(R.id.crypto_symbol);
            image = view.findViewById(R.id.crypto_image);
            priceChange24h = view.findViewById(R.id.crypto_price_change_24h);
            star = view.findViewById(R.id.crypto_star);

        }

        public TextView getName() {
            return name;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getSymbol() {
            return symbol;
        }

        public ImageView getImage() {
            return image;
        }
        public ImageView getStar() {
            return star;
        }

        public TextView getPriceChange24h() {
            return priceChange24h;
        }
    }

    public CryptoWalletAdapter(ArrayList<Crypto> cryptos) {
        this.cryptos = cryptos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.crypto_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Crypto crypto = cryptos.get(position);
        if (crypto != null) {
            viewHolder.getName().setText(crypto.getName());
            viewHolder.getSymbol().setText(crypto.getSymbol());
            viewHolder.getPrice().setText(viewHolder.itemView.getContext().getString(R.string.price_format, crypto.getPrice() * crypto.getOwnedValue()));
            viewHolder.getStar().setBackgroundResource(crypto.isFavorite() ? R.drawable.icon_star : R.drawable.icon_star_border);
            if (crypto.getDetails() != null) {
                if (crypto.getDetails().getPriceChange24h() != null) {
                    viewHolder.getPriceChange24h().setText(viewHolder.itemView.getContext().getString(R.string.price_change_format, crypto.getDetails().getPriceChange24h()));
                    switch (crypto.getDetails().getPriceChangeState24h()) {
                        case UP:
                            viewHolder.getPriceChange24h().setTextColor(Color.GREEN);
                            break;
                        case DOWN:
                            viewHolder.getPriceChange24h().setTextColor(Color.RED);
                            break;
                        default:
                            viewHolder.getPriceChange24h().setTextColor(Color.GRAY);
                            break;
                    }
                }
                if (crypto.getDetails().getImage() != null) {
                    Picasso.get().load(crypto.getDetails().getImage()).into(viewHolder.getImage());
                }
            }

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cryptos.size();
    }
}

