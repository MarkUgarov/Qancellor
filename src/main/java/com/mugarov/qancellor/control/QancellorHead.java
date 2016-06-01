/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.control;

import com.mugarov.qancellor.view.Frame;
import java.awt.event.WindowListener;
import java.util.Timer;

/**
 *
 * @author mugarov
 */
public class QancellorHead {
    private Frame frame;
    private StatusWatcher watcher;
    private Timer timer;
    
    private boolean started;
    
    private WindowListener windowListener;
    private boolean initVisibility;
    
    public QancellorHead(){
       this(null, true);
       this.start();
    }
    
    public QancellorHead(WindowListener listener, boolean visible){
        this.windowListener = listener;
        this.initVisibility = visible;
    }
    
    public void start(){
        this.started = true;
        this.frame = new Frame(this.windowListener, this.initVisibility);
        this.timer = new Timer();
        this.watcher = new StatusWatcher(this.frame);
        this.timer.schedule(this.watcher, 0, 2000);
    }
    
    
    
    public boolean isFrameVisible(){
        if(this.frame == null){
            return false;
        }
        return this.frame.isVisible();
    }
    
    public void setFrameVisible(boolean visible){
        if(!this.started || this.frame == null){
            this.initVisibility = visible;
            this.start();
        }
        else{
            this.frame.setVisible(visible);
            this.frame.setState(Frame.NORMAL);
            this.frame.requestFocus();
        }
    }
    
    
}
