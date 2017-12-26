#ifndef _TUNER_H
#define _TUNER_H

#ifdef __cplusplus
extern "C" {
#endif

#define SAMPLE_RATE 8000
#define FFT_SIZE 8192
#define FFT_EXP_SIZE 13

void build_hann_window(float* window, int size);
void apply_window(float* window, float* data, int size);
void signal_handler(int signum) ;

static bool running = true;

static char* NOTES[] = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };

#ifdef __cplusplus
}
#endif

#endif


