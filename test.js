const axios = require('axios');
const fs = require('fs');
const path = require('path');

const gitlabUrl = 'https://gitlab.com'; // Substitua pela URL do seu GitLab
const projectId = 'seu-id-do-projeto'; // Substitua pelo ID do seu projeto no GitLab
const privateToken = 'seu-token-de-acesso'; // Substitua pelo seu token de acesso do GitLab

const apiBaseUrl = `${gitlabUrl}/api/v4/projects/${projectId}/repository/tree`;

async function getFilesInProject() {
  try {
    const response = await axios.get(apiBaseUrl, {
      headers: {
        'PRIVATE-TOKEN': privateToken,
      },
    });

    // Processar os arquivos e pastas
    processFiles(response.data, '');

  } catch (error) {
    console.error('Erro ao obter arquivos do GitLab:', error.message);
  }
}

function processFiles(files, currentPath) {
  for (const file of files) {
    if (file.type === 'blob') {
      // Se for um arquivo, faça o que for necessário (por exemplo, salvar o caminho do arquivo)
      const filePath = path.join(currentPath, file.name);
      console.log('Caminho do arquivo:', filePath);

      // Aqui você pode fazer algo com o caminho do arquivo, como salvá-lo em um array ou arquivo.
    } else if (file.type === 'tree') {
      // Se for uma pasta, recursivamente processar os arquivos dentro dela
      const folderPath = path.join(currentPath, file.name);
      processFiles(file.children, folderPath);
    }
  }
}

// Iniciar a execução
getFilesInProject();
