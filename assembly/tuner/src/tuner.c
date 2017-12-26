#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <signal.h>

#include "libtuner_utils.h"
#include "tuner.h"
#include <portaudio.h>

void write_error(PaStream* stream, PaError error_message, void* fft) {
    if( stream ) {
        Pa_AbortStream(stream);
        Pa_CloseStream(stream);
    }
    destroyfft(fft);
    Pa_Terminate();
    fprintf(stderr, "An error occured while using the portaudio stream\n");
    fprintf(stderr, "Error number: %d\n", error_message);
    fprintf(stderr, "Error message: %s\n", Pa_GetErrorText(error_message));
    exit(1);
}

int main( int argc, char **argv ) {
    PaStreamParameters input_parameters;
    float a[2], b[3], mem1[4], mem2[4];
    float data[FFT_SIZE];
    float datai[FFT_SIZE];
    float window[FFT_SIZE];
    float freq_table[FFT_SIZE];
    int note_name_table[FFT_SIZE];
    float note_pitch_table[FFT_SIZE];
    void* fft = 0;
    PaStream* stream = 0;
    PaError error_message = 0;
    struct sigaction action;
   
    action.sa_handler = signal_handler;
    sigemptyset(&action.sa_mask);
    action.sa_flags = 0;

    sigaction(SIGINT, &action, 0);
    sigaction(SIGHUP, &action, 0);
    sigaction(SIGTERM, &action, 0);

    build_hann_window(window, FFT_SIZE);
    
    fft = initfft(FFT_EXP_SIZE);
    float pair[2];
    pair[0] = SAMPLE_RATE;
    pair[1] = 330.0;
   
    compute_second_low_pass_params(pair, a, b);
   
    for (int i = 0; i < 4; i++) {
        mem1[0] = 0;
        mem2[0] = 0;
    }
    
    get_freq_table(freq_table);

    for (int i = 0; i < FFT_SIZE; ++i )
        note_name_table[i] = -1;

    get_note_pitch_table(note_name_table, freq_table, note_pitch_table);

    error_message = Pa_Initialize();
    
    if(error_message != paNoError)
        write_error(stream, error_message, fft);

    input_parameters.device = Pa_GetDefaultInputDevice();
    input_parameters.channelCount = 1;
    input_parameters.sampleFormat = paFloat32;
    input_parameters.suggestedLatency = Pa_GetDeviceInfo(input_parameters.device)->defaultHighInputLatency ;
    input_parameters.hostApiSpecificStreamInfo = 0;

    printf("Opening %s\n", Pa_GetDeviceInfo(input_parameters.device)->name);

    error_message = Pa_OpenStream(&stream, &input_parameters, 0, SAMPLE_RATE, FFT_SIZE, paClipOff, 0, 0);
    
    if (error_message != paNoError)
        write_error(stream, error_message, fft);

    error_message = Pa_StartStream(stream);
    
    if (error_message != paNoError)
        write_error(stream, error_message, fft);

    while(running) {
        error_message = Pa_ReadStream(stream, data, FFT_SIZE);
        if (error_message)
            write_error(stream, error_message, fft);

        for (int j = 0; j < FFT_SIZE; ++j) {
            data[j] = process_second_order_filter(data[j], mem1, a, b);
            data[j] = process_second_order_filter(data[j], mem2, a, b);
        }
        
        apply_window(window, data, FFT_SIZE);

        for (int j = 0; j < FFT_SIZE; ++j)
            datai[j] = 0;
        applyfft(fft, data, datai, false);

        float max_value = -1;
        int max_index = -1;
     
        max_index = find_peak_index(data, datai);
        max_value = find_peak_value(data, datai);
        float freq = freq_table[max_index];
      
        int nearest_note_delta = 0;
      
        nearest_note_delta = find_nearest_note(note_name_table, max_index);
        char* nearest_note_name = NOTES[note_name_table[max_index+nearest_note_delta]];
        float nearest_note_pitch = note_pitch_table[max_index+nearest_note_delta];
        float cents_sharp = 1200 * log(freq / nearest_note_pitch) / log(2.0);

        printf("\033[2J\033[1;1H");
        fflush(stdout);

        printf("Tuner listening. Control-C to exit.\n");
        printf("%f Hz, %f\n", freq, max_value * 1000);
        printf("Nearest Note: %s\n", nearest_note_name);
        
        if (nearest_note_delta != 0) {
            if (cents_sharp > 0)
                printf("%f cents sharp.\n", cents_sharp);
            if (cents_sharp < 0)
                printf("%f cents flat.\n", -cents_sharp);
        } else {
            printf("in tune!\n");
        }
        
        printf("\n");
        int chars = 30;
        if (nearest_note_delta == 0 || cents_sharp >= 0) {
            for (int i = 0; i < chars; ++i)
                printf(" ");
        } else {
            for (int i = 0; i < chars + cents_sharp; ++i)
                printf(" ");
            for (int i = chars + cents_sharp < 0 ? 0 : chars + cents_sharp; i < chars; ++i)
                printf("=");
        }
        printf(" %2s ", nearest_note_name);
        if (nearest_note_delta != 0)
            for (int i = 0; i < chars && i < cents_sharp; ++i)
                printf("=");
        printf("\n");
    }
    error_message = Pa_StopStream(stream);
    if (error_message != paNoError)
        write_error(stream, error_message, fft);

    destroyfft(fft);
    Pa_Terminate();

    return 0;
}

void build_hann_window(float* window, int size) {
    for(int i = 0; i < size; ++i)
        window[i] = 0.5 * (1 - cos(2 * M_PI * i / (size - 1.0)));
}

void apply_window(float* window, float* data, int size) {
    for (int i = 0; i < size; ++i)
        data[i] *= window[i];
}

void signal_handler(int signum) { 
    running = false; 
}
