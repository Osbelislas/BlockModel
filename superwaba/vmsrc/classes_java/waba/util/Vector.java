package waba.util;

/*
Copyright (c) 1998, 1999 Wabasoft  All rights reserved.

This software is furnished under a license and may be used only in accordance
with the terms of that license. This software and documentation, and its
copyrights are owned by Wabasoft and are protected by copyright law.

THIS SOFTWARE AND REFERENCE MATERIALS ARE PROVIDED "AS IS" WITHOUT WARRANTY
AS TO THEIR PERFORMANCE, MERCHANTABILITY, FITNESS FOR ANY PARTICULAR PURPOSE,
OR AGAINST INFRINGEMENT. WABASOFT ASSUMES NO RESPONSIBILITY FOR THE USE OR
INABILITY TO USE THIS SOFTWARE. WABASOFT SHALL NOT BE LIABLE FOR INDIRECT,
SPECIAL OR CONSEQUENTIAL DAMAGES RESULTING FROM THE USE OF THIS PRODUCT.

WABASOFT SHALL HAVE NO LIABILITY OR RESPONSIBILITY FOR SOFTWARE ALTERED,
MODIFIED, OR CONVERTED BY YOU OR A THIRD PARTY, DAMAGES RESULTING FROM
ACCIDENT, ABUSE OR MISAPPLICATION, OR FOR PROBLEMS DUE TO THE MALFUNCTION OF
YOUR EQUIPMENT OR SOFTWARE NOT SUPPLIED BY WABASOFT.
*/

import waba.sys.*;

/**
 * A vector is an array of object references. The vector grows and shrinks
 * dynamically as objects are added and removed.
 * <p>
 * Here is an example showing a vector being used:
 *
 * <pre>
 * ...
 * Vector vec = new Vector();
 * vec.add(obj1);
 * vec.add(obj2);
 * ...
 * vec.insert(3, obj3);
 * vec.del(2);
 * if (vec.getCount() > 5)
 * ...
 * </pre>
 */
public class Vector
{
Object items[];
int count;

/** Constructs an empty vector. */
public Vector()
   {
   this(8);
   }   
/**
 * Constructs an empty vector with a given initial size. The size is
 * the initial size of the vector's internal object array. The vector
 * will grow as needed when objects are added.
 */
public Vector(int size)
   {
   items = new Object[size];
   }   
/** Adds an object to the end of the vector. */
public void add(Object obj)
   {
   if (count < items.length)
	  items[count++] = obj;
   else
	  insert(count, obj);
   }   
/** Deletes the object reference at the given index. */
public void del(int index)
   {
   if (index != count - 1)
	  Vm.copyArray(items, index + 1, items, index, count - index - 1);
   items[count - 1] = null;
   count--;
   }   
/**
 * Finds the index of the given object. The list is searched using a O(n) linear
 * search through all the objects in the vector.
 */
public int find(Object obj)
{
   // guich@102g - if string array, compare strings correctly
   if (count > 0 && obj != null && items[0] instanceof String)
   {
	  String str = (String)obj;
	  for (int i = 0; i < count; i++)
		 if (str.equals((String)items[i]))
			return i;
	  return -1;
   }
   for (int i = 0; i < count; i++)
	  if (items[i] == obj)
		 return i;
   return -1;
   }   
/** Returns the object at the given index. */
public Object get(int index)
   {
   if (index >= count)
	  index = items.length; // force an out of range error
   return items[index];
   }   
/** Returns the number of objects in the vector. */
public int getCount()
   {
   return count;
   }   
/** Inserts an object at the given index. */
public void insert(int index, Object obj)
   {
   if (count == items.length)
	  {
	  // double size of items array
	  Object newItems[] = new Object[items.length * 2];
	  Vm.copyArray(items, 0, newItems, 0, count);
	  items = newItems;
	  }
   if (index != count)
	  Vm.copyArray(items, index, items, index + 1, count - index);
   items[index] = obj;
   count++;
   }   
/** returns the last object, without removing it. returns null if no more elements. */ // guich@102
public Object peek()
{
   return (count > 0)?items[count-1]:null;
}
/** returns the last object, removing it. returns null if no more elements. */ // guich@102
public Object pop()
{
   Object o=null;
   if (count > 0)
	  o = items[--count];
   items[count] = null; // let gc do their work
   return o;
}
// methods to let the vector act like a stack

/** pushes a object. simply calls add. */ // guich@102
public void push(Object obj)
{
   add(obj);
}
/** Sets the object at the given index. */
public void set(int index, Object obj)
   {
   if (index >= count)
	  index = items.length; // force an out of range error
   items[index] = obj;
   }   
/** Converts the vector to an array of objects. */
public Object []toObjectArray()
   {
   Object objs[] = new Object[count];
   if (count > 0)  // guich
	  Vm.copyArray(items, 0, objs, 0, count);
   return objs;
   }   
/** sets the number of elements of this vector to 0. */
// guich@120
public void removeAll()
{
   count = 0;
}
}