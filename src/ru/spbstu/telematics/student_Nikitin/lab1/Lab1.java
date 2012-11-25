package ru.spbstu.telematics.student_Nikitin.lab1;

import java.io.*;
import java.math.*;

/*
Программа считывает матрицу 5х5 и считает ее определитель.
*/

public class Lab1 {
	public static float[][] enterMatrix() throws Exception {
		// Исходная матрица
		float[][] matrix = new float[5][5]; 
		// Создадим объект класса буфферизованного чтения из потока,
		// в качеcтве потока выберим стандартный поток ввода (клавиатура)
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
		// Переменная в которую будем писать то, что ввели с клавиатуры
		String inputString;
		System.out.print("Введите элементы матрицы начиная с первой стоки (после каждого элемента жмем \"Enter\"):\n");			
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				System.out.print("A[" + i + "][" + j + "]=");
				// Используем метод чтения из потока до символа конца строки
				inputString = input.readLine();
				// Проверим является ли введенная строка числом типа float
				try
				{
				matrix[i][j] = Float.parseFloat(inputString);
				}
				catch(Exception e) {
				System.out.println("Ошибка перевода из строки в вещественное число.\n Вам придется повторить ввод.");
				// Возвращаемся к текущему элементу
				j--;
				}
			}
		}	
		return matrix;
	}
	
	// N-размерность матрицы, minorElementStringNumber - номер строки элемента, для которого вычисляется минор
	// minorElementColumnNumber - номер столбца элемента, для которого вычисляется минор
	public static float[][] minor(int minorElementStringNumber, int minorElementColumnNumber, int N, float[][] matrix){
		// Матрица - минор
  		float[][] minor = new float[N-1][N-1];
  		for(int h=0,i=0; i<N-1; i++,h++){
    			if(i == minorElementStringNumber) 
				h++;
    			for(int k=0,j=0; j<N-1; j++,k++){
      				if(k == minorElementColumnNumber) 
					k++;
      				minor[i][j] = matrix[h][k];
    			}
  		}
  		return minor;
	}
	
	// N-размерность матрицы, matrix - матрица, для которой будет вычислен определитель
	public static float det(int N,float[][] matrix){ 
		float det=0;
		if(N!=2)
			// Разложение по первой строке
			for(int i=0; i<N; i++){ 
				det += Math.pow(-1,i+2) * matrix[0][i] * det(N-1,minor(0,i,N,matrix));
    			}
   		else
     			det = matrix[0][0]*matrix[N-1][N-1]-matrix[N-1][0]*matrix[0][N-1];
		return det;
	}
	
	public static void main(String[] args) throws Exception {
		float[][] matrix = new float[5][5]; 
		matrix = enterMatrix();
		String matrixString = "";
		String tempString;
		System.out.println("Исходная матрица:\n");
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				tempString = matrixString;
				matrixString = tempString + matrix[i][j] + " ";
			}
			System.out.println(matrixString);
			matrixString = "";
		}
		System.out.println("Определитель матрицы равен " + det(5,matrix));
	}
}

