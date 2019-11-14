/*
 * Copyright (c) 2019 Ahmad Issa
 * Copyright (c) 2019 Owain		<https://github.com/sdburns1998>
 * Copyright (c) 2019 Kyle 		<https://github.com/Kyleeld>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.plugins.valuehighlights;

import java.awt.Color;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("valuehighlights")
public interface ValueHighlightsConfig extends Config {
    @ConfigItem(keyName = "showBank", name = "Highlight Bank Items", description = "Show highlight on bank items.", position = 0)
    default boolean showBank() {
        return true;
    }

    @ConfigItem(keyName = "showInventory", name = "Highlight Inventory Items", description = "Show highlight on inventory items.", position = 1)
    default boolean showInventory() {
        return true;
    }

    @ConfigItem(keyName = "getHighlightLow", name = "Low Value Highlight Color", description = "Choose the color of the low highlight.", position = 2)
    default Color getHighlightLow() {
        return Color.BLUE;
    }

    @ConfigItem(keyName = "getValueLow", name = "Low Value Price", description = "Choose the minimum price of the low value.", position = 3)
    default int getValueLow() {
        return 50000;
    }

    @ConfigItem(keyName = "getHighlightMedium", name = "Medium Value Highlight Color", description = "Choose the color of the medium highlight.", position = 4)
    default Color getHighlightMedium() {
        return Color.GREEN;
    }

    @ConfigItem(keyName = "getValueMedium", name = "Medium Value Price", description = "Choose the minimum price of the medium value.", position = 5)
    default int getValueMedium() {
        return 250000;
    }

    @ConfigItem(keyName = "getHighlightHigh", name = "High Value Highlight Color", description = "Choose the color of the high highlight.", position = 6)
    default Color getHighlightHigh() {
        return Color.YELLOW;
    }

    @ConfigItem(keyName = "getValueHigh", name = "High Value Price", description = "Choose the minimum price of the high value.", position = 7)
    default int getValueHigh() {
        return 1250000;
    }

    @ConfigItem(keyName = "getHighlightInsane", name = "Insane Value Highlight Color", description = "Choose the color of the insane highlight.", position = 8)
    default Color getHighlightInsane() {
        return Color.MAGENTA;
    }

    @ConfigItem(keyName = "getValueInsane", name = "Insane Value Price", description = "Choose the minimum price of the insane value.", position = 9)
    default int getValueInsane() {
        return 6250000;
    }

    @ConfigItem(keyName = "doHighlightNull", name = "Highlight Valueless Items", description = "Show highlight on valueless items.", position = 10)
    default boolean doHighlightNull() {
        return false;
    }

    @ConfigItem(keyName = "getHighlightNull", name = "Valueless Highlight Color", description = "Choose the color of the valueless highlight.", hidden = true, unhide = "doHighlightNull", position = 11)
    default Color getHighlightNull() {
        return Color.GRAY;
    }
}