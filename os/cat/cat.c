#include <helpers.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
void print_error() {
	write_(STDERR_FILENO, strerror(errno), sizeof(char) * strlen(strerror(errno)));
}

int main() {
	char buffer[4096];
	size_t length = 0;
	size_t count = sizeof(buffer);
	while (1) {
		if ((length = read_(STDIN_FILENO, buffer, count)) == -1) {
			print_error();
			exit(1);
		}
		if (write_(STDOUT_FILENO, buffer, length) < length) {
			print_error();
			exit(1);
		}
		if (length != count)
			return 0;
	}
}
