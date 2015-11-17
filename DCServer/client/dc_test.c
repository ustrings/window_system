#include <string.h>
#include <stdio.h>
#include <stdlib.h>

extern void test_cookies_filter();
extern void test_host_filter();
extern void test_ip_learn();
int main(int argc, char *argv[])
{
    test_cookies_filter();
    test_host_filter();
    test_ip_learn();
    return 0;
}
