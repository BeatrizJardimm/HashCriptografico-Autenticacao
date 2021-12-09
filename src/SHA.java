package ItemTres;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

    private MessageDigest algoritmo;

    private SHA() {
        try {
            algoritmo = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static SHA hash = null;

    public synchronized static SHA getInstance(){
        if(hash == null )
            hash = new SHA();
        return hash;
    }

    public String toString(String entrada) {
        algoritmo.update(entrada.getBytes());
        byte[] bytes = algoritmo.digest();
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) s.append('0');
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
}
