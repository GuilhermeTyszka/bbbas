import os
from chardet import detect
import traceback
class Encoding():
    def get_encoding_type(self,file):
        with open(file, 'rb') as f:
            rawdata = f.read(67108864)
        return detect(rawdata)['encoding']
    def read_in_chunks(self,file_object, chunk_size=67108864):
        while True:
            data = file_object.read(chunk_size)
            if not data:
                break
            yield data
    def encode_file(self, srcfile, chunk_size=67108864):
        print("Encoding arquivo..")
        from_codec = self.get_encoding_type(srcfile)
        trgfile = srcfile+"_utf8"
        # add try: except block for reliability
        try:
            with open(srcfile, 'r', encoding=from_codec) as f, open(trgfile, 'w', encoding='utf-8') as e:
                for piece in self.read_in_chunks(f,chunk_size):
                    print("LENDO EM CHUNCKS...")
                    text = piece
                    e.write(text)
            os.remove(srcfile)  # remove old encoding file
            os.rename(trgfile, srcfile)  # rename new encoding
            print("ARQUIVO CONVERTIDO COM SUCESSO PARA UTF-8")
        except UnicodeDecodeError:
            print('Decode Error')
        except UnicodeEncodeError:
            print('Encode Error')
        except:
            traceback.print_exc()
