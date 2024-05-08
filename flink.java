import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DisableSSLForSingleConnectionExample {

    public static void main(String[] args) throws Exception {
        // URL para a conexão HTTP
        String url = "https://example.com/api/endpoint";

        // Abrir a conexão HTTP
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // Desativar a verificação SSL para esta conexão
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setSSLSocketFactory(new NoSSLVerificationSocketFactory());
            ((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);
        }

        // Configurar o método e ler a resposta da requisição
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

    // Implementa um SSLSocketFactory que não faz verificação do certificado SSL
    private static class NoSSLVerificationSocketFactory extends javax.net.ssl.SSLSocketFactory {
        public javax.net.ssl.SSLSocket createSocket() { return null; }
        public javax.net.ssl.SSLSocket createSocket(String host, int port) { return null; }
        public javax.net.ssl.SSLSocket createSocket(String host, int port, java.net.InetAddress localHost, int localPort) { return null; }
        public javax.net.ssl.SSLSocket createSocket(java.net.InetAddress host, int port) { return null; }
        public javax.net.ssl.SSLSocket createSocket(java.net.InetAddress address, int port, java.net.InetAddress localAddress, int localPort) { return null; }
        public java.lang.String[] getDefaultCipherSuites() { return new String[0]; }
        public java.lang.String[] getSupportedCipherSuites() { return new String[0]; }
    }
}
