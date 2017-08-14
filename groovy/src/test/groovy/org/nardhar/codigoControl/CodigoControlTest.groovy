package org.nardhar.codigoControl

import spock.lang.Specification

class CodigoControlTest extends Specification {

  def "ejemplo 1"() {
    setup:
    
    when:
    def codigo = CodigoControl.generarCodigo {
      nit '29040011007'
      numero 1503
      autorizacion 4189179011
      fecha Date.parse('yyyyMMdd', '20070702')
      importe 2500.00
      llave '9rCB7Sv4X29d)5k7N%3ab89p-3(5[A'
    }
    
    then:
    codigo == '123'
  }

  def "5000 casos"() {
    /*
    setup:
    File file = new File(getClass().getResource("sample.xlsx").getFile())
    WorkbookReader reader = new WorkbookReader(path: file.getAbsolutePath())
    ArrayList<Field> fieldList = [
      new Field(col: 0, name: 'number', valueType: ValueType.INTEGER, required: true),
      new Field(col: 1, name: 'decimal', valueType: ValueType.DECIMAL, required: true),
      new Field(col: 2, name: 'string', valueType: ValueType.STRING, required: true),
      new Field(col: 3, name: 'date', valueType: ValueType.DATE, required: true),
    ]
    def values = []
    SortedSet cellErrors = new TreeSet()
    
    when:
    reader.readSheet(sheetName: 'FROM_START', columnsToRead: fieldList.size()) { rowNumber, rowCellValueList ->
      // so it stops reading if no value is found in the first column
      if (!rowCellValueList.getAt(0).value) {
        return false
      }
      values << fieldList.inject([:]) { rowData, field ->
        rowData + [(field.name): rowCellValueList.getAt(field.col).getRealValue(field) { code ->
          cellErrors << new CellError(row: rowNumber, col: field.col, code: code, args: [])
        }]
      }
    }
    
    then:
    values.size() == 13 && values.every { value ->
      value instanceof LinkedHashMap && fieldList.every {
        value.containsKey(it.name)
      }
    } && cellErrors.size() == 0
    */
  }

}