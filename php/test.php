<?php
// test de los 5000 casos de prueba usando SimpleTest
require_once('codigo_control.class.php');
require_once('simpletest/autorun.php');

class CodigoControlTest extends UnitTestCase {
    function testGeneracionCodigo() {
		$filename = dirname(__FILE__).'/5000CasosPruebaCCVer7.txt';
		$handle = fopen($filename, 'r');
		$contents = fread($handle, filesize($filename));
		fclose($handle);
		
		$filas = explode("\n", $contents);
		foreach ($filas as $fila) {
			if ($fila[0] != 'N' && $fila != '') {
				$factura = explode('|', $fila);
				$CodigoControl = new CodigoControl(
					$factura[0],
					$factura[1],
					$factura[2],
					str_replace('/', '', $factura[3]), // cambia la fecha 2007/08/10 a 20070810
					round(str_replace(',', '.', $factura[4]), 0), // cambia , a . y redondea el importe de la factura 208.95 a 209
					$factura[5]
					);
				$this->assertEqual($CodigoControl->generar(), $factura[10]);
			}
		}
    }
}
?>
