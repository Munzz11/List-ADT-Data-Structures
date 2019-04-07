/*
 * Dave Rosenberg
 * Comp 2000 - Data Structures
 * Lab 4: List ADT 
 * Spring, 2019
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post or otherwise share this code with anyone other than
 * current students in my sections of this course. Violation of these usage
 * restrictions will be considered a violation of the Wentworth Institute of
 * Technology Academic Honesty Policy.
 */

package edu.wit.dcsn.comp2000.listadt ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator ;
import java.util.Random;

/*
 * NOTE: Make sure you add Javadoc comments to every method
 * hi
 */

/**
 * A class that implements the ADT list by using a chain of nodes that has both a head
 * reference and a tail reference.
 * <p>
 * Revised: 2018-07-22 by David M Rosenberg<br>
 * Revised: 2018-07-11 by David M Rosenberg
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 * 
 * @param <T>
 *        the class (or an ancestor class) of objects to be stored in the list 
 *        such that T implements the Comparable interface.
 */
public class LinkedListPlus<T extends Comparable<? super T>>
                           implements ListInterface<T>, Iterable<T>
	{

	private Node firstNode ;		// Head reference to first node
	private Node lastNode ;			// Tail reference to last node
	private int	 numberOfEntries ;


	/**
	 * 
	 */
	public LinkedListPlus()
		{
		initializeDataFields() ;
		
		}	// end default constructor


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#add()
	 */
	@Override
	public void add( T newEntry )
		{
		Node newNode = new Node( newEntry ) ;

		if ( isEmpty() )
			firstNode = newNode ;
		else
			lastNode.setNextNode( newNode ) ;

		lastNode = newNode ;
		numberOfEntries++ ;
		
		}	// end add()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#add()
	 */
	@Override
	public void add( int newPosition,
	                 T newEntry )
		{
		if ( ( newPosition >= 0 ) && ( newPosition <= numberOfEntries + 1 ) )
			{
			Node newNode = new Node( newEntry ) ;

			if ( isEmpty() )
				{
				firstNode = newNode ;
				lastNode = newNode ;
				}
			else if ( newPosition == 0 )
				{
				newNode.setNextNode( firstNode ) ;
				firstNode = newNode ;
				}
			else if ( newPosition == numberOfEntries + 1 )
				{
				lastNode.setNextNode( newNode ) ;
				lastNode = newNode ;
				}
			else
				{
				Node nodeBefore = getNodeAt( newPosition - 1 ) ;
				Node nodeAfter = nodeBefore.getNextNode() ;
				newNode.setNextNode( nodeAfter ) ;
				nodeBefore.setNextNode( newNode ) ;
				} // end if
			numberOfEntries++ ;
			}
		else
			throw new IndexOutOfBoundsException( "Illegal position given to add operation." ) ;
		
		}	// end add()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#clear()
	 */
	@Override
	public void clear()
		{
		initializeDataFields() ;
		
		}	// end clear()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#contains()
	 */
	@Override
	public boolean contains( T anEntry )
		{
		boolean found = false ;
		Node currentNode = firstNode ;

		while ( !found && ( currentNode != null ) )
			{
			if ( anEntry.equals( currentNode.getData() ) )
				found = true ;
			else
				currentNode = currentNode.getNextNode() ;
			} // end while

		return found ;
		
		}	// end contains()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#getEntry()
	 */
	@Override
	public T getEntry( int givenPosition )
		{
		if ( ( givenPosition >= 0 ) && ( givenPosition <= numberOfEntries ) )
			{
			assert !isEmpty() ;
			return getNodeAt( givenPosition ).getData() ;
			}
		else
			throw new IndexOutOfBoundsException( "Illegal position given to getEntry operation." ) ;
		}	// end getEntry()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#getLength()
	 */
	@Override
	public int getLength()
		{
		return numberOfEntries ;
		
		}	// end getLength()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#isEmpty()
	 */
	@Override
	public boolean isEmpty()
		{
		boolean result ;

		if ( numberOfEntries == 0 ) // Or getLength() == 0
			{
			assert ( firstNode == null ) && ( lastNode == null ) ;
			result = true ;
			}
		else
			{
			assert ( firstNode != null ) && ( lastNode != null ) ;
			result = false ;
			} // end if

		return result ;
		
		}	// end isEmpty()


	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator()
		{
		// TODO Auto-generated method stub
		
		return null ;
		
		}	// end iterator()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#remove()
	 */
	@Override
	public T remove( int givenPosition )
		{
		T result = null ;                           // Return value

		if ( ( givenPosition >= 0 ) && ( givenPosition <= numberOfEntries ) )
			{
			assert !isEmpty() ;
			if ( givenPosition == 0 )                 // Case 1: Remove first entry
				{
				result = firstNode.getData() ;        // Save entry to be removed
				firstNode = firstNode.getNextNode() ;
				if ( numberOfEntries == 1 )
					lastNode = null ;                  // Solitary entry was removed
				}
			else                                 // Case 2: Not first entry
				{
				Node nodeBefore = getNodeAt( givenPosition - 1 ) ;
				Node nodeToRemove = nodeBefore.getNextNode() ;
				Node nodeAfter = nodeToRemove.getNextNode() ;
				nodeBefore.setNextNode( nodeAfter ) ;// Disconnect the node to be removed
				result = nodeToRemove.getData() ;  // Save entry to be removed

				if ( givenPosition == numberOfEntries )
					lastNode = nodeBefore ;         // Last node was removed
				} // end if

			numberOfEntries-- ;
			}
		else
			throw new IndexOutOfBoundsException( "Illegal position given to remove operation." ) ;

		return result ;                             // Return removed entry
		
		}	// end remove()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#replace()
	 */
	@Override
	public T replace( int givenPosition,
	                  T newEntry )
		{
		if ( ( givenPosition >= 0 ) && ( givenPosition <= numberOfEntries ) )
			{
			assert !isEmpty() ;

			Node desiredNode = getNodeAt( givenPosition ) ;
			T originalEntry = desiredNode.getData() ;
			desiredNode.setData( newEntry ) ;
			return originalEntry ;
			}
		else
			throw new IndexOutOfBoundsException( "Illegal position given to replace operation." ) ;
		
		}	// end replace()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#shuffle()
	 */
	@Override
	public void shuffle()
		{
		ArrayList half1 = new ArrayList();
		ArrayList half2 = new ArrayList();
		
		int j = numberOfEntries;
		for(int i = 0;i<j;i++)
		{
		if(i<=(j/2))
		{
			T temp = remove(0);
			half1.add(temp);
		}
		else
		{
			T temp = remove(0);
			half2.add(temp);	
		}
			
		}
		System.out.println(numberOfEntries);
		System.out.println(half1);
		System.out.print(half2);
		
		for(int i = 0;i <= j-1;i++ )
		{
			int half1Count =0;
			int half2Count =0;
			if(i % 2 == 0)
			{
				System.out.println("adding from half1");
				add((T) half1.get(half1Count));
				half1.remove(half1Count);
				half1Count++;
			}
			else
			{
				System.out.println("adding from half2");
				add((T) half2.get(half2Count));
				half2.remove(half2Count);
				half2Count++;
			}
		}
		}	// end shuffle()


	/* (non-Javadoc)
	 * @see edu.wit.dcsn.comp2000.listadt.ListInterface#sort()
	 */
	@Override
	public void sort()
		{
		// TODO Auto-generated method stub
		
		}	// end sort()


	/**
	 * 
	 * @return
	 */
	public T[] toArray()
		{
		// The cast is safe because the new array contains null entries
		@SuppressWarnings( "unchecked" )
		//T[] result = ( T[] ) new Object[ numberOfEntries ] ;
		T[] result = (T[]) new Comparable[numberOfEntries];

		int index = 0 ;
		Node currentNode = firstNode ;
		while ( ( index < numberOfEntries ) && ( currentNode != null ) )
			{
			result[ index ] = currentNode.getData() ;
			currentNode = currentNode.getNextNode() ;
			index++ ;
			} // end while

		return result ;
		
		}	// end toArray()


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
		{
		
		//T[] array1 = toArray();
		String array1 = Arrays.toString(toArray());
		
		
		
			System.out.println(array1);
		
		
		
		return null ;
		
		}	// end toString()


	// Initializes the class's data fields to indicate an empty list.
	private void initializeDataFields()
		{
		firstNode = null ;
		lastNode = null ;
		numberOfEntries = 0 ;
		
		}	// end initializeDataFields()


	// Returns a reference to the node at a given position.
	// Precondition: List is not empty; 1 <= givenPosition <= numberOfEntries.
	private Node getNodeAt( int givenPosition )
		{
		assert ( firstNode != null ) && ( 1 <= givenPosition ) &&
		       ( givenPosition <= numberOfEntries ) ;
		Node currentNode = firstNode ;

		if ( givenPosition == numberOfEntries )
			currentNode = lastNode ;
		else if ( givenPosition > 1 )
			{
			assert givenPosition < numberOfEntries ;
			// Traverse the chain to locate the desired node
			for ( int counter = 1 ; counter < givenPosition ; counter++ )
				currentNode = currentNode.getNextNode() ;
			} // end if

		assert currentNode != null ;
		return currentNode ;
		}	// end getNodeAt()


	/**
	 * 
	 */
	private class Node
		{

		private T	 data ;     // Data portion
		private Node next ;		// Link to next node


		/**
		 * 
		 * @param dataPortion
		 */
		private Node( T dataPortion )
			{
			data = dataPortion ;
			next = null ;
			
			}	// end constructor


		/**
		 * 
		 * @param dataPortion
		 * @param nextNode
		 */
		private Node( T dataPortion, Node nextNode )
			{
			data = dataPortion ;
			next = nextNode ;
			
			}	// end constructor


		/**
		 * 
		 * @return
		 */
		private T getData()
			{
			return data ;
			
			}	// end getData()


		/**
		 * 
		 * @param newData
		 */
		private void setData( T newData )
			{
			data = newData ;
			
			}	// end setData()


		/**
		 * 
		 * @return
		 */
		private Node getNextNode()
			{
			return next ;
			
			}	// end getNextNode()


		/**
		 * 
		 * @param nextNode
		 */
		private void setNextNode( Node nextNode )
			{
			next = nextNode ;
			
			}	// end setNextNode()
		
		}	// end inner class Node
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args )
    	{
		
		LinkedListPlus testList = new LinkedListPlus();
		
		testList.add("a");
		testList.add("b");
		testList.add("c");
		testList.add("d");
		testList.add("e");
		testList.add("f");
		testList.add("g");
		testList.add("h");
		testList.add("i");
		testList.add("j");
		testList.add("k");
		testList.add("l");
		testList.add("m");
		testList.toString();
		testList.shuffle();
		testList.toString();
		
    	
    	}	// end main()
	
	} // end class LinkedListPlus
