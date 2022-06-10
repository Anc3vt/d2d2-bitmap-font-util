/**
 * Copyright (C) 2022 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ancevt.d2d2.bitmapfontutil;

import lombok.SneakyThrows;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class Canvas extends JPanel {

    private static final int DEFAULT_WIDTH = 1024;
    private static final int DEFAULT_HEIGHT = 1024;

    private String string;
    private Font font;
    private BiConsumer<List<CharInfo>, BufferedImage> writeFunction;
    private List<CharInfo> charInfos;
    private BufferedImage bufferedImage;
    private final ArgsBitmapFontUtil argsBitmapFontUtil;
    private int maxX;
    private int maxY;

    public Canvas(ArgsBitmapFontUtil argsBitmapFontUtil) {
        this.argsBitmapFontUtil = argsBitmapFontUtil;
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(argsBitmapFontUtil.getWidth(), argsBitmapFontUtil.getHeight()));
    }

    @SneakyThrows
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        Graphics2D graphics2D = (Graphics2D) g;

        if (string == null) return;

        if (argsBitmapFontUtil.isFractionalMetricsOn()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }

        if (argsBitmapFontUtil.isTextAntialiasOn()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }

        if (argsBitmapFontUtil.isTextAntialiasGasp()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        }

        if (argsBitmapFontUtil.isTextAntialiasLcdHrgb()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }

        if (argsBitmapFontUtil.isTextAntialiasLcdHbgr()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
        }

        if (argsBitmapFontUtil.isTextAntialiasLcdVrgb()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB);
        }

        if (argsBitmapFontUtil.isTextAntialiasLcdVbgr()) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR);
        }

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(font);

        g2.setColor(Color.WHITE);

        charInfos = new CopyOnWriteArrayList<>();

        int x = 0, y = font.getSize();

        for (int i = 0; i < string.length(); i++) {

            char c = string.charAt(i);

            FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
            int width = fontMetrics.charWidth(c);
            int height = fontMetrics.getHeight();
            int toY = fontMetrics.getDescent();

            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(String.valueOf(c), x, y);

            g2.setFont(font);
            g2.drawString(String.valueOf(c), x, y);

            graphics2D.setColor(Color.RED);
            graphics2D.setStroke(new BasicStroke(0.1f));
            graphics2D.drawRect(x, y - height + toY, width, height);

            CharInfo charInfo = new CharInfo();
            charInfo.character = c;
            charInfo.x = x;
            charInfo.y = y - height + toY;
            charInfo.width = width;
            charInfo.height = height;

            charInfos.add(charInfo);

            x += width;

            if (x > maxX) maxX = x;

            if (x >= getWidth() - font.getSize()) {
                y += height;
                if (y > maxY) maxY = y + 5;
                x = 0;
            }
        }

        if(argsBitmapFontUtil.getWidth() == 0 && argsBitmapFontUtil.getHeight() == 0) {
            if (getWidth() != maxX || getHeight() != maxY) {
                setSize(new Dimension(maxX, maxY));
                Thread.sleep(500);
            } else {
                if(!argsBitmapFontUtil.isStayOnScreen()) {
                    writeFunction.accept(charInfos, bufferedImage);
                    System.exit(0);
                }
            }
        }

        writeFunction.accept(charInfos, bufferedImage);
    }

    public List<CharInfo> getCharInfos() {
        return charInfos;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void draw(String string, Font font, BiConsumer<List<CharInfo>, BufferedImage> writeFunction) {
        this.string = string;
        this.font = font;
        this.writeFunction = writeFunction;
        if (argsBitmapFontUtil.getWidth() != 0 && argsBitmapFontUtil.getHeight() != 0) {
            setSize(argsBitmapFontUtil.getWidth(), argsBitmapFontUtil.getHeight());
        }
        paintComponent(getGraphics());
    }
}
