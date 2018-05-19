package edu.jhun.mediator;

public abstract class AbstractMediator {
	//所有中介者都存在的调用方法
	public abstract void dispatchCenter(String event, Object...objects);
}
