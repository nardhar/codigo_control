/**
 * Generacion del codigo de control v7 para impuestos internos de Bolivia
 * 
 * Copyright (c) 2010 Felix A. Carreño B. felix.carreno@gmail.com
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
using System;

namespace CodigoControl
{
	public static class CodigoControl
	{
		// Verhoeff Digit table variables
		static int[,] table_d = new int[,]{
			{0,1,2,3,4,5,6,7,8,9},
			{1,2,3,4,0,6,7,8,9,5},
			{2,3,4,0,1,7,8,9,5,6},
			{3,4,0,1,2,8,9,5,6,7},
			{4,0,1,2,3,9,5,6,7,8},
			{5,9,8,7,6,0,4,3,2,1},
			{6,5,9,8,7,1,0,4,3,2},
			{7,6,5,9,8,2,1,0,4,3},
			{8,7,6,5,9,3,2,1,0,4},
			{9,8,7,6,5,4,3,2,1,0}
		};
		static int[,] table_p = new int[,]{
			{0,1,2,3,4,5,6,7,8,9},
			{1,5,7,6,2,8,3,0,9,4},
			{5,8,0,3,7,9,6,1,4,2},
			{8,9,1,6,0,4,3,5,2,7},
			{9,4,5,3,1,2,6,8,7,0},
			{4,2,8,6,5,7,3,9,0,1},
			{2,7,9,3,8,0,6,4,1,5},
			{7,0,4,6,9,1,3,2,5,8}
			};
		static int[] table_inv = new int[] { 0, 4, 3, 2, 1, 5, 6, 7, 8, 9 };

		public static string generar(string autorizacion, string numero, string nitci, string fecha, string monto, string llave)
		{
			// paso 1
			numero = verhoeff_add_recursive(numero, 2);
			nitci = verhoeff_add_recursive(nitci, 2);
			fecha = verhoeff_add_recursive(fecha, 2);
			monto = verhoeff_add_recursive(monto, 2);
			string suma = ((long)(long.Parse(numero) + long.Parse(nitci) + long.Parse(fecha) + long.Parse(monto))).ToString();
			suma = verhoeff_add_recursive(suma, 5);
			// paso2
			string digitos = "" + suma.Substring(suma.Length - 5, 5);
			int[] digitossum = new int[]{0, 0, 0, 0, 0};
			string[] cadenas = new string[]{"", "", "", "", ""};
			int inicio = 0;
			int x = 0;
			foreach (char d in digitos.ToCharArray()) {
				digitossum[x] = int.Parse(d.ToString()) + 1;
				cadenas[x] = llave.Substring(inicio, int.Parse(d.ToString()) + 1);
				inicio += int.Parse(d.ToString()) + 1;
				x++;
			}
			autorizacion += cadenas[0];
			numero += cadenas[1];
			nitci += cadenas[2];
			fecha += cadenas[3];
			monto += cadenas[4];
			// paso3
			string arc4 = allegedrc4(autorizacion + numero + nitci + fecha + monto, llave + digitos);
			// paso4
			long suma_total = 0;
			long[] sumas = new long[]{0, 0, 0, 0, 0};
			int strlen_arc4 = arc4.Length;
			for (int i = 0; i < strlen_arc4; i++) {
				x = (int)arc4[i];
				sumas[i % 5] += x;
				suma_total += x;
			}
			// paso5
			long total = 0;
			for (int i = 0; i < sumas.Length; i++ ) {
				total += suma_total * sumas[i] / digitossum[i];
			}
			string mensaje = big_base_convert(total, 64);
			string last = allegedrc4(mensaje, llave + digitos).Insert(2, "-").Insert(5, "-").Insert(8, "-");
			if (last.Length > 11)
			{
				last = last.Insert(11, "-");
			}
			return last;
		}

		private static string big_base_convert(long numero, long baseconv) {
			char[] dic = new char[]{
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
				'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
				'y', 'z', '+', '/' };
			long cociente = 1;
			long resto = 0;
			string palabra = "";
			while (cociente > 0) {
				cociente = numero / baseconv;
				resto = numero % baseconv;
				palabra = dic[resto] + palabra;
				numero = cociente;
			}
			return palabra;
		}
		private static void swap(ref int num1, ref int num2) {
			int temp = num2;
			num2 = num1;
			num1 = temp;
		}
		private static string allegedrc4(string mensaje, string llaverc4) {
			int[] state = new int[256];
			int x = 0;
			int y = 0;
			int index1 = 0;
			int index2 = 0;
			int nmen = 0;
			int i = 0;
			string cifrado = "";

			for (i = 0; i < 256; i++){
				state[i] = i;
			}
			
			int strlen_llave = llaverc4.Length;
			int strlen_mensaje = mensaje.Length;
			for (i = 0; i < 256; i++) {
				index2 = ( ((int)llaverc4[index1]) + state[i] + index2 ) % 256;
				swap(ref state[index2], ref state[i]);
				index1 = (index1 + 1) % strlen_llave;
			}
			string cadtemp = "";
			for (i = 0; i < strlen_mensaje; i++) {
				x = (x + 1) % 256;
				y = (state[x] + y) % 256;
				swap(ref state[y], ref state[x]);
				// ^ = XOR function
				nmen = (int)mensaje[i] ^ state[ ( state[x] + state[y] ) % 256];
				cadtemp = "0" + big_base_convert(nmen, 16);
				cifrado += cadtemp.Substring(cadtemp.Length - 2, 2);
			}
			return cifrado;
		}
		/**
		 * Digito Verhoeff
		 */
		private static int calcsum(string number) {
			int c = 0;
			string n = reverse(number);
		
			int len = n.Length;
			char[] nchar = n.ToCharArray();
			for (int i = 0; i < len; i++) {
				c = table_d[ c , table_p[ (i+1) % 8 , int.Parse(nchar[i].ToString()) ] ];
			}
		
			return table_inv[c];
		}
		private static string verhoeff_add_recursive(string number, int digits) {
			string temp = number;
			while (digits > 0) {
				temp += calcsum(temp);
				digits--;
			}
			return temp;
		}
		private static string reverse(string cadena)
		{
			char[] str = cadena.ToCharArray();
			Array.Reverse(str);
			return new string(str); 
		}
	}
}

