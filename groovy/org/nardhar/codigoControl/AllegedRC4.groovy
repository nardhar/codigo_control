package org.nardhar.codigoControl

/**
 * Generacion del codigo de control v7 para impuestos internos de Bolivia
 * 
 * Copyright (c) 2011 Felix A. Carreño B. felix.carreno@gmail.com
 * 
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies or substantial portions of
 * the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
class AllegedRC4 {
	
    /**
     * Algoritmo Alleged RC4
     * @param string mensaje: mensaje a cifrar
     * @param string llave: llave a utilizar para el cifrado
     * @return string: cadena cifrada
     */
    def static hash(String mensaje, String llaverc4) {
        def state = []
        (0..255).each { state[it] = it }
        def x = 0
        def y = 0
        def index1 = 0
        def index2 = 0
        def nmen = 0
        def cifrado = ""
        
        def strlen_llave = llaverc4.size()
        def strlen_mensaje = mensaje.size()
        (0..255).each {
            index2 = ( ((int) llaverc4[index1]) + state[it] + index2 ) % 256
            //(state[it], state[index2]) = [state[index2], state[it]]
            def temp = state[index2]
            state[index2] = state[it]
            state[it] = temp
            
            index1 = (index1 + 1) % strlen_llave
        }
        (0..<strlen_mensaje).each {
            x = (x + 1) % 256
            y = (state[x] + y) % 256
            //(state[x], state[y]) = [state[y], state[x]]
            def temp = state[y]
            state[y] = state[x]
            state[x] = temp
            // ^ = XOR function
            nmen = ((int)mensaje[it]) ^ state[ ( state[x] + state[y] ) % 256]
            cifrado += ("0" + BaseConvert.toBase(nmen, 16))[-2..-1]
        }
        return cifrado
    }
}
