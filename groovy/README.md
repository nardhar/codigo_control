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
  nit '1234567012'
  razonSocial 'nombre_de_cliente'
  numero 1564
  autorizacion '1234567890123'
  fecha objetoFecha
  importe 123.45
  llave llave
}
```

o como una libreria estatica (para java)

```java
CodigoControl cc = new CodigoControl();
cc.setNit("1234567012");
cc.setRazonSocial("nombre_de_cliente");
cc.setNumero(1564);
cc.setAutorizacion("1234567890123");
cc.setFecha(Date.parse("yyyy-MM-dd", "2017-08-14"));
cc.setImporte(123.45);
cc.setLlave("");
String codigo = cc.generar();
```

## 3. Api

```groovy
CodigoControl()
```

|Parámetro|Tipo    |Valor por defecto|Descripción|
|---------|--------|-----------------|-----------|
|path|String (Obligatorio)|''|Ruta del archivo que se desea importar|
|rowCacheSize|Integer (Opcional)|100|cantidad de filas en caché|
|bufferSize|Integer (Opcional)|4096|cantidad de bytes para el buffer de lectura|

```groovy
WorkbookReader.readSheet(LinkedHashMap params, Closure rowReadCallback)
```

|Parámetro|Tipo    |Valor por defecto|Descripción|
|---------|--------|-----------------|-----------|
|params.sheetName|String (Obligatorio)|''|nombre de la hoja a leer|
|params.rowStart|Integer (Opcional)|0|Numero de fila donde se empieza a leer (se usa índice 0)|
|params.columnsToRead|Integer (Opcional)|1|Cantidad de columnas a leer|
|params.onRowCount|Closure (Opcional)|null|Indica que se debe contar las filas antes de leer el archivo (que en realidad es solo una lectura del archivo pero solo para contar las filas), como parámetro del Closure se envía el total de filas encontradas|
|params.useInputStream|Boolean (Opcional)|false|Indica si se debe usar un FileInputStream() para leer el archivo o solo un File()|
|rowReadCallback|Closure (Obligatorio)|null|Método a ejecutar cada vez que se lee una fila, los parametros son el numero de fila y los valores de la fila leida como ArrayList<CellValue>|

```groovy
WorkbookReader.countSheetRows(String sheetName)
```

Si solo se quiere contar las filas sin leer el libro

|Parámetro|Tipo    |Valor por defecto|Descripción|
|---------|--------|-----------------|-----------|
|sheetName|String (Obligatorio)|''|nombre de la hoja a leer|

lo cual retorna el total de filas encontradas

```groovy
Field()
```

Para configurar la lectura del archivo usando la clase Field 

|Parámetro|Tipo    |Descripción|
|---------|--------|-----------|
|col|Integer|Número de columna con indice 0|
|name|String|Nombre de la columna, debe ser única para poder almacenar los valores de cada fila en un Map|
|valueType|ValueType|Tipo de valor de la columna|
|required|Boolean|Indica si el valor leido no puede estar vacío|

## 4. Publicación de libreria en artifactory

Se usa el plugin de publicación de artefactos

```groovy
com.jfrog.artifactory
```

Configurar nombre de usuario y contraseña de artifactory en ```~/.gradle/gradle.properties```:

```properties
artifactory_lib_release_username=myusername
artifactory_lib_release_password=mypassword
```

luego ejecutar el siguiente comando

```bash
$ gradle artifactoryPublish
```
