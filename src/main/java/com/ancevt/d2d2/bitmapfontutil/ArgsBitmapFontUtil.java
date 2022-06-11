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

import com.ancevt.util.args.ArgsParameter;
import lombok.Data;

@Data
public class ArgsBitmapFontUtil {

    @ArgsParameter(names = {"--width", "-w"})
    private int width = 0;

    @ArgsParameter(names = {"--height", "-h"})
    private int height = 0;

    @ArgsParameter(names = {"--input", "-i"}, required = true)
    private String input;

    @ArgsParameter(names = {"--output", "-o"}, required = true)
    private String output;

    @ArgsParameter(names = {"--size", "-s"}, required = true)
    private int size;

    @ArgsParameter(names = {"--bold", "-B"})
    private boolean bold;

    @ArgsParameter(names = {"--italic", "-I"})
    private boolean italic;

    @ArgsParameter(names = "--text-antialias-on")
    private boolean textAntialiasOn;

    @ArgsParameter(names = "--text-antialias-lcd-gasp")
    private boolean textAntialiasGasp;

    @ArgsParameter(names = "--text-antialias-lcd-hrgb")
    private boolean textAntialiasLcdHrgb;

    @ArgsParameter(names = "--text-antialias-lcd-hbgr")
    private boolean textAntialiasLcdHbgr;

    @ArgsParameter(names = "--text-antialias-lcd-vrgb")
    private boolean textAntialiasLcdVrgb;

    @ArgsParameter(names = "--text-antialias-lcd-vbgr")
    private boolean textAntialiasLcdVbgr;

    @ArgsParameter(names = "--fractional-metrics-on")
    private boolean fractionalMetricsOn;

    @ArgsParameter(names = {"--stay-on-screen", "-S"})
    private boolean stayOnScreen;

    @ArgsParameter(names = {"--spacing-x", "-sx"})
    private int spacingX;

    @ArgsParameter(names = {"--spacing-y", "-sy"})
    private int spacingY;
}
