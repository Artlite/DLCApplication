package com.artlite.pluginmanagerapi.helpers;

import android.util.Base64;
import android.util.Log;


import com.artlite.pluginmanagerapi.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class which provide the crypt functional
 */
public final class PSCryptHelper {

    /**
     * {@link String} constant of the TAG
     */
    private static final String TAG = PSCryptHelper.class.getSimpleName();

    /**
     * Instance of the {@link String}
     */
    private static final String K_ALGORITHM = "Blowfish";

    /**
     * Instance of the {@link String}
     */
    private static final String K_MODE = "Blowfish/CBC/PKCS5Padding";

    /**
     * Instance of the {@link String}
     */
    private static final String K_IV = "abcdefgh";

    /**
     * Method which provide to encrypt of the information
     *
     * @param value {@link String} value of the information which should be encrypted
     * @param key   {@link String} value of the key
     * @return {@link String} value of the encrypted information
     */
    @Nullable
    public static String encrypt(@Nullable String value,
                                 @Nullable String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), K_ALGORITHM);
            Cipher cipher = Cipher.getInstance(K_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,
                    new IvParameterSpec(K_IV.getBytes()));
            byte[] values = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(values, Base64.DEFAULT);
        } catch (Exception ex) {
            Log.e(TAG, "encrypt: ", ex);
        }
        return null;
    }

    /**
     * Method which provide to decrypt of the information
     *
     * @param value {@link String} value of the information which should be encrypted
     * @param key   {@link String} value of the key
     * @return {@link String} value of the encrypted information
     */
    public static String decrypt(@Nullable String value,
                                 @Nullable String key) {
        try {
            byte[] values = Base64.decode(value, Base64.DEFAULT);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), K_ALGORITHM);
            Cipher cipher = Cipher.getInstance(K_MODE);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(K_IV.getBytes()));
            return new String(cipher.doFinal(values));
        } catch (Exception ex) {
            Log.e(TAG, "decrypt: ", ex);
        }
        return null;
    }

}
