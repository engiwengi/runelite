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

import com.google.inject.Provides;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.AccessLevel;
import lombok.Getter;

import static net.runelite.api.widgets.WidgetID.BANK_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.BANK_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.DEPOSIT_BOX_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.EQUIPMENT_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.GRAND_EXCHANGE_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.GUIDE_PRICES_INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.INVENTORY_GROUP_ID;
import static net.runelite.api.widgets.WidgetID.SHOP_INVENTORY_GROUP_ID;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Value Highlights",
        description = "Highlights items by value in your bank and inventory.",
        tags = {
                "bank",
                "inventory",
                "overlay",
                "grand",
                "exchange",
                "tooltips"
        },
        type = PluginType.EXTERNAL,
        enabledByDefault = false
)
@Singleton
public class ValueHighlightsPlugin extends Plugin {
    private static final String CONFIG_GROUP = "valuehighlights";

    @Getter(AccessLevel.PACKAGE)
    private final Set<Integer> interfaceGroups = new HashSet<>();

    @Inject
    private ValueHighlightsConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ValueHighlightsOverlay overlay;

    @Inject
    private EventBus eventBus;

    @Provides
    ValueHighlightsConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ValueHighlightsConfig.class);
    }

    private boolean showBank;
    private boolean showInventory;
    @Getter(AccessLevel.PACKAGE)
    private boolean doHighlightNull;
    @Getter(AccessLevel.PACKAGE)
    private Color highlightNull;
    @Getter(AccessLevel.PACKAGE)
    private Color highlightLow;
    @Getter(AccessLevel.PACKAGE)
    private Color highlightMedium;
    @Getter(AccessLevel.PACKAGE)
    private Color highlightHigh;
    @Getter(AccessLevel.PACKAGE)
    private Color highlightInsane;
    @Getter(AccessLevel.PACKAGE)
    private int valueLow;
    @Getter(AccessLevel.PACKAGE)
    private int valueMedium;
    @Getter(AccessLevel.PACKAGE)
    private int valueHigh;
    @Getter(AccessLevel.PACKAGE)
    private int valueInsane;

    @Override
    protected void startUp() throws Exception {
        updateConfig();
        buildGroupList();
        eventBus.subscribe(ConfigChanged.class, this, this::onConfigChanged);
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
    }

    private void onConfigChanged(ConfigChanged event) {
        if (event.getGroup().equals(CONFIG_GROUP)) {
            updateConfig();
            buildGroupList();
        }
    }

    private void buildGroupList() {
        interfaceGroups.clear();

        if (this.showBank) {
            interfaceGroups.add(BANK_GROUP_ID);
        }

        if (this.showInventory) {
            Arrays.stream(new int[]{DEPOSIT_BOX_GROUP_ID, BANK_INVENTORY_GROUP_ID, SHOP_INVENTORY_GROUP_ID,
                    GRAND_EXCHANGE_INVENTORY_GROUP_ID, GUIDE_PRICES_INVENTORY_GROUP_ID, EQUIPMENT_INVENTORY_GROUP_ID,
                    INVENTORY_GROUP_ID}).forEach(interfaceGroups::add);
        }
    }

    private void updateConfig() {
        this.showBank = config.showBank();
        this.showInventory = config.showInventory();
        this.doHighlightNull = config.doHighlightNull();
        this.highlightNull = config.getHighlightNull();
        this.highlightLow = config.getHighlightLow();
        this.highlightMedium = config.getHighlightMedium();
        this.highlightHigh = config.getHighlightHigh();
        this.highlightInsane = config.getHighlightInsane();
        this.valueLow = config.getValueLow();
        this.valueMedium = config.getValueMedium();
        this.valueHigh = config.getValueHigh();
        this.valueInsane = config.getValueInsane();
    }
}