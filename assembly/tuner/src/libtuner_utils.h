#ifndef _FFT_H
#define _FFT_H
#include <stdbool.h>
#ifdef __cplusplus
extern "C" {
#endif

    /* Initialize for fast Fourier transform
    *  bits - power of 2 such that 2^nu = number of samples */
    void* initfft(int bits);
    
    /* Free memory */
    void destroyfft(void* fft);
    
    /* Apply fast Fourier transform
    *  xr - real part of data to be transformed
    *  xi - imaginary part (normally zero, unless inverse transform in effect)
    *  inv - flag for inverse */
    void applyfft(void* fft, float* xr, float* xi, bool inv);

    /* Returns table of pitches of every note. Also takes array of FFT_SIZE, 
    *  that must be filled with -1 and fills it with indexes from NOTES array. 
    *  If note must be NULL then it's -1. */
    void get_note_pitch_table(int* note, float* freq_table, float* note_pitch_table);

    /* Returns table of frequences. Size of table is FFT_SIZE. */
    void get_freq_table(float* table);

    /* Computes the second low passed parameters
     * Args[0] - state; Args[1] - f; size of a = 2; size of b = 3 */
    void compute_second_low_pass_params(float* args, float* a, float* b);
    
    /* Filters frequences */
    float process_second_order_filter(float x, float *mem, float *a, float* b);
    
    /* Finds peak of frequnces */
    float find_peak_value(float* data, float* datai);
    
    /* Returns index of peak in frequences table */
    int find_peak_index(float* data, float* datai);
    
    /* Finds the nearest note of index */
    int find_nearest_note(int* note_table, int max_index);

    void build_han_window(float* window, int size);

#ifdef __cplusplus
}
#endif

#endif


