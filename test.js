const fs = require('fs');

const inputFilePath = 'caminho/do/arquivo';
const outputFilePath = 'caminho/do/novo-arquivo';

// Função para ler o arquivo, modificar a primeira linha e escrever em um novo arquivo
function replaceFirstLine(inputFilePath, outputFilePath, newFirstLine) {
  const readStream = fs.createReadStream(inputFilePath, { encoding: 'utf8' });
  const writeStream = fs.createWriteStream(outputFilePath, { encoding: 'utf8' });

  let isFirstLine = true;
  let replacedFirstLine = false;

  readStream.on('data', (chunk) => {
    // Dividir o chunk em linhas
    const lines = chunk.split('\n');

    // Processar cada linha
    lines.forEach((line, index) => {
      if (isFirstLine) {
        // Substituir a primeira linha
        writeStream.write(newFirstLine + '\n');
        isFirstLine = false;
        replacedFirstLine = true;
      } else {
        // Escrever outras linhas sem modificação
        if (index === 0 && replacedFirstLine) {
          // Ignorar a primeira linha original se já foi substituída
          return;
        }
        writeStream.write(line + '\n');
      }
    });
  });

  readStream.on('end', () => {
    // Fechar os streams ao terminar a leitura e escrita
    readStream.close();
    writeStream.end();
    console.log('Substituição da primeira linha concluída.');
  });

  readStream.on('error', (err) => {
    console.error('Erro ao ler o arquivo:', err);
  });

  writeStream.on('error', (err) => {
    console.error('Erro ao escrever no novo arquivo:', err);
  });
}

// Exemplo de uso: substituir a primeira linha por uma nova linha
const newFirstLine = 'Nova primeira linha substituída';

replaceFirstLine(inputFilePath, outputFilePath, newFirstLine);
