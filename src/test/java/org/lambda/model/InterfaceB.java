/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lambda.model;

/**
 *
 * @author ken.lai
 */
public interface InterfaceB extends InterfaceA {
    default void scream()
    {
        System.out.println("InterfaceB.scream()");
    }
}
