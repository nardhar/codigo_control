<?php
require_once('codigo_control.class.php');

// Ejemplo de generacion
$CodigoControl = new CodigoControl(
	'29040011007',
	'1503',
	'4189179011',
	'20070702',
	'2500',
	'9rCB7Sv4X29d)5k7N%3ab89p-3(5[A'
	);
echo $CodigoControl->generar().'<br/>';
// 5 casos de prueba iniciales del codigo de control
$CodigoControl = new CodigoControl(
	'79040011859',
	'152',
	'1026469026',
	'20070728',
	'135',
	'A3Fs4s$)2cvD(eY667A5C4A2rsdf53kw9654E2B23s24df35F5'
	);
echo $CodigoControl->generar().'<br/>';
$CodigoControl = new CodigoControl(
	'20040010113',
	'665',
	'1004141023',
	'20070108',
	'905',
	'442F3w5AggG7644D737asd4BH5677sasdL4%44643(3C3674F4'
	);
echo $CodigoControl->generar().'<br/>';
$CodigoControl = new CodigoControl(
	'1904008691195',
	'978256',
	'0',
	'20080201',
	'26006',
	'pPgiFS%)v}@N4W3aQqqXCEHVS2[aDw_n%3)pFyU%bEB9)YXt%xNBub4@PZ4S9)ct'
	);
echo $CodigoControl->generar().'<br/>';
$CodigoControl = new CodigoControl(
	'10040010640',
	'9901',
	'1035012010',
	'20070813',
	'451',
	'DSrCB7Ssdfv4X29d)5k7N%3ab8p3S(asFG5YU8477SWW)FDAQA'
	);
echo $CodigoControl->generar().'<br/>';
$CodigoControl = new CodigoControl(
	'30040010595',
	'10015',
	'953387014',
	'20070825',
	'5726',
	'33E265B43C4435sdTuyBVssD355FC4A6F46sdQWasdA)d56666fDsmp9846636B3'
	);
echo $CodigoControl->generar().'<br/>';
?>
