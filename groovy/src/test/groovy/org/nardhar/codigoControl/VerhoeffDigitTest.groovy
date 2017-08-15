package org.nardhar.codigoControl

import spock.lang.Specification

class VerhoeffDigitTest extends Specification{

  def "calculate single digit with strings"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum('123456')
    
    then:
    digit == 8
  }

}