import java.util.ArrayList;
import java.util.EmptyStackException;


/**
 * 
 */

/**
 * @author Aaron Zheng
 *
 */
public class GenericStack<E> {

	private ArrayList<E> stack = new ArrayList<E>();
	/**
	 * You need to implement the following functions
	 * a) isEmpty() - returns true if the element is empty
	 * b) getSize() - returns the size of the Stack
	 * c) peek() - returns the object that is at the top of the stack
	 * d) pop() - gets the object at the top of stack, then removes it from 
	 *            the stack and returns the object
	 * e) push(o) - adds the object to the top of stack
	 */
	//returns whether or not the stack is empty.
	public boolean isEmpty() {
		return(stack.size() == 0);
	}
	//returns the size of the stack
	public int getSize() {
		return stack.size();

	}
	//returns the topmost object in the stack
	public E peek(){
		if(stack.isEmpty()) {
			throw new EmptyStackException();
		}
		return stack.get(stack.size()-1);
		
	}
	//returns the topmost object in the stack and deletes it from the stack
	public E pop() {
		if(stack.isEmpty()) {
			throw new EmptyStackException();
		}
		
		E temp;
		temp = stack.get(stack.size() -1);
		stack.remove(stack.size()-1);
		return temp;
	}
	//adds a new object on top of the stack
	public void push(E o) {
		stack.add(o);
	}
	//toString method
	public String toString() {
		return stack.toString();
	}
}
