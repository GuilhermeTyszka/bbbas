import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class DisableSSLVerificationExample {

    public static void main(String[] args) throws Exception {
        // Desativar a verificação SSL globalmente
        disableSSLVerification();

        // Continuar com a lógica para fazer requisições HTTP sem verificação SSL
        String url = "https://example.com/api/endpoint";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println("Resposta da requisição: " + response.toString());
        }
    }

    private static void disableSSLVerification() throws Exception {
        // Cria um SSLContext que não faz verificação do certificado SSL
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());

        // Define o SSLContext padrão para o SSLContext criado
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Define um HostnameVerifier que aceita todos os certificados
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

    // Implementa um TrustManager que não faz verificação do certificado SSL
    private static class TrustAllManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
    }
}
