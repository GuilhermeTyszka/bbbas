const fs = require('fs');
const jschardet = require('jschardet');
const util = require('util');
const rename = util.promisify(fs.rename);

class Encoding {
  async getEncodingType(file) {
    const rawdata = await this.readFileChunk(file, 67108864);
    const result = jschardet.detect(rawdata);
    return result.encoding;
  }

  async readFileChunk(file, chunkSize) {
    const data = [];
    const fileStream = fs.createReadStream(file, { highWaterMark: chunkSize });

    for await (const chunk of fileStream) {
      data.push(chunk);
    }

    return Buffer.concat(data);
  }

  async encodeFile(srcfile, chunkSize = 67108864) {
    console.log("Encoding arquivo..");

    try {
      const fromCodec = await this.getEncodingType(srcfile);
      const trgfile = srcfile + "_utf8";

      const srcFileStream = fs.createReadStream(srcfile, { encoding: fromCodec });
      const trgFileStream = fs.createWriteStream(trgfile, { encoding: 'utf-8' });

      await this.copyFileWithChunks(srcFileStream, trgFileStream, chunkSize);

      await fs.promises.unlink(srcfile); // remove old encoding file
      await rename(trgfile, srcfile); // rename new encoding

      console.log("ARQUIVO CONVERTIDO COM SUCESSO PARA UTF-8");
    } catch (error) {
      console.error('Erro durante a conversão:', error);
    }
  }

  async copyFileWithChunks(srcStream, trgStream, chunkSize) {
    for await (const chunk of srcStream) {
      trgStream.write(chunk);
    }

    trgStream.end();
  }
}

const encoder = new Encoding();
encoder.encodeFile('seuarquivo.txt');
