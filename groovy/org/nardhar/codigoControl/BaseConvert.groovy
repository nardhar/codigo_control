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
class BaseConvert {
    
    /**
     * Conversion de numeros en base 10 a base 64 o 16
     * @param string numero: numero a convertir
     * @param string base: Opcional, convierte a la base indicada
     * @return string: numero convertido
     */
    def static toBase(String numero, Integer base = 64) {
        return toBase(numero.toLong(), base)
    }
    
    def static toBase(Long numero, Integer base = 64) {
    	def dic = [
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
            'y', 'z', '+', '/' ]
        def cociente = 1
        def resto = 0
        def palabra = ""
        while (cociente > 0) {
            cociente = numero / base
            resto = numero % base
            palabra = dic[resto.toInteger()] + palabra
            numero = cociente.toInteger()
        }
        if (palabra.size() > 1) {
        	// quitando ceros de la izquierda
			while (palabra[0] == '0') {
				palabra = palabra.substring(1)
			}
		}
        return palabra
    }
}
