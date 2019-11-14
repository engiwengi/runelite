/*
 * Copyright (c) 2019 Ahmad Issa
 * Copyright (c) 2019 Owain		<https://github.com/sdburns1998>
 * Copyright (c) 2019 Kyle 		<https://github.com/Kyleeld>
 * Copyright (c) 2019, Lucas <https://github.com/Lucwousin>
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
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import javax.inject.Inject;
import javax.inject.Singleton;

import net.runelite.api.ItemDefinition;
import net.runelite.api.widgets.Widget;

import static net.runelite.api.widgets.WidgetInfo.TO_GROUP;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

@Singleton
public class ValueHighlightsOverlay extends WidgetItemOverlay {
    private final ItemManager itemManager;
    private final ValueHighlightsPlugin plugin;

    @Inject
    public ValueHighlightsOverlay(final ItemManager itemManager, final ValueHighlightsPlugin plugin) {
        this.itemManager = itemManager;
        this.plugin = plugin;

        showOnBank();
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget) {
        Widget widget = itemWidget.getWidget();
        int interfaceGroup = TO_GROUP(widget.getId());

        if (!plugin.getInterfaceGroups().contains(interfaceGroup)) {
            return;
        }

        final int id = getNotedId(itemId);
        final int gePrice = getGEPrice(id);
        final Color color = getColor(gePrice * widget.getItemQuantity());

        {
            if (color != null) {
                Rectangle bounds = itemWidget.getCanvasBounds();
                bounds.grow(2, 2);
                graphics.setStroke(new BasicStroke(1));
                graphics.setColor(color);
                graphics.draw(bounds);
            }

        }
    }

    private int getGEPrice(int id) {
        return itemManager.getItemPrice(id);
    }

    // Checks if item is noted, if not returns id
    private int getNotedId(int id) {
        int noteID = id;
        ItemDefinition itemComposition = itemManager.getItemDefinition(noteID);
        if (itemComposition.getNote() != -1) {
            noteID = itemComposition.getLinkedNoteId();
        }
        return noteID;
    }

    private Color getColor(int price) {
        Color color = null;
        if (price >= plugin.getValueInsane()) {
            color = plugin.getHighlightInsane();
        } else if (price >= plugin.getValueHigh()) {
            color = plugin.getHighlightHigh();
        } else if (price >= plugin.getValueMedium()) {
            color = plugin.getHighlightMedium();
        } else if (price >= plugin.getValueLow()) {
            color = plugin.getHighlightLow();
        } else if (plugin.isDoHighlightNull()) {
            color = plugin.getHighlightNull();
        }
        return color;
    }
}
