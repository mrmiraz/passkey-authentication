package com.example.passkey_authentication.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class RSAEncryption {
    public static String encryptionWithPublicKey(String text, String publicKeyPem) throws Exception {
        String publicKeyPEM = publicKeyPem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");  // Remove all whitespace

        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryption(String encryptedText, String privateKeyPem) throws Exception {
        // Remove the header and footer from the PEM format
        String privateKeyPEM = privateKeyPem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");  // Remove all whitespace

        // Decode the Base64 encoded private key
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM);

        // Generate the private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // Decode the base64 encoded ciphertext
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        // Decrypt the ciphertext
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Return the decrypted text
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        String publicKeyPem = """
                -----BEGIN PUBLIC KEY-----
                MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz1UYFJ3MmwXBVLE40wI9
                Ys4iNwVdSdShDW8EMhIk76JduI9iU3T2LzY+StZC3C1NdiuKLd+fgHxtZgeLIIBO
                vBDiOZyc1i59DhDgXpIVFZhdFeEohchL/XYQJumfTFlm44zf1C6Q9WXtlM2JDCDx
                tAyA3KCNSnDlEKN7Q+pqnlY4Dq+DE4x+338Kj92fISUUKWmp7gjYVpXCbY7mqRwk
                6tNWEc+3d9qFnpAm4rxFUUzoxsFHFZR95c20sp7KNnFCrsPFAdYZOIaHR5+a7ku+
                dmHWceqRt2Uu0zQiKUN0g5ZALkZbM6Pl9ZvmTqhUX/xBfrb51f64PzVC49YRDNkG
                CwIDAQAB
                -----END PUBLIC KEY-----
                """;
        String privateKeyPem = """
                -----BEGIN PRIVATE KEY-----
                MIIEowIBAAKCAQEAp0z1YgRQqpYJA4mRDb9IR7fEe9hmgji6c+KucEzcq8ndn5Wy
                ZPLhStwEjZCQef1s/YC5tixofynIeCkHOxN8GKQTrymQJyIEwnHzA+8YTLpWROGo
                sblOiJZm9ANTqR+Xz95VvO1DFxo1LywlV8uGjcpAgFVHWrFnTn1A+Vk3kn9csSqR
                R3rbpHvlTHVH6Vv2LmU6ZeSzhc1TC4EAZGWMDngzB7oxHN+nGgvuziyEE9c2/z5Z
                rl3N/vr25mAEWuDt0jai+cO2eo0uiC6TGGHxMVRCMx8IadvAnZYpNlNn1mpu2Fjd
                UCxfbT6tkdfRE/e7q9IqCXiqOPPdphyyUiW/bwIDAQABAoIBACA/HSz/V+0JbkNY
                SLU1oWDxctXMKDYBaS7dCnJq3j3ecvFHkVMT3YjoT5hZvP9GcbOLFECg3qNkElfW
                0KX6Y3j8Zy7j+/0O+YvhGs4vT6e6YtZ0CGf5J8H7+2wdGAsJI+HVIQGBWeUiEA4/n
                BMID69UxIn35gD9IMRoMBXftG5v/k4+tsWgMrO3IGHc9WhbVuslLsu5G+Xd41Qvd
                C0r5t37isLtPzG/vxjnhFTYE/IpX8da6Fc4xIwNK8Q+RAFuLnZn7dgji8BZwJNq/R
                SEPgt+OJIrUZp/51/JvN4VMj1wQXf0kWINuWXdjRuC/4dKkUDdIeEDACAkBsPQXP
                +zE0ECgYEAzwG9MlRLIuudk6thJ8SAtD9TDLOm4BYmuTiL8K90PjsZg/4dv8GTQS
                cn7sSWxj+NqvAbIFRmlu7K/qJpk+5q+YZKGnaxeftzdDaagnWaBHcQPdoZwndhXL
                Hm7FwhTr+Tebu0JApp0VPP2UfO1Pi6EHv8ACtFH4YV3QSbBhZpH20CgYEAzuV56k
                rBoUvho/hbegRqbmM7zli/srUBXkf1DdRk0cJ1sNobghN/SgyycezMhlYLQCshm+
                HOcdFot989cjaXB9Iyz4XMmsamRiFaqV7SksCAkgolyNQhLpY4dzjWKjisgImiGk
                DAiiKyDVYxSwQtHuBtNhCvtW/S//xQ2xZIpMsCgYEAkqoXwy3n4kDvby2wcz2NNq
                fbDpfGduQa4YZH1+pnwKOcy2Di8PaUf0ffyhKtBJ8In4DopzpFSVC8sDaf+PimAE
                59+Ev6KIPYZBE/3Yi268zzy+7X62krqUI+a15HFwUy1r//vL1IBdCM5dQznb7wDT
                fa7r+5hME6glJ+B40fQ+0CgYBBlq3XgGtx3sRhXtJhFBVJeVv0Rl4/IPKWGOmuwK
                5VcPguYHZn/hZUO0I2q4MK8ywN45579FGl82TnJXYtRvEKIbDJ6Pi7+E2t3dUH7G
                +E5lHb+iuQRu9d/XFZGkOKjvzgosEo4W4oXGdlovVtep1zNGJqyU9qjVCupiZbze
                ypkQKBgEssFP8jRMe/lW2msp7FFIkII0jKWfG71xmstQemZekBKrsgYo6qTrDDnt
                Its7Z//IIwybX/hMV8Pm+MVYUoFpB9P86wzU0YaNLEioaK8tA89kXCH4h/1fOcb9
                /e5jcAi02iPBhhqiyBaOdaMniXS6eSvqhIz+d9DeHIsK3MwGFT
                -----END PRIVATE KEY-----
                """;

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("signature", "BSrcT0zZm1x9ZAKo1Pxd1mg3kS1O/LV4xJib2ocgX5CUro168PjLjf4309nBSvvVS1XqQ2+DRz/9quMg81fCxbMGCNvGAw8y/Uuc7AY/2wFp8jndlQ5EcR/MPo/ls3uO/XUdQsrfahLtArkBS9TBA6PTDckQXR1AnUfCfVrbF6c6gI+RwtdmDcOB5kx9CwOt9rS+Le0o8T2B6Ri07MClMbtDtQfslkn6aNQ1yecZ/YJXq3Q6AT7TOnFrczWw9j7oIKCK/43agRGQNwZPx4w8PpylngEDZvUMQxdpPx1bLlRg7DaiqsbbzHXdary1FwvMGqhGCjZ8GvvW2gFI4iQPrg==");
        requestBody.put("secretKey", "SEU2MDB");
        String text = "MDB2024093011400521461";
        try {
            String encryptedData = encryptionWithPublicKey(text, publicKeyPem);
            requestBody.put("encrpAuthData", encryptedData);
            String url = "https://apitest.midlandbankbd.net/feescollection/fc/gettransactiondetails";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Response: " + response.getBody());
            } else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}