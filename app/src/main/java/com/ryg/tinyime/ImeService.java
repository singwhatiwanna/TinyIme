package com.ryg.tinyime;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class ImeService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView mInputView;
    private Keyboard mQwertyKeyboard;
    private Keyboard mSymbolKeyboard;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onInitializeInterface() {
        mQwertyKeyboard = new Keyboard(this, R.xml.qwerty);
        mSymbolKeyboard = new Keyboard(this, R.xml.symbols);
    }

    @Override
    public View onCreateInputView() {
        mInputView = (KeyboardView) getLayoutInflater().inflate(
                R.layout.input, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setKeyboard(mQwertyKeyboard);
        return mInputView;
    }

    @Override
    public View onCreateCandidatesView() {
        return super.onCreateCandidatesView();
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            mInputView.setShifted(!mInputView.isShifted());
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            requestHideSelf(0);
            mInputView.closing();
        } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE
                && mInputView != null) {
            Keyboard current = mInputView.getKeyboard();
            if (current == mSymbolKeyboard) {
                current = mQwertyKeyboard;
            } else {
                current = mSymbolKeyboard;
            }
            mInputView.setKeyboard(current);
        } else {
            if (isInputViewShown()) {
                if (mInputView.isShifted()) {
                    primaryCode = Character.toUpperCase(primaryCode);
                }
            }
            getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {
//        InputConnection ic = getCurrentInputConnection();
//        if (ic == null) return;
//        ic.beginBatchEdit();
//        ic.commitText(text, 0);
//        ic.endBatchEdit();
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }
}
