CFILES := $(wildcard src/*.c)
OBJS := $(patsubst src/%.c,objs/%.o,$(CFILES))
HEADERS := $(wildcard src/*.h)
ASMS := $(wildcard src/*.asm)

ASM_FLAGS = -felf64 -gdwarf2

CFLAGS += -O2 -Wall -Werror -std=c99 -D_XOPEN_SOURCE
CFLAGS += $(shell pkg-config portaudio-2.0 --cflags)
LIBS += $(shell pkg-config portaudio-2.0 --libs-only-l)
LDFLAGS += $(shell pkg-config portaudio-2.0 --libs-only-L --libs-only-other)

all: tuner

tuner: objs/libtuner_utils.a $(OBJS)
	$(CC) $(LDFLAGS) -o $@ $(OBJS) -L ./objs -ltuner_utils $(LIBS) 

objs/%.o: src/%.c $(HEADERS) | objs
	$(CC) $(CFLAGS) -c -o $@ $<

objs/libtuner_utils.a: objs/libtuner_utils.o
	ar rcs objs/libtuner_utils.a objs/libtuner_utils.o

objs/libtuner_utils.o: $(ASMS) | objs
	yasm -o objs/libtuner_utils.o $(ASM_FLAGS) $@ $<

objs:
	mkdir objs

clean:
	-rm tuner
	-rm -r objs
