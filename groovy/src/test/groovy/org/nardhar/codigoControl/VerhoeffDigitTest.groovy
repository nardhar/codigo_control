package org.nardhar.codigoControl

import spock.lang.Specification

class VerhoeffDigitTest extends Specification{

  // strings
  def "calculate single digit with strings 123456"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum('123456')
    
    then:
    digit == 8
  }

  def "calculate single digit with strings 1234567"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum('1234567')
    
    then:
    digit == 9
  }

  def "calculate single digit with strings 6547891"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum('6547891')
    
    then:
    digit == 5
  }

  def "calculate single digit with strings 147852"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum('147852')
    
    then:
    digit == 5
  }

  def "calculate single digit with strings 6589312"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum('6589312')
    
    then:
    digit == 7
  }

  // same but with numbers
  def "calculate single digit with numbers 123456"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum(123456)
    
    then:
    digit == 8
  }

  def "calculate single digit with numbers 1234567"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum(1234567)
    
    then:
    digit == 9
  }

  def "calculate single digit with numbers 6547891"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum(6547891)
    
    then:
    digit == 5
  }

  def "calculate single digit with numbers 147852"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum(147852)
    
    then:
    digit == 5
  }

  def "calculate single digit with numbers 6589312"() {
    setup:
    VerhoeffDigit vd = new VerhoeffDigit()
    
    when:
    def digit = vd.calcsum(6589312)
    
    then:
    digit == 7
  }
  
  // checking numbers for Impuestos Bolivia

}