package org.cos30018.hets.ui.custom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

public class StyledJPanelUI extends BasicPanelUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JPanel panel = (JPanel) c;
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        JPanel p = (JPanel) c;
        paintBackground(g, p, p.getBaseline(100, 100));
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JPanel p, int yOffset) {

        Dimension size = p.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(p.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 20, 20);
        g.setColor(p.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 1, 20, 20);

    }
}
