const fs = require('fs');
const Papa = require('papaparse');

const arquivo = 'seuarquivo.csv';

fs.readFile(arquivo, 'utf8', (err, data) => {
  if (err) {
    console.error(err);
    return;
  }

  Papa.parse(data, {
    header: true,
    delimiter: ',',
    complete: function(results) {
      const header = results.meta.fields;
      console.log('Cabeçalhos:', header);
      
      results.data.forEach(row => {
        console.log('ID:', row.id);
        console.log('Name:', row[' "name"']);
        console.log('Databases:', row[' "databases"']);
      });
    }
  });
});
