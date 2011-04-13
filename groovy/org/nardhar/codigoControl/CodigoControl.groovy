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
class CodigoControl {
	
    def static generar = { params ->
        def autorizacion = params.autorizacion.toString()
        def factura = params.factura.toString()
        def nitci = params.nitci.toString()
        def fecha = params.fecha.toString()
        def monto = params.monto.toString()
        def llave = params.llave.toString()
        // paso 1
        factura = VerhoeffDigit.addRecursive(factura, 2)
        nitci = VerhoeffDigit.addRecursive(nitci, 2)
        fecha = VerhoeffDigit.addRecursive(fecha, 2)
        monto = VerhoeffDigit.addRecursive(monto, 2)
        def suma = factura.toBigDecimal() + nitci.toBigDecimal() + fecha.toBigDecimal() + monto.toBigDecimal()
        suma = VerhoeffDigit.addRecursive(suma.toString(), 5).toBigDecimal()
        // paso2
        def digitos = suma.toString()[-5..-1]
        
        def digitossum = []
        def cadenas = []
        def inicio = 0
        digitos[-5..-1].each { d ->
            digitossum.add(d.toInteger() + 1)
            cadenas.add(llave[inicio..(inicio + d.toInteger())])
            inicio += d.toInteger() + 1
        }
        
        autorizacion += cadenas[0]
        factura += cadenas[1]
        nitci += cadenas[2]
        fecha += cadenas[3]
        monto += cadenas[4]
        // paso3
        def arc4 = AllegedRC4.hash(autorizacion + factura + nitci + fecha + monto, llave + digitos)
        // paso4
        def suma_total = 0
        def sumas = [0, 0, 0, 0, 0]
        def strlen_arc4 = arc4.size()
        (0..<strlen_arc4).each {
            def x = ((int)arc4[it])
            sumas[it % 5] += x
            suma_total += x
        }
        // paso5
        def total = 0
        sumas.eachWithIndex { sp, i ->
        	// convierte a Long porque al dividir long/long devuelve BigDecimal
        	total += ((suma_total.toLong() * sp.toLong()) / digitossum[i].toLong()).toLong()
        }
        def mensaje = BaseConvert.toBase(total, 64)
        def result = ''
        def arr = [2,4,6,8,10]
        AllegedRC4.hash(mensaje, llave + digitos).toList().eachWithIndex { val, k ->
        	// agrega los guiones en las posiciones 2,4,6,8,10 (si existen)
        	result += (arr.contains(k) ? '-' : '') + val
        }
        return result
    }
}
