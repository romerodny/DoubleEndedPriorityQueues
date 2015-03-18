/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cop3530;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author drome028
 */

public class test
{
    public static void main(String args[])
    {
        ListDoubleEndedPriorityQueue l = new ListDoubleEndedPriorityQueue( );
        
        l.add( "hello" );
        l.add( "World" );
        l.add( "zebra" );
        l.add( "peter" );
        l.add( "Peter" );
        l.add( "Francisco" );
        l.add( "Soccer" );
        l.add( "soccer" );
        l.add( "yoyo" );
        l.add( "attorney" );
        l.add( "Abracadabra" );
        l.add( "abracadabra" );
        
        System.out.println(l);
        System.out.println(l.findMin());
        System.out.println(l.findMax());
        System.out.println("removed " + l.deleteMin());
        System.out.println("removed " + l.deleteMax());
        System.out.println(l);
        
        l.makeEmpty();
        
        System.out.println(l);
        
         final int N = 20;
        
        Random r = new Random( 1 );
        List<Integer> randomNums = new ArrayList<>( );
        List<Integer> randomNums1 = new ArrayList<>( );
        
        for( int i = 0; i < N; i++ )
            randomNums.add( r.nextInt( N * 2 ) );
        
        for( int i = 0; i < N; i++ )
            randomNums1.add( r.nextInt( N * 2 ) );
        
        TreeDoubleEndedPriorityQueue<Integer> s2 = new TreeDoubleEndedPriorityQueue<>( );
        for( int x : randomNums ){
            s2.add( x );
            s2.add(2);
        }
                for( int x : randomNums1 ){
            s2.add( x );
        }
        s2.add(3);
        for (int i = 1; i < 20; i++)
        {
            s2.add(i);
        }

        System.out.println(s2);
        System.out.println("min "+s2.findMin());
        System.out.println("max "+s2.findMax());
        System.out.println("delete min "+s2.deleteMin());
        System.out.println(s2);
        System.out.println("min "+s2.findMin());
        System.out.println("delete min "+s2.deleteMin());
        System.out.println(s2);
                System.out.println("min "+s2.findMin());
        System.out.println("delete min "+s2.deleteMin());
        System.out.println(s2);
                System.out.println("min "+s2.findMin());
        System.out.println("delete min "+s2.deleteMin());
        System.out.println(s2);
                System.out.println("min "+s2.findMin());
        System.out.println("delete min "+s2.deleteMin());
        System.out.println(s2);
        System.out.println("delete max " + s2.deleteMax());
        System.out.println(s2);
                System.out.println("delete max " + s2.deleteMax());
        System.out.println(s2);
                System.out.println("delete max " + s2.deleteMax());
        System.out.println(s2);
                System.out.println("delete max " + s2.deleteMax());
        System.out.println(s2);
        
    }

}
