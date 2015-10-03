package ua.pp.fairwind.javafx.effects.special_paints;

import javafx.animation.Interpolator;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;

import java.util.*;


/**
 * Created by
 * User: hansolo
 * Date: 20.08.12
 * Time: 10:37
 */
public class EllipticalGradient {
    private List<Stop> sortedStops;

    public EllipticalGradient(final Stop... STOPS) {
        this(Arrays.asList(STOPS));
    }

    public EllipticalGradient(final List<Stop> STOPS) {
        List<Stop> stops;
        if (STOPS == null || STOPS.isEmpty()) {
            stops = new ArrayList<>();
            stops.add(new Stop(0.0, Color.TRANSPARENT));
            stops.add(new Stop(1.0, Color.TRANSPARENT));
        } else {
            stops = STOPS;
        }

        HashMap<Double, Color> stopMap = new LinkedHashMap<>(stops.size());
        for (Stop stop : stops) {
            stopMap.put(stop.getOffset(), stop.getColor());
        }

        sortedStops = new LinkedList<>();
        final SortedSet<Double> sortedFractions = new TreeSet<>(stopMap.keySet());
        if (sortedFractions.last() < 1) {
            stopMap.put(1.0, stopMap.get(sortedFractions.last()));
            sortedFractions.add(1.0);
        }
        if (sortedFractions.first() > 0) {
            stopMap.put(0.0, stopMap.get(sortedFractions.first()));
            sortedFractions.add(0.0);
        }
        for (final Double FRACTION : sortedFractions) {
            sortedStops.add(new Stop(FRACTION, stopMap.get(FRACTION)));
        }
    }

    public List<Stop> getStops() {
        return sortedStops;
    }

    public Image getImage(final double WIDTH, final double HEIGHT) {
        return getImage(WIDTH, HEIGHT, new Point2D(WIDTH / 2.0, HEIGHT / 2.0));
    }

    public Image getImage(final double WIDTH, final double HEIGHT, final Point2D CENTER) {
        int width = (int) WIDTH <= 0 ? 100 : (int) WIDTH;
        int height = (int) HEIGHT <= 0 ? 50 : (int) HEIGHT;
        double a = WIDTH / 2.0;
        double aSquare = a * a;
        double b = HEIGHT / 2.0;
        double bSquare = b * b;
        Color color = Color.TRANSPARENT;
        final WritableImage RASTER = new WritableImage(width, height);
        final PixelWriter PIXEL_WRITER = RASTER.getPixelWriter();
        double isInside;
        double fraction;
        double currentOffset;
        double nextOffset;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                isInside = ((x - CENTER.getX()) * (x - CENTER.getX())) / aSquare + ((y - CENTER.getY()) * (y - CENTER.getY())) / bSquare;
                isInside = isInside > 1 ? 1 : isInside;
                for (int i = 0; i < (sortedStops.size() - 1); i++) {
                    currentOffset = sortedStops.get(i).getOffset();
                    nextOffset = sortedStops.get(i + 1).getOffset();
                    if (Double.compare(isInside, currentOffset) > 0 && Double.compare(isInside, nextOffset) <= 0) {
                        fraction = (isInside - currentOffset) / (nextOffset - currentOffset);
                        color = (Color) Interpolator.LINEAR.interpolate(sortedStops.get(i).getColor(), sortedStops.get(i + 1).getColor(), fraction);
                    }
                }
                PIXEL_WRITER.setColor(x, y, color);
            }
        }
        return RASTER;
    }

    public ImagePattern getFill(final Shape SHAPE) {
        return getFill(SHAPE, new Point2D(SHAPE.getLayoutBounds().getWidth() / 2.0, SHAPE.getLayoutBounds().getHeight() / 2.0));
    }

    public ImagePattern getFill(final Shape SHAPE, final Point2D CENTER) {
        double x = SHAPE.getLayoutBounds().getMinX();
        double y = SHAPE.getLayoutBounds().getMinY();
        double width = SHAPE.getLayoutBounds().getWidth();
        double height = SHAPE.getLayoutBounds().getHeight();
        return new ImagePattern(getImage(width, height, CENTER), x, y, width, height, false);
    }
}