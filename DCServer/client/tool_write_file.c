#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <memory.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <unistd.h>

int fast_write_file(const char* file_name,const char * start, int len)
{
    char *fd_mmap = NULL;
    int fdout;

    if((fdout = open(file_name, O_RDWR | O_CREAT,0644)) == -1)
    {
        printf("can't open fdin ");
        return 1;
    }
    int pagesize = sysconf(_SC_PAGESIZE);
    printf("pagesize = %ld\n", pagesize);

    long old_size = lseek(fdout, 0, SEEK_END);
    int last = (old_size)%pagesize;
    if(last > 0)
        old_size = ((old_size/pagesize) + 1) *pagesize;

    int pages = len/pagesize;
    int lastPage = 0;
    if((len)%pagesize != 0)
    {
        lastPage = (len)%pagesize;
        pages += 1;
        printf("lastPage = %d\n", lastPage);
    }
    printf("pages = %d\n", pages);

    int offset = old_size;
    printf("offset = %d\n", offset);
    while(pages--)
    {
        int currLen = 0;
        if (pages == 0 && lastPage != 0)
        {
            currLen = lastPage;
        }
        else
        {
            currLen = pagesize;
        }

        ftruncate(fdout, offset+currLen);
        printf("at %d\n", pages);

        fd_mmap = mmap(0,pagesize, PROT_READ | PROT_WRITE,MAP_SHARED, fdout, offset);
        if( fd_mmap == MAP_FAILED )
        {
            printf("mmap error for fdout.\n");
            return 1;
        }
        memcpy(fd_mmap,start + offset-old_size, currLen);

        offset += currLen;
        munmap(fd_mmap, pagesize);
    }
    close(fdout);
    return 0;
}

static FILE * stream = NULL;
int normal_write_file(const char* file_name,const char * start, int len)
{
	if(stream == NULL)
	{
		stream=fopen(file_name, "at+");
	}
	fwrite(start, len, 1, stream);
	fflush(stream);
	return 0;
}

int fast_write_file_test()
{
    char * file = "./testfile.txt";

    int i=0;
    for(i=0; i < 10; i++)
    {
        printf("%d\n", i);
        char* test = "123123123\n";
        //fast_write_file(file, test, 4096);
        normal_write_file(file, test, strlen(test));
    }
    return 0;
}
