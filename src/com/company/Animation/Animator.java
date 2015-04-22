/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Animation;

import com.company.Math.Vector2f;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Animator<Type> {

    private HashMap<String, Timeline<Type>> animations;
    private Timeline<Type> current;
    private Type target;
    private Type idle;
    private String defaultValueName;
    private Action setToTargetAction;
    private String currentName;

    public Animator(Type idle) {
        this(idle, null, null);
    }

    public Animator(Type idle, Type target, String defaultValueName) {
        this.target = target;
        this.defaultValueName = defaultValueName;
        this.idle = idle;
        animations = new HashMap<>();
    }

    public void Update(double delta) {
        if (current != null) {
            current.Update(delta);
        }
        if (target != null) {
            if (setToTargetAction != null) {
                setToTargetAction.Perform();
            };
        }
    }

    public void AddAnimation(String name, Timeline timeline) {
        timeline.Stop().Reset();
        animations.put(name, timeline);
    }

    public void Play() {
        current.Reset();
        current.Start();
    }

    public void Play(String name) {
        if(currentName != null && currentName.equals(name)){
            return;
        }
        currentName = name;
        StopAll();
        current = animations.get(name);
        Play();
    }

    public void Pause() {
        current.Stop();
    }

    public void Stop() {
        if(current == null){
            return;
        }
        Pause();
        current.Reset();
        current = null;
        currentName = null;
    }

    public void StopAll() {
        animations.entrySet().forEach(x -> {
            x.getValue().Stop().Reset();
        });
        current = null;
    }

    public Type GetCurrentContent(String valueName) {
        if (current == null) {
            return idle;

        }
        return (Type) current.GetLastKeyframe().GetData(valueName);
    }

    public HashMap<String, Timeline<Type>> GetAnimations() {
        return animations;
    }

    public void SetToTargetAction(Action action) {
        this.setToTargetAction = action;
    }

}
