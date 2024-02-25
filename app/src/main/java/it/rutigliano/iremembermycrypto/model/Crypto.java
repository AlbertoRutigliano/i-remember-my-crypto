package it.rutigliano.iremembermycrypto.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class Crypto {
    private String name;
    private Double price;
    private String symbol;
    private CryptoDetails details;
    private boolean isFavorite = false;
    private Double ownedValue;
    public String getLongName() {
        return String.format("%s (%s)", name, symbol);
    }
}
