const jdbc = require('jdbc');
const jinst = require('jdbc/lib/jinst');

if (!jinst.isJvmCreated()) {
  jinst.addOption('-Xrs');
  jinst.setupClasspath(['caminho/para/o/driver/jdbc/ImpalaJDBCDriver.jar']); // Substitua pelo caminho para o arquivo JAR do driver do Impala
}

const config = {
  url: 'jdbc:impala://seuserver:porta/seubanco', // Substitua pelo URL do Impala
  drivername: 'com.cloudera.impala.jdbc41.Driver',
  minpoolsize: 5,
  maxpoolsize: 10,
  user: 'seu_usuario', // Substitua pelo nome de usuário
  password: 'sua_senha', // Substitua pela senha
};

jdbc.initialize(config, (err, res) => {
  if (err) {
    console.log(err);
  } else {
    console.log('Conexão com o Impala estabelecida.');

    const conn = jdbc.reserve();

    conn.createStatement((err, statement) => {
      if (err) {
        console.log(err);
      } else {
        const sql = 'SELECT * FROM sua_tabela'; // Substitua pela consulta SQL desejada

        statement.executeQuery(sql, (err, resultset) => {
          if (err) {
            console.log(err);
          } else {
            resultset.toObjArray((err, results) => {
              if (err) {
                console.log(err);
              } else {
                console.log('Resultado da consulta:', results);
              }
            });
          }

          jdbc.release(conn, (err) => {
            if (err) {
              console.log(err);
            } else {
              console.log('Conexão liberada.');
            }
          });
        });
      }
    });
  }
});
