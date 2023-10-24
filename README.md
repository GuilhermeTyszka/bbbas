const jdbc = require('jdbc');

const config = {
  url: 'jdbc:impala://seuserver:porta/seubanco', // Substitua pelo URL do Impala
  drivername: 'com.cloudera.impala.jdbc41.Driver',
  user: 'seu_usuario', // Substitua pelo nome de usuário
  password: 'sua_senha', // Substitua pela senha
};

jdbc.open(config, (err, conn) => {
  if (err) {
    console.error(err);
  } else {
    console.log('Conexão com o Impala estabelecida.');

    const sql = 'SELECT * FROM sua_tabela'; // Substitua pela consulta SQL desejada

    conn.createStatement((err, statement) => {
      if (err) {
        console.error(err);
      } else {
        statement.executeQuery(sql, (err, resultset) => {
          if (err) {
            console.error(err);
          } else {
            resultset.toObjArray((err, results) => {
              if (err) {
                console.error(err);
              } else {
                console.log('Resultado da consulta:', results);
              }
            });
          }

          conn.close((err) => {
            if (err) {
              console.error('Erro ao fechar a conexão:', err);
            } else {
              console.log('Conexão fechada.');
            }
          });
        });
      }
    });
  }
});
