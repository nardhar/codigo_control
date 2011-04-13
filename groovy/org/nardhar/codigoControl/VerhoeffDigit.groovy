package org.nardhar.codigoControl

/**
 * Generacion del codigo de control v7 para impuestos internos de Bolivia
 * 
 * Copyright (c) 2010 Felix A. Carreño B. felix.carreno@gmail.com
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
class VerhoeffDigit {
    
	// Verhoeff Digit table variables
	def static verD = [ [0,1,2,3,4,5,6,7,8,9],
		[1,2,3,4,0,6,7,8,9,5],
		[2,3,4,0,1,7,8,9,5,6],
		[3,4,0,1,2,8,9,5,6,7],
		[4,0,1,2,3,9,5,6,7,8],
		[5,9,8,7,6,0,4,3,2,1],
		[6,5,9,8,7,1,0,4,3,2],
		[7,6,5,9,8,2,1,0,4,3],
		[8,7,6,5,9,3,2,1,0,4],
		[9,8,7,6,5,4,3,2,1,0]
		]
	def static verP = [ [0,1,2,3,4,5,6,7,8,9],
		[1,5,7,6,2,8,3,0,9,4],
		[5,8,0,3,7,9,6,1,4,2],
		[8,9,1,6,0,4,3,5,2,7],
		[9,4,5,3,1,2,6,8,7,0],
		[4,2,8,6,5,7,3,9,0,1],
		[2,7,9,3,8,0,6,4,1,5],
		[7,0,4,6,9,1,3,2,5,8]
		]
	def static verInv = [0,4,3,2,1,5,6,7,8,9]
	
    def static calcsum(Long number) {
    	return calcsum(number.toString())
    }
    
    def static calcsum(String number) {
        def c = 0
        def n = number.reverse()
        def len = n.size()
        (0..<len).each {
        	c = verD[ c ][ verP[ (it + 1) % 8 ][ n[it].toInteger() ] ]
        }
        return verInv[c]
    }
    
    def static addRecursive(Long number, digits) {
    	return addRecursive(number.toString(), digits).toLong()
    }
    
    def static addRecursive(String number, digits) {
        (1..digits).each {
            number += calcsum(number)
        }
        return number
    }
}
