#include <stdio.h>
#include <string.h>
#include <stdlib.h>

char CharToInt(char ch){
        if(ch>='0' && ch<='9')return (char)(ch-'0');
        if(ch>='a' && ch<='f')return (char)(ch-'a'+10);
        if(ch>='A' && ch<='F')return (char)(ch-'A'+10);
        return -1;
}

char StrToBin(char *str){
        char tempWord[2];
        char chn;
        tempWord[0] = CharToInt(str[0]);                         //make the B to 11 -- 00001011
        tempWord[1] = CharToInt(str[1]);                         //make the 0 to 0 -- 00000000
        chn = (tempWord[0] << 4) | tempWord[1];                //to change the BO to 10110000
        return chn;
}

void UrlGB2312Decode(char* url, char*dest)
{
   	int j = 0;
        char tmp[2];
        int i=0,idx=0,ndx,len=strlen(url);

        while(i<len){
                if(url[i]=='%'){
                        tmp[0]=url[i+1];
                        tmp[1]=url[i+2];
                        dest[j++] += StrToBin(tmp);
                        i=i+3;
                }
                else if(url[i]=='+'){
                        //output+=' ';
			dest[j++] = ' ';
                        i++;
                }
                else{
                        //output+=str[i];
			dest[j++] = url[i];
                        i++;
                }
        }
}

void UrlenUTF8(char * str, char* dest)
{
	char pOut[512] = {0};
    	int len=strlen(str);
	int i = 0;
    for (i=0;i<len;i++)
    {
        if(isalnum((char)str[i]))
        {
            char tempbuff[2]={0};
            sprintf(tempbuff,"%c",(char)str[i]);
            strcat(dest, tempbuff);
        }
        else if (isspace(str[i]))
        {
	    strcat(dest, "+");
        }
        else
        {
            char tempbuff[4];
            sprintf(tempbuff,"%%%X%X",(str[i]) >>4,(str[i]) %16);
	    strcat(dest, tempbuff);
        }
    }
}

