package eu.komanda30.kupra;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void scaleStream(InputStream sourceFile, OutputStream targetFile, String targetFormat, int maxWidth, int maxHeight)
            throws IOException {
        final BufferedImage target = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        final BufferedImage source = ImageIO.read(sourceFile);
        target.createGraphics().drawImage(scale(source, maxWidth, maxHeight), 0, 0, null);
        ImageIO.write(target, targetFormat, targetFile);
    }

    public static BufferedImage scale(BufferedImage source, int maxWidth, int maxHeight) {
        double ratioWidth = maxWidth/source.getWidth();
        double ratioHeight = maxHeight/source.getHeight();
        return scale(source, Math.min(ratioHeight, ratioWidth));
    }

    public static BufferedImage scale(BufferedImage source,double ratio) {
        int w = (int) (source.getWidth() * ratio);
        int h = (int) (source.getHeight() * ratio);
        BufferedImage bi = getCompatibleImage(w, h);
        Graphics2D g2d = bi.createGraphics();
        double xScale = (double) w / source.getWidth();
        double yScale = (double) h / source.getHeight();
        AffineTransform at = AffineTransform.getScaleInstance(xScale,yScale);
        g2d.drawRenderedImage(source, at);
        g2d.dispose();
        return bi;
    }

    private static BufferedImage getCompatibleImage(int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        return gc.createCompatibleImage(w, h);
    }
}
