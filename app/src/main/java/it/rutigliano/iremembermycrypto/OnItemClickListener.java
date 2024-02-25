package it.rutigliano.iremembermycrypto;

import it.rutigliano.iremembermycrypto.model.Crypto;

public interface OnItemClickListener {
    void onItemClick(Crypto crypto, int position);
}