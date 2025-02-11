package com.example.webshoplabb.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate bankIdRestTemplate() throws Exception {
        // ✅ Load password from environment variables or application.properties
        String keystorePassword = System.getenv("BANKID_KEYSTORE_PASSWORD"); // Load from env variable
        if (keystorePassword == null) {
            keystorePassword = "qwerty123"; // Fallback (not recommended)
        }

        // ✅ Load keystore dynamically from resources
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream keyStoreStream = getClass().getClassLoader().getResourceAsStream("FPTestcert5_20240610.p12")) {
            if (keyStoreStream == null) {
                throw new RuntimeException("Keystore file not found in resources!");
            }
            keyStore.load(keyStoreStream, keystorePassword.toCharArray());
        }

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, keystorePassword.toCharArray())
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();



        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

        // ✅ Use a pooling connection manager for better performance
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(20);  // Maximum total connections
        connectionManager.setDefaultMaxPerRoute(5);  // Maximum connections per route

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }
}
