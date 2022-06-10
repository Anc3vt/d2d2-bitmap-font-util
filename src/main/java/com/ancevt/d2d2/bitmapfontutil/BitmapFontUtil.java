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

import com.ancevt.util.args.Args;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class BitmapFontUtil extends JFrame {

    private static ArgsBitmapFontUtil argsBitmapFontUtil;
    private final Canvas canvas;

    public BitmapFontUtil(ArgsBitmapFontUtil argsBitmapFontUtil) {
        canvas = new Canvas(argsBitmapFontUtil);
        getContentPane().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        argsBitmapFontUtil = Args.of(args).convert(ArgsBitmapFontUtil.class);

        BitmapFontUtil frame = new BitmapFontUtil(argsBitmapFontUtil);
        frame.setLocationByPlatform(true);
        frame.setPreferredSize(new Dimension(512, 512));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.pack();

        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Font font = Font.createFont(Font.TRUETYPE_FONT, Path.of(argsBitmapFontUtil.getInput()).toFile());
        String fontName = font.getName();
        ge.registerFont(font);

        String string =
                " !\"#№$%&'()*+,-./\\0123456789:;<=>@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz[]_{}АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя?^~`ҐґЇїЎў";

        boolean bold = argsBitmapFontUtil.isBold();
        boolean italic = argsBitmapFontUtil.isItalic();
        int fontSize = argsBitmapFontUtil.getSize();
        int fontStyle = Font.PLAIN | (bold ? Font.BOLD : Font.PLAIN) | (italic ? Font.ITALIC : Font.PLAIN);

        font = new Font(fontName, fontStyle, fontSize);

        frame.getCanvas().draw(string, font, BitmapFontUtil::write);

        if (argsBitmapFontUtil.getWidth() != 0 && argsBitmapFontUtil.getHeight() != 0 && !argsBitmapFontUtil.isStayOnScreen()) {
            System.exit(0);
        }
    }

    @SneakyThrows
    private static void write(List<CharInfo> charInfos, BufferedImage bufferedImage) {

        System.out.println(charInfos.size() + " " + bufferedImage);

        StringBuilder stringBuilder = new StringBuilder();
        charInfos.forEach(charInfo -> {
            stringBuilder.append(charInfo.character);
            stringBuilder.append(' ');
            stringBuilder.append(charInfo.x);
            stringBuilder.append(' ');
            stringBuilder.append(charInfo.y);
            stringBuilder.append(' ');
            stringBuilder.append(charInfo.width);
            stringBuilder.append(' ');
            stringBuilder.append(charInfo.height);
            stringBuilder.append('\n');
        });
        Files.writeString(Path.of(argsBitmapFontUtil.getOutput() + ".bmf"),
                stringBuilder.toString(),
                StandardCharsets.UTF_8,
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        ImageIO.write(bufferedImage, "png", Path.of(argsBitmapFontUtil.getOutput() + ".png").toFile());
    }
}
