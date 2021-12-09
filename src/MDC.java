package ItemTres;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MDC {

    private MessageDigest algoritmo;

    private MDC() {
        try {
            algoritmo = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static MDC hash = null;

    public synchronized static MDC getInstance(){
        if(hash == null )
            hash = new MDC();
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
