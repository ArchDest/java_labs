package ru.spbstu.telematics.student_Nikitin.lab2_ArrayQueue;

import java.util.Iterator;
import java.lang.reflect.Array;

/**
 * Очередь, которая хранит данные внутри массива. Когда массив очереди заполняется объектами, 
 * массив переинициализируется большим в два раза объемом, старые объекты помещаются в новый массив 
 * и заполнение очереди продолжается 
 */

@SuppressWarnings("unchecked")
public class ArrayQueue	<T> implements IArrayQueue <T>, Iterable <T>
{
	private int defaultLength; //размер очереди по умолчанию
	private int size; //количество элементов в очереди
	private ArrayQueueElement <T> [] queue;
	
	public ArrayQueue()
	{
		defaultLength = 4;
		size = 0;
		queue = (ArrayQueueElement <T> []) Array.newInstance(ArrayQueueElement.class, defaultLength);
	}
	
	public Iterator <T> iterator()
	{
		return new ArrayListIterator();
	}
	
	public class ArrayListIterator implements Iterator <T>
	{
		private int iterPos = 0;
		
		public boolean hasNext() 
		{
			if (iterPos < queue.length)
				return true;
			else
				return false;
		}
		
		public T next()
		{
			if (queue[iterPos]==null){
				iterPos++;
				return null;
			}
			ArrayQueueElement <T> nextVal = queue[iterPos];
			iterPos ++;			
			return nextVal.getData_();
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub			
		}
	}

    /**
     * Добавляет объект в очередь
     * @param o
     */
    public void add(T element)
    {
    	queue[size] = new ArrayQueueElement(element);
    	size ++;
    	// Если очередь заполнена - увеличим её размер в два раза
    	if(size == queue.length)
    		resizeQueue();
    }    

    /**
     * Забирает объект из очереди. Возвращаеммый объект удаляется.
     * @return
     */
    public T get()
    {
    	if(queue[0] != null)
    	{
    		ArrayQueueElement <T> element = queue[0];
    		for(int i=1; i<size; i++)
    			queue[i-1] = queue[i];  
    		queue[size-1] = null;
    		size --;
    		return element.getData_();
    	}
    	else 
    		return null;
    }

    /**
     * Возвращает размер очереди
     * @return
     */
    public int size()
    {
    	return this.size;
    }

    /**
     * Циклический сдвиг в массиве на заданное количество элементов
     * Пояснение: метод переносит первые posNum элементов в конец очереди 
     */
    public void rotate(int posNum)
    {
    	for(int i=0; i<posNum; i++)
    		add(get());
    }
    
    /**
     * Увеличение размера очереди в два раза 
     */    
    private void resizeQueue()
    {
    	int oldLength = queue.length;
    	int oldSize = this.size;
    	ArrayQueueElement <T> [] tempQueue;
    	
    	tempQueue = (ArrayQueueElement <T> []) Array.newInstance(ArrayQueueElement.class, oldLength*2);
    	
    	for(int i=0; i<oldSize; i++)
    	{
    		tempQueue[i] = queue[i];  																																						
    	}
    	
    	queue = (ArrayQueueElement <T> []) Array.newInstance(ArrayQueueElement.class, oldLength*2);
    	queue = tempQueue;
    }
}
