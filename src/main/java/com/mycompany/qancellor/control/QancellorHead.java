/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qancellor.control;

import com.mycompany.qancellor.view.Frame;
import java.util.Timer;

/**
 *
 * @author mugarov
 */
public class QancellorHead {
    private Frame frame;
    private StatusWatcher watcher;
    private Timer timer;
    
    public QancellorHead(){
        this.frame = new Frame();
        this.timer = new Timer();
        this.watcher = new StatusWatcher(this.frame);
        this.timer.schedule(this.watcher, 0, 2000);
    }
    
}
