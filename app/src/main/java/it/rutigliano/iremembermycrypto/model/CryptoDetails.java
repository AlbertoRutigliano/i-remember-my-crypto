package it.rutigliano.iremembermycrypto.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CryptoDetails {
    private String Image;
    private Double PriceChange24h;

    public enum PriceChange24State {
        UP,
        DOWN,
        EQUAL
    }
    public PriceChange24State getPriceChangeState24h() {
        double roundedPriceChange24h = (double) Math.round(PriceChange24h * 100) / 100;
        if (roundedPriceChange24h > 0.0) return PriceChange24State.UP;
        if (roundedPriceChange24h < 0.0) return PriceChange24State.DOWN;
        return PriceChange24State.EQUAL;
    }
}
