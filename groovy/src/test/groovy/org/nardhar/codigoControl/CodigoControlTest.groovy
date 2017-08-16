package org.nardhar.codigoControl

import spock.lang.Specification

class CodigoControlTest extends Specification {

  // tests de funciones utilitarias
  def "conversion de base - base 10 a base 16"() {
      when:
      def valor15 = new CodigoControl().toBase(15, 16)
      
      then:
      valor15 == 'F'

      when:
      def valor32 = new CodigoControl().toBase(32, 16)
      
      then:
      valor32 == '20'

      when:
      def valor500 = new CodigoControl().toBase(500, 16)
      
      then:
      valor500 == '1F4'
  }

  def "conversion de base - base 10 a base 64"() {
      when:
      def valor15 = new CodigoControl().toBase(15, 64)
      
      then:
      valor15 == 'F'

      when:
      def valor19058106 = new CodigoControl().toBase(19058106, 64)
      
      then:
      valor19058106 == '18isw'

      when:
      def valor14142416 = new CodigoControl().toBase(14142416, 64)
      
      then:
      valor14142416 == 'rylG'
  }
  
  def "codigo allegedRC4"() {
      when:
      def codigo1 = new CodigoControl().allegedRC4('290400110079rCB7Sv4150312X24189179011589d)5k7N2007070201%3a250031b8', '9rCB7Sv4X29d)5k7N%3ab89p-3(5[A71621')
      
      then:
      codigo1 == '69DD0A42536C9900C4AE6484726C122ABDBF95D80A4BA403FB7834B3EC2A88595E2149A3D965923BA4547B42B9528AAE7B8CFB9996BA2B58516913057C9D791B6B748A'
  }
  
  // proceso de generacion
  def "generacion codigo - proceso ejemplo inicial"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '4189179011'
      numero 1503
      autorizacion 29040011007
      fecha Date.parse('yyyyMMdd', '20070702')
      importe 2500.00
      llave '9rCB7Sv4X29d)5k7N%3ab89p-3(5[A'
    }
    
    then:
    codigo == '6A-DC-53-05-14'
  }

  def "generacion codigo - proceso ejemplo 1"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '1026469026'
      numero 152
      autorizacion 79040011859
      fecha Date.parse('yyyyMMdd', '20070728')
      importe 135.00
      llave 'A3Fs4s$)2cvD(eY667A5C4A2rsdf53kw9654E2B23s24df35F5'
    }
    
    then:
    codigo == 'FB-A6-E4-78'
  }

  def "generacion codigo - proceso ejemplo 2"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '1004141023'
      numero 665
      autorizacion 20040010113
      fecha Date.parse('yyyyMMdd', '20070108')
      importe 905.23
      llave '442F3w5AggG7644D737asd4BH5677sasdL4%44643(3C3674F4'
    }
    
    then:
    codigo == '71-D5-61-C8'
  }

  def "generacion codigo - proceso ejemplo 3"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '0'
      numero 978256
      autorizacion 1904008691195
      fecha Date.parse('yyyyMMdd', '20080201')
      importe 26006
      llave 'pPgiFS%)v}@N4W3aQqqXCEHVS2[aDw_n%3)pFyU%bEB9)YXt%xNBub4@PZ4S9)ct'
    }
    
    then:
    codigo == '62-12-AF-1B'
  }

  def "generacion codigo - proceso ejemplo 4"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '1035012010'
      numero 9901
      autorizacion 10040010640
      fecha Date.parse('yyyyMMdd', '20070813')
      importe 451.49
      llave 'DSrCB7Ssdfv4X29d)5k7N%3ab8p3S(asFG5YU8477SWW)FDAQA'
    }
    
    then:
    codigo == '6A-50-31-01-32'
  }

  def "generacion codigo - proceso ejemplo 5"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '953387014'
      numero 10015
      autorizacion 30040010595
      fecha Date.parse('yyyyMMdd', '20070825')
      importe 5725.90
      llave '33E265B43C4435sdTuyBVssD355FC4A6F46sdQWasdA)d56666fDsmp9846636B3'
    }
    
    then:
    codigo == 'A8-6B-FD-82-16'
  }

  // 5000 casos de prueba
  def "5000 casos"() {
    setup:
    File file = new File(getClass().getResource('5000CasosPruebaCCVer7.txt').getFile())
    
    when:
    def resultado = file.readLines().inject([]) { acc, val ->
      if (val.matches(/^[0-9]+.+/)) {
        def datos = val.tokenize('|')
        
        def codigoGenerado = CodigoControl.generarCodigo {
          nit datos.getAt(2)
          numero datos.getAt(1).toLong()
          autorizacion datos.getAt(0).toLong()
          fecha Date.parse('yyyy/MM/dd', datos.getAt(3))
          importe datos.getAt(4).replaceAll(',', '.').toBigDecimal().setScale(0, BigDecimal.ROUND_HALF_UP)
          llave datos.getAt(5)
        }
        return acc + [[generado: codigoGenerado, leido: datos.getAt(10)]]
      }
      acc
    }
    
    then:
    resultado.size() == 5000 && resultado.every {
      it.generado == it.leido
    }
  }

}