/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customComponents;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Carol
 */

 

public class MyButton extends JButton{
    BufferedImage image;
    
    public MyButton(String imageFile){
      super();
        try {
           image = ImageIO.read(new File(imageFile));
          this.setIcon(new ImageIcon(image));
            
        } catch (IOException ex) {
            Logger.getLogger(MyButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @Override
            public boolean contains(int x, int y) {
                Rectangle viewRect = getBounds();
                Insets insets = getInsets();
                viewRect.x = insets.left;
                viewRect.y = insets.top;
                viewRect.width -= insets.left + insets.right;
                viewRect.height -= insets.top + insets.bottom;
                Rectangle iconR = new Rectangle();
                SwingUtilities.layoutCompoundLabel(this, this.getFontMetrics(this.getFont()), this.getText(), this.getIcon(),
                        this.getVerticalAlignment(), this.getHorizontalAlignment(), this.getVerticalTextPosition(),
                        this.getHorizontalTextPosition(), viewRect, iconR, new Rectangle(), this.getIconTextGap());
                if (!iconR.contains(x, y)) {
                    return false;
                }
                x -= iconR.x;
                y -= iconR.y;
                Color c = new Color(image.getRGB(x, y), true);
                return c.getAlpha() != 0 && (c.getRed() < 255 || c.getGreen() < 255 || c.getBlue() < 255);
            }
    


    
}
