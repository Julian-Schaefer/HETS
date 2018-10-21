package org.cos30018.hets.ui.custom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PopupMenuUI;
import java.awt.*;

public class StyledPopupMenuUI extends PopupMenuUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JPopupMenu menu = (JPopupMenu) c;
        menu.setOpaque(false);
        menu.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        JPopupMenu p = (JPopupMenu) c;
        c.setBackground(Color.white);
        paintBackground(g, p, p.getBaseline(100, 100));
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JPopupMenu p, int yOffset) {

        Dimension size = p.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(p.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 20, 20);
        g.setColor(p.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset, 20, 20);

    }
}
