/*
* this is a des/cbc/pkcs#5 sample code with crypto++ 5.6.3 lib
*/

#include "DesUtil.h"  
#include <iostream>  
#include <des.h>  
#include <base64.h>  
#include <osrng.h>  
#include <string>  
#include <cstdlib>  
#include <cryptlib.h>  
#include <hex.h>  
#include <filters.h>  
#include <modes.h>  
#include <secblock.h>  
  
DesUtil::DesUtil(const char *key) : keystr(key) {  
    // TODO Auto-generated constructor stub  
  
}  
  
void DesUtil::Base64en(const unsigned char* ming, unsigned char* enstr) {  
    using CryptoPP::Base64Encoder;  
    Base64Encoder base64en;  
    base64en.Put(ming, strlen((const char *) ming));  
  
    base64en.MessageEnd();  
    //  base64en.MessageSeriesEnd();  
  
    unsigned int size = base64en.MaxRetrievable();  
    byte* temp = new byte[size + 1];  
    temp[size] = '\0';  
  
    base64en.Get(temp, size);  
  
    //  cout << "Base64en: " << temp << endl;  
    memcpy(enstr, temp, size + 1);  
    delete temp;  
}  
  
void DesUtil::Base64de(const unsigned char* mi, unsigned char* destr) {  
    using CryptoPP::Base64Decoder;  
    Base64Decoder base64de;  
    base64de.Put(mi, strlen((const char *) mi));  
    base64de.MessageEnd();  
    unsigned int size = base64de.MaxRetrievable();  
    byte* temp = new byte[size + 1];  
    temp[size] = '\0';  
    base64de.Get(temp, size);  
    //  cout << "Base64de: " << temp << endl;  
    memcpy(destr, temp, size + 1);  
    delete temp;  
}  
  
std::string DesUtil::Encrypt(const std::string & content) {  
    using namespace CryptoPP;  
    std::string cipher;  
    try {  
		//ECB_Mode<DES>::Encryption e;  
        //e.SetKey((const unsigned char *) keystr, DES::DEFAULT_KEYLENGTH);
		AutoSeededRandomPool prng;

		//SecByteBlock key(AES::DEFAULT_KEYLENGTH);
		//prng.GenerateBlock( key, key.size() );

		byte iv[ DES::BLOCKSIZE ];
		//prng.GenerateBlock( iv, sizeof(iv) );
		memcpy(iv,keystr,sizeof(iv));

		CBC_Mode<DES>::Encryption e;  
		e.SetKeyWithIV((const unsigned char *) keystr, DES::DEFAULT_KEYLENGTH, iv ) ; 
		//or like this:
		//CryptoPP::DESEncryption desE((const unsigned char *) keystr, DES::DEFAULT_KEYLENGTH); 
		//CryptoPP::CBC_Mode_ExternalCipher::Encryption e(desE, (const byte *)iv); 

		// The StreamTransformationFilter adds padding  
        //  as required. ECB and CBC Mode must be padded  
        //  to the block size of the cipher.  
        StringSource(content, true,  
                new StreamTransformationFilter(e, new StringSink(cipher), StreamTransformationFilter::PKCS_PADDING) // StreamTransformationFilter  
                        );// StringSource  
        byte * mi = new byte[strlen(content.c_str())*8];  
        Base64en((unsigned char*) cipher.c_str(), mi);  
        std::string result((char*) mi);  
        //delete mi;  
        return result;  
    } catch (const CryptoPP::Exception& e) {  
        std::cerr << e.what() << std::endl;  
        exit(1);  
    }  
    return NULL;  
}  
  
std::string DesUtil::Decrypt(const std::string & content) {  
    using namespace CryptoPP;  
    std::string recovered;  
    byte * ming = new byte[strlen(content.c_str())*8];  
    Base64de((unsigned char*) content.c_str(), ming);  
    std::string temp((char *) ming);  
    delete ming;  
    try {  
		byte iv[ DES::BLOCKSIZE ];
		memcpy(iv,keystr,sizeof(iv));
        CBC_Mode<DES>::Decryption d;  
        d.SetKeyWithIV((const unsigned char *) keystr, DES::DEFAULT_KEYLENGTH, iv);  
  
        // The StreamTransformationFilter removes  
        //  padding as required.  
        StringSource s(temp, true,  
                new StreamTransformationFilter(d, new StringSink(recovered), StreamTransformationFilter::PKCS_PADDING) // StreamTransformationFilter  
                        );// StringSource  
        return recovered;  
//          std::cout << "recovered text: " << recovered << std::endl;  
    } catch (const CryptoPP::Exception& e) {  
        std::cerr << e.what() << std::endl;  
        exit(1);  
    }  
    return NULL;  
}  