/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.spinach.domain;

/**
 * Created by Administrator on 14-6-13.
 */
public class Greeting extends BaseEntity{
	private static final long serialVersionUID = 8006186339276482673L;
	private String content;
    public Greeting(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
}