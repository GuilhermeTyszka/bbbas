const message = {
  "@context": "https://schema.org/extensions",
  "@type": "MessageCard",
  "themeColor": "0078D7",
  "title": "Mensagem de Exemplo",
  "text": "Esta é uma mensagem de exemplo enviada para o Microsoft Teams usando um webhook.",
  "potentialAction": [
    {
      "@type": "OpenUri",
      "name": "Saiba mais",
      "targets": [
        { "os": "default", "uri": "https://example.com" }
      ]
    }
  ]
};

// Enviar a mensagem usando Axios
axios.post(webhookUrl, message)
  .then(response => {
    console.log('Mensagem enviada com sucesso:', response.data);
  })
  .catch(error => {
    console.error('Erro ao enviar mensagem:', error.message);
  });
