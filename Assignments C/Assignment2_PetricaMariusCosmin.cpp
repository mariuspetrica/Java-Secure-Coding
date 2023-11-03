
#include <iostream>
#include <sstream>
#include <fstream>
#include <string>
#include <iomanip>
#include <stdlib.h>
#include <openssl/md5.h>
#include <openssl/sha.h>
#include <openssl/rsa.h>
#include <openssl/pem.h>
#include <openssl/applink.c>

using namespace std;

bool verify(string decrFile, string signFile) {
	FILE* decrData = NULL;
	FILE* signF = NULL;

	unsigned char* fileBuff = NULL;
	unsigned char digest[SHA256_DIGEST_LENGTH];
	SHA256_CTX ctx;
	SHA256_Init(&ctx);
	fopen_s(&decrData, decrFile.c_str(), "rb");
	fseek(decrData, 0, SEEK_END);
	int fileLen = ftell(decrData);
	fseek(decrData, 0, SEEK_SET);

	fileBuff = (unsigned char*)malloc(fileLen);
	fread(fileBuff, fileLen, 1, decrData);
	unsigned char* tmpBuff = fileBuff;

	while (fileLen > 0) {
		if (fileLen > 256) {
			SHA256_Update(&ctx, tmpBuff, 256);
		}
		else {
			SHA256_Update(&ctx, tmpBuff, fileLen);
		}
		fileLen -= 256;
		tmpBuff += 256;
	}

	SHA256_Final(digest, &ctx);

	string result;
	char buff[64];
	cout << "SH256 Value is: ";
	for (int i = 0; i < 32; i++) {
		//printf("%02x", digest[i]);
		sprintf_s(buff, "%02x", digest[i]);
		result.append(buff);
		cout <<buff;
	}
	cout << endl;
	fopen_s(&signF, signFile.c_str(), "rb");

	RSA* apublic;
	FILE* f;
	int ret;
	unsigned char* buf = NULL;
	unsigned char* lastData = NULL;

	apublic = RSA_new();

	f = fopen("pubKeySender.pem", "r");
	apublic = PEM_read_RSAPublicKey(f, NULL, NULL, NULL);//loading public key
	fclose(f);

	buf = (unsigned char*)malloc(RSA_size(apublic));

	fread(buf, RSA_size(apublic), 1, signF); // read signature

	stringstream ss;
	for (int i = 0; i < 32; i++)
	{
		ss << hex << setw(2) << setfill('0') << (int)buf[i];
	}
	string decryptedSignature= ss.str();
	cout << "Signature from file is: " << decryptedSignature << endl;

	lastData = (unsigned char*)malloc(32); // restored hash buffer

	RSA_public_decrypt(RSA_size(apublic), buf, lastData, apublic, RSA_PKCS1_PADDING); // decryption with pubKey

	fclose(signF);

	if (memcmp(lastData, digest, 32) == 0) {// compare
		printf("\n Signature OK!\n");
		return true;
	}
	else {
		printf("\n Signature is wrong!\n");
		return false;
	}

	free(lastData);
	free(buf);

	RSA_free(apublic);

}

int main() {

	verify("ignis-10M.txt", "RSASign.sig");
	return 0;
}
