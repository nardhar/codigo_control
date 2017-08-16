# Libreria para la generación del codigo de control (Impuestos Nacionales Bolivia)
## 1. Instalación

**Gradle**

```groovy
compile 'org.nardhar:codigo-control:1.0'
```

## 2. Uso

Se puede usar como una libreria con closures

```groovy
def codigo = CodigoControl.generarCodigo {
  nit '4189179011'
  numero 1503
  autorizacion 29040011007
  fecha Date.parse('yyyyMMdd', '20070702')
  importe 2500.00
  llave '9rCB7Sv4X29d)5k7N%3ab89p-3(5[A'
}
```

o como una libreria estatica (para java)

```java
CodigoControl cc = new CodigoControl();
cc.setNit("4189179011");
cc.setNumero(1503);
cc.setAutorizacion(29040011007);
cc.setFecha(Date.parse("yyyyMMdd", "20070702"));
cc.setImporte(2500.00);
cc.setLlave("9rCB7Sv4X29d)5k7N%3ab89p-3(5[A");
String codigo = cc.generar();
```