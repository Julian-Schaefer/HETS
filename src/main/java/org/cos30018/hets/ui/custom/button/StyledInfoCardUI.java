package org.cos30018.hets.ui.custom.button;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

public class StyledInfoCardUI extends BasicPanelUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JPanel panel = (JPanel) c;
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        JPanel p = (JPanel) c;
        paintBackground(g, p, p.getBaseline(152, 152), p.getBaseline(277, 277));
        super.paint(g, c);
    }


    private void paintBackground(Graphics g, JPanel p, int yOffset, int xOffset) {

        Dimension size = p.getSize();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setColor(p.getBackground().darker());
//        g.setColor(new Color(0x2dce98));
//        g.fillRoundRect(0, 0, size.width, size.height, 20, 20);
//        g.setColor(p.getBackground());
//        g.fillRoundRect(0, 0, size.width, size.height, 20, 20);

        g.setColor(new Color(0x2dce98));
        g.fillRoundRect(0, 0, size.width, size.height, 20, 20);
        g.setColor(Color.WHITE);
        g.fillRoundRect(2, 2, size.width - 4, size.height - 4, 20, 20);

    }
}
