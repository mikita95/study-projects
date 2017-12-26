;; @author Nikita MArkovnikov, group 2537
;; Some utils for course project
;; FFT and help functions like filter, Hann window, maximim peak search

default rel

extern malloc
extern free
extern fprintf
extern exit
extern stderr

global initfft
global destroyfft
global applyfft

global get_note_pitch_table
global get_freq_table
global compute_second_low_pass_params
global process_second_order_filter

global find_peak_value
global find_peak_index
global find_nearest_note

global build_han_window

section .text

%define MAX_FFT_SIZE        32769
%define LOG2_MAX_FFT_SIZE   15

;; Save registers as a convenction says
%macro begin_function 0

    push r15
    push r14
    push r13
    push r12
    push r11
    push r10
    push rdi
    push rbx

%endmacro

;; Return registers
%macro finish_function 0

    pop rbx
    pop rdi
    pop r10
    pop r11
    pop r12
    pop r13
    pop r14
    pop r15

%endmacro

;; Puts register or constant to st(0)
%macro put_to_fpu 1
    push rbx

    mov rbx, %1
    mov [p], ebx
    fild dword[p]

    pop rbx
%endmacro

;; Loads values from table and write to specified place
%macro load_from_table 3
    push rbx

    fld qword[%1]
    fstp dword[rdx + 4 * %2]

    xor rbx, rbx
    mov rbx, %3
    mov [rdi + 4 * %2], ebx

    pop rbx
%endmacro

;; b - bits count
;; bitreverse - reversed array of bits
struc fft

    b:          resd    1
    bitreverse: resd    MAX_FFT_SIZE

endstruc

;; void* initfft(int bits)
;;      Initialize for fast Fourier transform
;; Takes:   RDI - bits - power of 2 such that 2^nu = number of samples
;; Returns: RAX - pointer to fft structure
initfft:
    push rdi

    mov rdi, fft_size
    call malloc                                 ; pointer to fft in rax

    pop rdi

    cmp rax, 0
    je .malloc_error
    mov [rax], edi

    cmp rdi, LOG2_MAX_FFT_SIZE
    jg .bits_error

    xor rcx, rcx
    mov rcx, rdi

    push rbx
    mov rbx, 1
    shl rbx, cl
    mov rcx, rbx
    pop rbx

    push rbx
    push r15
    push r14

    ;; for (int i = (1 << fft->bits) - 1; i >= 0; i--)
    .loop1:
        sub rcx, 1
        xor rbx, rbx                            ; k
        xor r14, r14                            ; j

        ;; for (int j = 0; j < fft->bits; j++)
        .loop2:
            imul rbx, 2
            mov r15, 1

            push rcx
            xor rcx, rcx
            mov rcx, r14
            shl r15, cl
            pop rcx

            and r15, rcx
            cmp r15, 0

            jng .continue
            inc rbx
            .continue:

            inc r14
            cmp r14, rdi

            je .break2
            jmp .loop2
        .break2:
        mov [rax + 4 * (rcx + 1)], ebx
        cmp rcx, 0

        je .break1
        jmp .loop1
    .break1:

    pop r14
    pop r15
    pop rbx

    ret

    .malloc_error:
    mov rsi, msgAlloc
    jmp .msg

    .bits_error:
    mov rsi, msgBits
    jmp .msg

    .msg:
    mov rdi, [stderr]
    call fprintf
    mov rdi, 1

    call exit

;; void destroyfft(void *fft);
;;      Free memory of fft
;; Takes:   RDI - pointer to fft structure
destroyfft:
    call free

    ret

;; void applyfft(void* fft, float* xr, float* xi, bool inv);
;;      Applies fast Fourier transform
;; Takes:   RDI - pointer to fft structure
;;          RSI - pointer to array of real part of data to be transform
;;          RDX - pointer to array of imaginary part of data to transform
;;                  normally zero, unless inverse transform in effect
;;          RCX - flag to be inverse
applyfft:
    push r15
    push r14
    push r13
    push r12
    push r11
    push r10
    push rdi

    push rcx

    xor rcx, rcx
    mov ecx, [rdi]
    mov r14, 1                                  ; r14 = n
    shl r14, cl                                 ; n = 1 << fft->bits
    pop rcx

    push rdx
    xor rdx, rdx
    mov rax, r14
    mov r8, 2
    div r8                                      ; n2 = n / 2
    mov r13, rax                                ; r13 = n2

    pop rdx

    push rbx
    xor rbx, rbx                                ; l = 0

    ;; for (l = 0; l < mfft->bits; l++)
    .loop1:
        xor r12, r12                            ; k = 0
        ;; for (k = 0; k < n; k += n2)
        .loop2:
            xor r11, r11                        ; i = 0
            ;; for (i = 0; i < n2; i++; k++)
            .loop3:

                mov rax, r12
                push rdx
                xor rdx, rdx

                div r13
                mov r10, rax                    ; r10 = k / n2
                pop rdx            

                xor r8, r8
                xor rax, rax 

                fild dword[rdi + 4 * (r10 + 1)] ; st(0) = p
                

                fldpi                           ; st(0) = pi; st(1) = p
                fmul qword[d]                   ; st(0) = 2 * pi; st(1) = p
                fmul st1, st0                   ; st(0) = 2 * pi; st(1) = p * 2 * pi
                fstp qword[p]                   ; st(0) = p * 2 * pi

                mov [p], r14d
                fild dword[p]                    ; st(0) = n; st(1) = 2 * pi * n
                fdiv st1, st0
                                                ; st(0) = n; st(1) = 2 * pi * p / n = ang
                fstp qword[p]

                xor r8, r8                      ; r8 = 0
                xor r9, r9                      ; r9 = 0
                fst st1                         ; copy ang to st(1)

                fcos                            ; st(0) = cos(ang)
                fstp st2                        ; st(1) = cos(ang) = c and st(0) = ang

                fsin                            ; st(0) = sin(ang) = s

                cmp rcx, 0
                jng .not_inv

                fchs                            ; st(0) = -st(0) <=> s = -s
                .not_inv:

                push rcx
                push rbx

                mov rbx, r12
                add rbx, r13                    ; rbx = k + n2
                fld dword[rsi + 4 * rbx]        ; st(0) = xr[k + n2]; st(1) = s; st(2) = c
                fmul st0, st2                   ; st(0) = xr[k + n2] * c

                fld dword[rdx + 4 * rbx]        ; st(0) = xi[k + n2]; st(1) = xr[k + n2] * c; 
                                                ; st(2) = s; st(3) = c
                fmul st0, st2                   ; st(0) = xi[k + n2] * s
                
                fadd st0, st1                   ; st(0) = xi[k + n2] * s + xr[k + n2] * c = tr; 
                                                ; st(1) = xr[k + n2] * c;
                                                ; st(2) = s; st(3) = c

                fld dword[rdx + 4 * rbx]        ; st(0) = xi[k + n2]; st(1) = tr; st(2) = ...; 
                                                ; st(3) = s; st(4) = c
                fmul st0, st4                   ; st(0) = xi[k + n2] * c; st(1) = tr; 
                                                ; st(2) = xr[k + n2] * c; st(3) = s; st(4) = c

                fld dword[rsi + 4 * rbx]        ; st(0) = xr[k + n2]; st(1) = xi[k + n2] * c; st(2) = tr;
                                                ; st(3) = ...; st(4) = s; st(5) = c
                
                fmul st0, st4                   ; st(0) = xr[k + n2] * s
                fsub st1, st0                   ; ... st(1) = xi[k + n2] * c - xr[k + n2] * s = ti; 
                                                ; st(2) = tr; st(3) = ..; st(4) = s; st(5) = c

                fld dword[rsi + 4 * r12]        ; st(0) = xr[k]; st(1) = ..; st(2) = ti; 
                                                ; st(3) = tr; ... ;st(6) = c
                fsub st0, st3                   ; st(0) = xr[k] - tr
                
                fstp dword[rsi + 4 * rbx]       ; xr[k + n2] = xr[k] - tr
                                                ; st(0) = ...; st(1) = ti; st(2) = tr; 
                                                ; st(3) = ...; st(4) = s; st(5) = c
            
                fld dword[rdx + 4 * r12]        ; st(0) = xi[k]; ...; st(2) = ti; 
                                                ; st(3) = tr; ... ;st(6) = c
                fsub st0, st2                   ; st(0) = xi[k] - ti; ...
                fstp dword[rdx + 4 * rbx]       ; xi[k + n2] = xi[k] - ti ; ... ; st(5) = c

                fld dword[rsi + 4 * r12]        ; st(0) = xr[k]; ...; st(3) = tr ; ...; st(6) = c
                fadd st0, st3                   ; st(0) = xr[k] + tr
                fstp dword[rsi + 4 * r12]       ; xr[k] += tr; st(0) = ...; st(1) = ti ; st(5) = c
                
                fld dword[rdx + 4 * r12]        ; st(0) = xi[k]; ...; st(2) = ti; ... ;st(6) = c
                fadd st0, st2                   ; st(0) = xi[k] + ti
                fstp dword[rdx + 4 * r12]       ; xi[k] += ti ; ...; st(5) = c

                fstp qword[p] ; 5
                fstp qword[p] ; 4
                fstp qword[p] ; 3
                fstp qword[p] ; 2
                fstp qword[p] ; 1
                fstp qword[p] ; 0

                pop rbx
                pop rcx

                inc r11                         ; i++
                inc r12                         ; k++
                cmp r11, r13

                je .break3
                jmp .loop3
            .break3:

            add r12, r13                        ; k += n2
            cmp r12, r14

            jge .break2
            jmp .loop2
        .break2:
            xor rax, rax
            mov rax, r13

            push rdx
            mov r8, 2
            xor rdx, rdx
            div r8
            mov r13, rax                        ; n2 /= 2
            pop rdx

            inc rbx
            cmp ebx, [rdi] 

            je .break1
            jmp .loop1
    .break1:

    xor rbx, rbx                                ; k = 0
    ;; for (k = 0; k < n; k++)
    .loop_swap:
        xor rax, rax
        mov eax, [rdi + 4 * (rbx + 1)]          ; rax = i = mfft->bitreverse[k]    

        cmp rax, rbx
        jg .continue_swap
        inc rbx
        cmp rbx, r14
        je .break_swap
        jmp .loop_swap
        .continue_swap:

        xor r8, r8
        xor r9, r9
        mov r8d, [rsi + 4 * rbx]                ; r8 = xr[k] = tr
        mov r9d, [rdx + 4 * rbx]                ; r9 = xi[k] = ti

        xor r11, r11
        mov r11d, [rsi + 4 * rax]               ; r11 = xr[i]
        mov [rsi + 4 * rbx], r11d               ; xr[k] = xr[i]

        xor r11, r11
        mov r11d, [rdx + 4 * rax]               ; r11 = xi[i]
        mov [rdx + 4 * rbx], r11d               ; xi[k] = xi[i]

        mov [rsi + 4 * rax], r8d                ; xr[i] = tr
        mov [rdx + 4 * rax], r9d                ; xi[i] = ti

        inc rbx
        cmp rbx, r14

        je .break_swap
        jmp .loop_swap
    .break_swap:
    cmp rcx, 0
    jg .finish
    
    fld qword[o]                                ; st(0) = 1.0
    mov [p], r14d
    fild dword[p]                               ; st(0) = n; st(1) = 1
    fdiv st1, st0                               ; st(0) = n; st(1) = 1 / n
    fstp qword[p]                               ; st(0) = 1 / n = f

    xor rbx, rbx                                ; i = 0
    ;; for (i = 0; i < n; i++)
    .loop_finish:
        fld dword[rsi + 4 * rbx]                ; st(0) = xr[i]; st(1) = f
        fmul st0, st1                           ; st(0) = xr[i] * f; st(1) = f

        fstp dword[rsi + 4 * rbx]               ; xr[i] *= f; st(0) = f
        fld dword[rdx + 4 * rbx]                ; st(0) = xi[i]; st(1) = f
        fmul st0, st1                           ; st(0) = xi[i] * f; st(1) = f
        fstp dword[rdx + 4 * rbx]               ; xi[i] *= f; st(0) = f

        inc rbx                                 ; i++
        cmp rbx, r14

        je .break_finish
        jmp .loop_finish
    .break_finish:

    .finish:

    pop rbx
    pop rdi
    pop r10
    pop r11
    pop r12
    pop r13
    pop r14
    pop r15

    ret

;; Power x of y
;; Takes:   ST0 - x
;;          ST1 - y
;; Returns: ST0 - x^y
pow:
    fyl2x
    ftst
    fstsw ax
    fwait
    sahf
    fld1
    fscale
    fxch

    jae .st0positive
    jb  .st0negative

    .st0positive
        fsub qword [shift]
        fist dword [intNum]

        fadd qword [shift]
        fisub dword [intNum]
        jmp .done

    .st0negative
        fadd qword [shift]
        fist dword [intNum]

        fsub qword [shift]
        fisub dword [intNum]
        jmp .done
    .done

    f2xm1
    fld1
    faddp st1

    fmul st1
    ret

;; void get_note_pitch_table(int* note_table, float* freq_table, float* pitch_table)
;;      Fills note and pitch tables
;; Takes:   RDI - note_table
;;          RSI - freq_table
;;          RDX - pitch_table
get_note_pitch_table:

    push rbx
    push r15
    push r14
    
    ;push rdi
    ;call get_freq_table
    ;pop rdi
    ;mov rbx, rax                    ; freq_table in rbx
    ;push rdi
    ;push rbx
    ;mov rdi, [FFT_SIZE]
    ;call malloc                     ; poiner to pitch_table in rax
    ;pop rbx
    ;pop rdi
    fild dword[INIT]
    xor rcx, rcx

    .loop_init:
        fst dword[rdx + 4 * rcx]
        inc rcx

        cmp rcx, [FFT_SIZE]
        je .break_init
        jmp .loop_init
    .break_init:

    fstp qword[p]
    xor rcx, rcx
    ;; for (i = 0; i < 127, i++)
    .loop1:
        put_to_fpu 32               ; st(0) = 32
        put_to_fpu 440              ; st(0) = 440; st(1) = 32

        fdiv st0, st1               ; st(0) = 440 / 32; st(1) = 32
        put_to_fpu rcx              ; st(0) = i; st(1) = 440/32; st(2) = 32
        put_to_fpu 9                ; st(0) = 9; st(1) = i; st(2) = 440/32; st(3) = 32

        fsubp st1, st0              ; st(0) = i - 9; st(1) = 440/32; st(2) = 32
        put_to_fpu 12               ; st(0) = 12; st(1) = i - 9; ...

        fdivp st1, st0              ; st(0) = (i - 9) / 12; st(1) = 440/32; st(2) = 32

        push rax
        put_to_fpu 2

        call pow
        pop rax

        fstp st1       
        fmulp st1, st0              ; st(0) = pitch; st(1) = 32
        fild dword[SAMPLE_RATE]

        put_to_fpu 2
        fdivp st1, st0              ; st(0) = SAMPLE_RATE / 2; st(1) = pitch; st(2) = 32

        fcomp st1
        fwait

        push rdx
        push rax
        fstsw ax
        sahf
        pop rax
        pop rdx

        jbe .break1               
        fild dword[MIN]             ; st(0) = min; st(1) = pitch; st(2) = 32
        xor r15, r15                
        mov r15, -1                 ; index = -1
        xor r14, r14                ; j = 0
        
        ;; for (j = 0; j < FFT_SIZE; j++)
        .loop_brute:
            fld dword[rsi + 4 * r14]

            fsub st0, st2
            fabs                    ; st(0) = |freq_table[j] - pitch|; st(1) = min; st(2) = pitch
                                    ; st(3) = 32
            fcom st1
            fwait
            push rax
            push rbx
            
            fstsw ax
            sahf

            pop rbx
            pop rax

            jae .continue_brute
            fst st1                 ; st(0) = |...|; st(1) = min = |freq_table[j] - pitch|; st(2) = pitch; st(3) = 32
            mov r15, r14            ; index = j

            .continue_brute:
            fstp qword[p]           ; st(0) = min; ...

            inc r14                 ; j++
            cmp r14, [FFT_SIZE]

            je .break_brute
            jmp .loop_brute
        .break_brute:

        fstp qword[p]               ; st(0) = pitch; st(1) = 32
        fstp dword[rdx + 4 * r15]
        fstp qword[p]

        push rdx
        push rax

        xor rax, rax
        mov rdx, 0
        mov rax, rcx
        mov r8d, 12

        div r8d
        mov [rdi + 4 * r15], edx

        pop rax
        pop rdx

        inc rcx
        cmp rcx, 127

        je .break1
        jmp .loop1

    .break1:
    
    load_from_table mas1, 2143, 0
    load_from_table mas2, 2271, 1
    load_from_table mas3, 2406, 2
    load_from_table mas4, 2549, 3
    load_from_table mas5, 2700, 4
    load_from_table mas6, 2861, 5
    load_from_table mas7, 3031, 6
    load_from_table mas8, 3211, 7
    load_from_table mas9, 3402, 8
    load_from_table mas10, 3604, 9
    load_from_table mas11, 3819, 10
    load_from_table mas12, 4046, 11

    pop r14
    pop r15
    pop rbx

    ret

;; void get_freq_table(float* freq_table)
;;      Fills freq_table with frequences
;; Takes:   RDI - freq_table
get_freq_table:
    ;push rdi
    ;xor rdi, rdi
    ;mov rdi, [FFT_SIZE]
    ;call malloc                     ; poiner to freq_table in rax
    ;pop rdi                         

    xor rcx, rcx
    ;; for(i = 0; i < FFT_SIZE; i++)
    .loop:
        mov [p], ecx
        fild dword[p]               ; st(0) = i
        fmul dword[SAMPLE_RATE]     ; st(0) = i * SAMPLE_RATE
        fdiv dword[FFT_SIZE]        ; st(0) = i * SAMPLE_RATE / FFT_SIZE

        fstp dword[rdi + 4 * rcx]   ; freq_table[i]

        inc rcx
        cmp rcx, [FFT_SIZE]
        je .break

        jmp .loop

    .break:

    ret

;; void compute_second_low_pass_params(float* pair, float* a, float* b);
;;      Compute second order low pass parameters
;; Takes:   RDI - pait: state, f
;;          RSI - a
;;          RDX - b
compute_second_low_pass_params:
    fldpi                           ; st(0) = pi
    put_to_fpu 2

    fmulp st1, st0                  ; st(0) = 2 * pi
    fld dword[rdi + 4]                  ; st(0) = f
    fmulp st1, st0                  ; st(0) = 2 * pi * f
    fld dword[rdi]                  ; st(0) = state; st(1) = 2 * pi * f

    fdivp st1, st0                  ; st(0) = 2 * pi * f / state = w0
    fst st1                         ; st(0) = w0; st(1) = w0

    fcos                            ; st(0) = cos(w0) = cosw0
    fst st2
    fstp dword[p]

    fsin                            ; st(0) = sin(w0); st(1) = cos(w0)
    put_to_fpu 2                  
    fsqrt                           ; st(0) = sqrt(2); st(1) = sin(w0); st(2) = cos(w0)
    fmul st0, st1                    ; st(0) = sqrt(2) * sin(w0); ...

    put_to_fpu 2
    fdivp st1, st0                  ; st(0) = sqrt(2) * sin(w0) / 2 = alpha; st(1) = sin(w0); st(2) = cos(w0)
    fld1
    fadd st0, st1                   ; st(0) = 1 + alpha = a0; st(1) = alpha; st(2) = sin(w0); st(3) = cos(w0)

    put_to_fpu -2                   ; st(0) = -2; st(1) = a0; st(2) = alpha; st(3) = sin(w0); st(4) = cos(w0)                   
    fmul st0, st4
    fdiv st1
    fstp dword[rsi]                 ; a[0]; st(0) = a0; ...

    fld1
    fsub st0, st2
    fdiv st0, st1
    fstp dword[rsi + 4]             ; a[1]

    fld1
    fsub st0, st4

    put_to_fpu 2
    fdivp st1, st0
    fdiv st0, st1
    fstp dword[rdx]                 ; b[0]

    fld1
    fsub st0, st4
    fdiv st0, st1

    fstp dword[rdx + 4]             ; b[1]
    fld dword[rdx]
    fstp dword[rdx + 8]

    fstp qword[p]
    fstp qword[p]
    fstp qword[p]
    fstp qword[p]

    ret

;; float process_second_order_filter(float x, float *mem, float *a, float* b)
;;      Filter frequences
;; Takes:   XMM0 - x
;;          RDI - mem
;;          RSI - a
;;          RDX - b
;; Returns: XMM0 - new filtered value
process_second_order_filter:

    movss xmm1, xmm0
    movss xmm2, [rdx]
    mulss xmm1, xmm2                ; b[0] * x

    movss xmm2, [rdx + 4]
    movss xmm3, [rdi]
    mulss xmm2, xmm3
    addss xmm1, xmm2                ; b[0] * x + b[1] * mem[0]

    movss xmm2, [rdx + 8]
    movss xmm3, [rdi + 4]
    mulss xmm2, xmm3
    addss xmm1, xmm2                ; b[0] * x + b[1] * mem[0] + b[2] * mem[1]

    movss xmm2, [rsi]
    movss xmm3, [rdi + 8]
    mulss xmm2, xmm3
    subss xmm1, xmm2                ; -= a[0] * mem[2]

    movss xmm2, [rsi + 4]
    movss xmm3, [rdi + 12]
    mulss xmm2, xmm3
    subss xmm1, xmm2
    
    movss xmm2, [rdi]
    movd [rdi + 4], xmm2
    movd [rdi], xmm0

    movss xmm2, [rdi + 8]
    movd [rdi + 12], xmm2
    movd [rdi + 8], xmm1

    movss xmm0, xmm1

    ret

;; float find_peak_value(float* data, float* datai)
;;      Finds value of peak of frequences
;; Takes:   RDI - real part
;;          RSI - imagenary part
;; Returns: XMM0 - peak
find_peak_value:
    movss xmm0, [INIT]
    push rax

    xor rdx, rdx
    xor rax, rax

    mov rax, [FFT_SIZE];
    mov r8, 2
    div r8
    
    mov rdx, rax                    ; rdx = FFT_SIZE / 2
    pop rax

    xor rcx, rcx                    ; j = 0

    ;; for (j = 0; j < FFT_SIZE / 2; j++)
    .loop_peak_1:

        movss xmm1, [rdi + 4 * rcx]
        mulss xmm1, xmm1

        movss xmm2, [rsi + 4 * rcx]
        mulss xmm2, xmm2
        addss xmm1, xmm2            ; data[j]^2 + datai[j]^2

        movss xmm2, xmm1
        cmpnless xmm2, xmm0

        movq r8, xmm2
        cmp r8, 0

        je .continue_finding
        movss xmm0, xmm1

        .continue_finding:
        inc rcx
        cmp rcx, rdx

        je .break
        jmp .loop_peak_1
    
    .break:

    ret

;; int find_peak_index(float* data, float* datai)
;;      Finds index of peak of frequences
;; Takes:   RDI - real part
;;          RSI - imagenary part
;; Returns: RAX - peak's index
find_peak_index:
    mov rax, [INIT]
    movss xmm0, [INIT]

    push rax
    xor rdx, rdx
    xor rax, rax
    mov rax, [FFT_SIZE];

    mov r8, 2
    div r8

    mov rdx, rax                    ; rdx = FFT_SIZE / 2
    pop rax

    xor rcx, rcx                    ; j = 0

    ;; for (j = 0; j < FFT_SIZE / 2; j++)
    .loop_peak_1:
        movss xmm1, [rdi + 4 * rcx]
        mulss xmm1, xmm1

        movss xmm2, [rsi + 4 * rcx]
        mulss xmm2, xmm2
        addss xmm1, xmm2            ; data[j]^2 + datai[j]^2

        movss xmm2, xmm1
        cmpnless xmm2, xmm0

        movq r8, xmm2
        cmp r8, 0

        je .continue_finding
        mov rax, rcx
        movss xmm0, xmm1

        .continue_finding:
        inc rcx
        cmp rcx, rdx

        je .break

        jmp .loop_peak_1
    .break:
    ret

;; int find_nearest_note(int* note_table, int max_index)
;;      Finds the nearest note for index
;; Takes:   RDI - note_table
;;          RSI - max_index
;; Returns: RAX - the nearest note
find_nearest_note:
    push rbx
    xor rbx, rbx

    xor rax, rax
    .loop:
        cmp rax, rsi
        jge .else

        xor rdx, rdx
        mov rbx, rsi
        sub rbx, rax
        mov edx, [rdi + 4 * rbx]

        cmp edx, -1
        je .else

        neg rax

        jmp .break
        .else:

        mov rdx, rax
        add rdx, rsi
        cmp rdx, [FFT_SIZE]
        jge .continue

        xor rdx, rdx
        mov rbx, rsi
        add rbx, rax

        mov edx, [rdi + 4 * rbx]
        cmp edx, -1
        jne .break

        .continue:
        inc rax
        jmp .loop

    .break:
    
    pop rbx
    
    ret

;; void build_han_window(float* window, int size)
;;      Hann window function
;; Takes: RDI - poiter to result array
;;        RSI - size of window
build_han_window:

    xor rcx, rcx
    ;; for (i = 0; i < size; i++)
    .loop:
        fldpi                   ; st0 = pi
        put_to_fpu 2            ; st0 = 2; st1 = pi
        fmulp st1, st0          ; st0 = 2 * pi
        
        put_to_fpu rcx
        fmulp st1, st0
        put_to_fpu rsi
        
        fld1
        fsubp st1, st0
        fdivp st1, st0
        
        fcos
        fld1
        
        fsub st0, st1           ; st0 = 1 - cos(); st1 = cos()
        fld1
        put_to_fpu 2
        fdivp st1, st0          ; st0 = 0.5; 
        fmulp st1, st0
        
        fstp dword[rdi + 4 * rcx]
        fstp qword[p]
        
        cmp rcx, rsi
        je .break
        
        inc rcx
    .break:
    
    ret

section .data

    FFT_SIZE:   dq      8192
    SAMPLE_RATE:dq      8000

    INIT:       dq      -1
    MIN:        dq      1000000000
    shift:      dq      0.4999999999999
    
    msgAlloc:   db      "Could not allocate memory for FFT", 10, 0
    msgBits:    db      "Too many bits", 10, 0

    d:          dq      2.0
    o:          dq      1.0

    mas1:       dq      2093.004639
    mas2:       dq      2217.460938
    mas3:       dq      2349.318115
    mas4:       dq      2489.015869
    mas5:       dq      2637.020508
    mas6:       dq      2793.825928
    mas7:       dq      2959.955322
    mas8:       dq      3135.963379
    mas9:       dq      3322.437500
    mas10:      dq      3520.000000
    mas11:      dq      3729.310059
    mas12:      dq      3951.066406

section .bss
    p:          resq        1
    intNum:     resd        1
