
#include <iostream>
#include <sstream>
#include <fstream>
#include <string>
#include <iomanip>
#include <stdlib.h>
#include <openssl/md5.h>
#include <openssl/sha.h>

using namespace std;


void compute_md5(char* str, unsigned char digest[16]) {
	MD5_CTX ctx;
	MD5_Init(&ctx);
	MD5_Update(&ctx, str, strlen(str));
	MD5_Final(digest, &ctx);
}

void sha256(unsigned char str[16], unsigned char digest[32])
{
	//unsigned char hash[32];
	SHA256_CTX sha256;
	SHA256_Init(&sha256);
	SHA256_Update(&sha256, str, 16);
	SHA256_Final(digest, &sha256);
	/*stringstream ss;
	for (int i = 0; i < 32; i++)
	{
		ss << hex << setw(2) << setfill('0') << (int)hash[i];
	}
	return ss.str();*/
}

int main() {
	//ifstream file("ignis-10M.txt");

	string str;
	int i = 0;


	FILE * fp = fopen("ignis-10M.txt", "rb");
		if (fp == NULL) {
			perror("Unable to open file!");
			exit(1);
		}

	char chunk[128];
	string salt = "ismsap";
    while (fgets(chunk, sizeof(chunk), fp)) {
		//cout << chunk;
		chunk[strcspn(chunk, "\r\n")] = 0;
		string chunkString = string(chunk);
		string newChunk = salt + chunkString;
		//cout << newChunk<< endl;

		char* cstr = new char[newChunk.length() + 1];
		strcpy(cstr, newChunk.c_str());

		unsigned char digest[16];
		compute_md5(cstr, digest);
		unsigned char digestFinal[32];
		sha256(digest,digestFinal);

		stringstream ss;
		for (int i = 0; i < 32; i++)
		{
			ss << hex << setw(2) << setfill('0') << (int)digestFinal[i];
		}
		string sha256Result = ss.str();

		//cout << result;
		//putchar('\n');
		//cout << sha256(result);
		//putchar('\n');
		string myHash("82e14169ced3bd6612336fe774e90dc7eb2e302f8bcc6aef4ef46cbf6267db34");
		//cout << sha256Result;
		int res = sha256Result.compare(myHash);
		if (res == 0) {
			cout << "\nBoth the input strings are equal." << endl;
			cout<<"Corresponding password is : " << chunk << " at line : " << i + 1 << endl;
			cout << "Found hash is :";
			string result;
			char buf[64];

			for (int i = 0; i < 32; i++) {
				//printf("%02x", digest[i]);
				sprintf_s(buf, "%02x", digestFinal[i]);
				result.append(buf);
				cout << buf;
			}
			return 0;
		}
		delete[] cstr;
		i++;
		//cout << i << endl;
	}
    fclose(fp);
	return 0;
}