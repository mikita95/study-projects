CC = gcc
LIBDIR = ../lib/
CFLAG = -I../lib/
LDFLAG = -L../lib/ -lhelpers

PROGRAM = simplesh

all: $(PROGRAM)

$(PROGRAM): ../lib/libhelpers.so
	$(CC) $(PROGRAM).c $(CFLAG) $(LDFLAG) -o $(PROGRAM)

../lib/libhelpers.so:
	$(MAKE) -C ../lib
	
clean:
	rm -rf simplesh
