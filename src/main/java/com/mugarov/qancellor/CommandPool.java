/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor;

/**
 *
 * @author mugarov
 */
public abstract class CommandPool {
    
    public static final String[] GET_QSTAT = new String[]{"qstat", "-xml"};
    public static final String[] DELETE_PROCESS = new String[]{"qdel"};
}
