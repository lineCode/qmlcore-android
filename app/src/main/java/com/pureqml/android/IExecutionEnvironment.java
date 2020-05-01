package com.pureqml.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.view.ViewGroup;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.pureqml.android.runtime.BaseObject;
import com.pureqml.android.runtime.Element;

import java.util.Timer;
import java.util.concurrent.ExecutorService;

public interface IExecutionEnvironment {
    Context getContext();
    ExecutorService getExecutor();
    ExecutorService getThreadPool();
    ViewGroup getRootView();
    Timer getTimer();
    void setRenderer(IRenderer renderer);
    IRenderer getRenderer();

    V8 getRuntime();

    BaseObject getObjectById(int id);
    void putObject(int id, BaseObject element);
    void removeObject(int id);

    //invoke function + schedulePaint
    Object invokeCallback(V8Function callback, V8Object receiver, V8Array arguments);
    void invokeVoidCallback(V8Function callback, V8Object receiver, V8Array arguments);
    void update(Element el);
    void animate(Element el, float seconds);

    //image loader api
    AssetManager getAssets();
    ImageLoader getImageLoader();

    void register(IResource res);

    void focusView(View view, boolean set);
    void blockUiInput(boolean block);
}
