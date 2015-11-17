///////////////////////////////////////////////////////////////////////////////////
//		FileName	:	gb2utf8.h 
//		Author		:	郭瑞杰(ruijieguo@software.ict.ac.cn)
//		CreationDate:	2007-01-24
//		Function	:	1) GBK代码转换为UTF-8代码;  
//						2) GBK代码转换为BIG5代码
//		UpdateLog	:	1.Created on 2007-01-24
//		
///////////////////////////////////////////////////////////////////////////////////
#ifndef __GB2UTF8__H__
#define __GB2UTF8__H__


/*
	[功能说明]
	GBK字符到UTF－8的转换 

	[参数说明]
	code : GBK代码
	output : 输出的UTF-8字符，应该预留好足够空间，至少5个字符的大小

	[返回值说明]
	成功返回0, 失败返回非零
*/
int char_gbk2utf8( unsigned  code, char *output);

/*
	[功能说明]
	GBK字符到BIG5的转换

	[参数说明]
	code : GBK代码
	output : 输出的BIG5字符，应该预留好足够空间，至少3个字符的大小

	[返回值说明]
	成功返回0, 失败返回非零
*/
int char_gbk2big5( unsigned code, char *output);

/*
	[功能说明]
	GBK字符串到UTF－8的转换

	[参数说明]
	buffer : GBK字符串
	output : 输出的UTF-8字符串，应该预留好足够空间，如果为空，则返回值为所需空间

	[返回值说明]
	成功返回0, 失败返回非零，如果output为空则返回值为所需空间
*/
int buffer_gbk2utf8( const char* buffer,int output_len, char *output);

/*
	[功能说明]
	GBK字符串到BIG5的转换 

	[参数说明]
	buffer : GBK字符串
	output : 输出的BIG5字符串，应该预留好足够空间

	[返回值说明]
	成功返回0, 失败返回非零
*/
int buffer_gbk2big5( const char* buffer, char *output);


/*
	[功能说明]
	将GBK码的文件转换成UTF8的

	[参数说明]
	infile : 输入的文件名
	outfile : 输出的文件名

	[返回值说明]
	成功时返回0, 失败时返回负值
*/
int file_gbk2utf8( char *infile, char *outfile);

/*
	[功能说明]
	将GBK码的文件转换成BIG5的

	[参数说明]
	infile : 输入的文件名
	outfile : 输出的文件名
	outfound : 对于无法转换的字，使用此字符串进行替换

	[返回值说明]
	成功时返回0, 失败时返回负值
*/
int file_gbk2big5(  char*infile,  char *outfile,  char* notfound);



#endif //__GB2UTF8__H__

