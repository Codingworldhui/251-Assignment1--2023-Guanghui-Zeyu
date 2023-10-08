package org.example;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TextEditorTest {
    private SimpleNotepad textEditor;

    @Before
    public void setUp() {
        // 在每个测试方法之前创建一个新的 SimpleNotepad 实例
        textEditor = new SimpleNotepad();
    }

    @Test
    public void testAboutMethod() {
        // 使用 Mockito 创建一个 spy 对象以便跟踪方法调用
        SimpleNotepad spyEditor = Mockito.spy(textEditor);

        // 使用 Mockito 的 doNothing() 来模拟 JOptionPane.showMessageDialog 方法，以避免实际弹出消息框
        Mockito.doNothing().when(spyEditor).showMessageDialog(Mockito.any(), Mockito.anyString());

        // 调用 About 方法
        spyEditor.About();

        // 使用 Mockito 验证是否调用了 showMessageDialog 方法，检查消息框是否弹出
        Mockito.verify(spyEditor).showMessageDialog(Mockito.any(), Mockito.anyString());
    }
}
