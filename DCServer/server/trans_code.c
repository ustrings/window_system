#include <stdio.h>
#include <string.h>


int get_character_code_type(const char* s)  
{  
    if(NULL == s)  
    {  
        return -1;  
    }  
      
    int i = 0;  
    for(; s[i] != '\0'; i++)  
    {   
        if (!(s[i] & 0x80))  
        {  
            continue;  
        }    
        else if(!( (s[i] & 0xF0) ^ 0xE0)   
                && s[i+1]   
                && !( (s[i+1] & 0xC0) ^ 0x80)   
                && s[i+2]   
                && !( (s[i+2] & 0xC0) ^ 0x80))  
        {  
            return 1;  
        }   
        else  
        {  
            return 0;  
        }  
    }  
      
    return -1;  
}  
