// openssl-des.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "openssl\bio.h"
#include "openssl\evp.h"
#include  <string.h>

int Encrypt( unsigned char * inbuf , unsigned char * outbuf , int inlen , unsigned char * key, unsigned char * iv );

int _tmain(int argc, _TCHAR* argv[])
{
	unsigned char key[] = "saf#top9";
	unsigned char iv[] = "saf#top9";
	char plaintext[] = "1";
	char outbuf[1024];

	int inlen = strlen(plaintext);
	int len = Encrypt((unsigned char *)plaintext, (unsigned char *)outbuf, inlen, key, iv);

	return 0;
}

int Encrypt( unsigned char * inbuf , unsigned char * outbuf , int inlen , unsigned char * key, unsigned char * iv )
{
	int outlen = 0, tmpLen = 0;
	EVP_CIPHER_CTX ctx;
	const EVP_CIPHER *evp_cipher; 

	EVP_CIPHER_CTX_init(&ctx);
	evp_cipher = EVP_des_cbc();	

	do
	{
		if(EVP_CipherInit_ex(&ctx,evp_cipher,NULL,key,iv,1)!= 1)//加密
		{
			break;
		}

		//EVP_CIPHER_CTX_set_padding(&ctx, 0);	//1自动填充，0不填充 ;openssl自动填充

		if(EVP_CipherUpdate(&ctx, outbuf ,&outlen, inbuf, inlen) != 1)
		{
			break;
		}

		EVP_CipherFinal_ex(&ctx, outbuf+outlen, &tmpLen);
		outlen += tmpLen;
	}while(0);

	EVP_CIPHER_CTX_cleanup(&ctx);

	return outlen;
}
