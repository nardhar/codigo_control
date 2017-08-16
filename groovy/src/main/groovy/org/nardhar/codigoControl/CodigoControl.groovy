package org.nardhar.codigoControl

/**
 * Generacion del codigo de control v7 para impuestos internos de Bolivia
 * 
 * Copyright (c) 2017 Felix A. Carreño B. felix.carreno@gmail.com
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

import java.text.NumberFormat
import java.text.DecimalFormat

class CodigoControl {
  
  String nit
  Long numero
  Long autorizacion
  Date fecha
  BigDecimal importe
  String llave
  
  static String generarCodigo(Closure action) {
    def myInstance = new CodigoControl()
    myInstance.with(action)
    myInstance.generar()
  }

  void nit(String value) {
    this.nit = value
  }

  void numero(Long value) {
    this.numero = value
  }

  void autorizacion(Long value) {
    this.autorizacion = value
  }

  void fecha(Date value) {
    this.fecha = value
  }

  void importe(BigDecimal value) {
    this.importe = value
  }

  void llave(String value) {
    this.llave = value
  }

  String generar() {
    // convierte los parametros a cadena
    def autorizacion = this.autorizacion.toString()
    def factura = this.numero.toString()
    def nitci = this.nit ?: '0'
    def fecha = this.fecha.format('yyyyMMdd')
    def monto = new DecimalFormat('#0').format(this.importe.setScale(0, BigDecimal.ROUND_HALF_UP))
    def llave = this.llave
    
    // paso 1
    factura = verhoeffDigit(factura, 2)
    nitci = verhoeffDigit(nitci, 2)
    fecha = verhoeffDigit(fecha, 2)
    monto = verhoeffDigit(monto, 2)
    def suma = factura.toBigDecimal() + nitci.toBigDecimal() + fecha.toBigDecimal() + monto.toBigDecimal()
    suma = verhoeffDigit(suma.toString(), 5).toBigDecimal()
    
    // paso2
    def digitos = suma.toString()[-5..-1]
    
    def digitossum = []
    def cadenas = []
    
    digitos[-5..-1].toList().inject(0) { inicio, val ->
      digitossum.add(val.toInteger() + 1)
      //cadenas.add(llave[inicio..(inicio + val.toInteger())])
      cadenas.add(llave.substring(inicio, inicio + val.toInteger() + 1))
      inicio + val.toInteger() + 1
    }
    
    autorizacion += cadenas[0]
    factura += cadenas[1]
    nitci += cadenas[2]
    fecha += cadenas[3]
    monto += cadenas[4]
    // paso3
    def arc4 = allegedRC4(autorizacion + factura + nitci + fecha + monto, llave + digitos)
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
    // y agrega los guiones, el split crea un array cada dos caracteres, el join lo une con un guion
    allegedRC4(toBase(total, 64), llave + digitos).split("(?<=\\G..)").join('-');
  }

  String allegedRC4(String mensaje, String llaverc4) {
    def state = (0..255).toList()
    def index1 = 0
    def index2 = 0
    def strlen_llave = llaverc4.size()
    
    256.times {
      index2 = ( ((int) llaverc4[index1]) + state[it] + index2 ) % 256
      //(state[it], state[index2]) = [state[index2], state[it]]
      def temp = state[index2]
      state[index2] = state[it]
      state[it] = temp
      
      index1 = (index1 + 1) % strlen_llave
    }
    
    def x = 0
    def y = 0
    def nmen = 0
    def strlen_mensaje = mensaje.size()
    (1..strlen_mensaje).inject('') { cifrado, val ->
      x = (x + 1) % 256
      y = (state[x] + y) % 256
      //(state[x], state[y]) = [state[y], state[x]]
      def temp = state[y]
      state[y] = state[x]
      state[x] = temp
      // ^ = XOR function
      nmen = ((int)mensaje[val - 1]) ^ state[( state[x] + state[y] ) % 256]
      cifrado + ('0' + toBase(nmen, 16))[-2..-1]
    }
  }
  
  String toBase(String numero, Integer base = 64) {
    toBase(numero.toLong(), base)
  }
  
  String toBase(Long numero, Integer base = 64) {
    def dic = [
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
        'y', 'z', '+', '/'
      ]
      def cociente = 1
      def resto = 0
      def palabra = ''
      while (cociente > 0) {
        cociente = numero / base
        resto = numero % base
        palabra = dic[resto.toInteger()] + palabra
        numero = cociente.toInteger()
      }
      palabra.size() > 1 ? palabra.replaceAll(/^0+(.*)/, '$1') : palabra
  }

  String verhoeffDigit(String value, Integer digits = 1) {
    new VerhoeffDigit().addRecursive(value, digits)
  }

  Long verhoeffDigit(Long value, Integer digits = 1) {
    new VerhoeffDigit().addRecursive(value, digits)
  }

}
