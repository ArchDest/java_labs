package ru.spbstu.telematics.student_Nikitin.lab2_ArrayQueue;

import java.util.Iterator;

public class Main
{
	public static void main(String args[])
	{
		ArrayQueue <Integer> testQueue = new ArrayQueue <Integer> (); // Проверяем конструктор
		System.out.println("В очереди " + testQueue.size() + " объектов"); // Проверяем метод size() при нулевой очереди
		for(int i=0; i<10; i++)
			testQueue.add(i); // Проверяем метод add(T element), проверяем работу метода resizeQueue()s
		System.out.println("В очереди " + testQueue.size() + " объектов"); // Проверяем метод size() при непустой очереди
		System.out.println("Забираем из очереди объект со значением " + testQueue.get()); // Проверяем метод get()
		System.out.println("В очереди " + testQueue.size() + " объектов"); // Проверяем, что количество объектов в очереди уменьшилось на 1
		System.out.println("Забираем из очереди объект со значением " + testQueue.get()); // Проверяем метод get()
		System.out.println("В очереди " + testQueue.size() + " объектов"); // Проверяем, что количество объектов в очереди уменьшилось на 1
		System.out.println("Сдвинем очередь по кругу (влево) на 2 элемента:");
		testQueue.rotate(2); // Проверяем метод rotate()
		for(int i=0; i<8; i++)
			System.out.println("Забираем из очереди объект со значением " + testQueue.get()); // Проверяем сколько и в каком порядки элементов находились в очереди
		for(int i=0; i<10; i++)
			testQueue.add(i+10); // Добавим ещё 10 объектов для проверки итератора
		Iterator <Integer> iter = testQueue.iterator();
		System.out.println("Обходим очередь с помощью итератора:");
		while(iter.hasNext())
				System.out.println("Очередной элемент: " + iter.next());
		System.out.println("Заберем из очереди все элементы:");
		for(int i=0; i<10; i++)
			System.out.println("Забираем из очереди объект со значением " + testQueue.get());
		System.out.println("В очереди " + testQueue.size() + " объектов");
	}
}