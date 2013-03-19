package ru.spbstu.telematics.student_Nikitin.lab2_ArrayQueue;

public class ArrayQueueElement <T> {
	
	private T data_;
	
	public ArrayQueueElement(T data)
	{
		setData_(data);
	}

	public T getData_()
	{
		return data_;
	}
	
	public void setData_(T data_)
	{
		this.data_ = data_;
	}
}