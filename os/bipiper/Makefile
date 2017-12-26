CC = gcc
LIBDIR = ../lib/
CFLAG = -I../lib/
LDFLAG = -L../lib/ -lbufio

PROGRAM1 = forking
PROGRAM2 = polling

all: $(PROGRAM1) $(PROGRAM2)

$(PROGRAM1): ../lib/libbufio.so
	$(CC) $(PROGRAM1).c $(CFLAG) $(LDFLAG) -o $(PROGRAM1)
	
$(PROGRAM2): ../lib/libbufio.so
	$(CC) $(PROGRAM2).c $(CFLAG) $(LDFLAG) -o $(PROGRAM2)
	
../lib/libbufio.so:
	$(MAKE) -C ../lib
	
clean:
	rm -rf forking
	rm -rf polling
