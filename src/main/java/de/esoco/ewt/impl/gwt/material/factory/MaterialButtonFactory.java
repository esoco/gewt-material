//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2018 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
package de.esoco.ewt.impl.gwt.material.factory;

import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.ui.MaterialButton;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Component;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialAnchorButton;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialButton;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialIcon;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialLink;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import de.esoco.lib.property.ButtonStyle;

import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.FLOAT;
import static de.esoco.lib.property.StyleProperties.BUTTON_STYLE;

/**
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialButtonFactory<W extends Widget & Focusable & HasText>
	extends MaterialWidgetFactory<W> {

	@Override
	@SuppressWarnings("unchecked")
	public W createMaterialWidget(Component component, StyleData style) {
		ButtonStyle buttonStyle =
			style.getProperty(BUTTON_STYLE, ButtonStyle.DEFAULT);

		ButtonType buttonType = mapButtonType(buttonStyle);
		AbstractButton buttonWidget;

		if (buttonType == ButtonType.LINK ||
			style.hasFlag(StyleFlag.HYPERLINK)) {
			buttonWidget = new GewtMaterialLink();
		} else if (buttonStyle == ButtonStyle.ICON) {
			buttonWidget = new GewtMaterialIcon();
		} else if (buttonStyle == ButtonStyle.FLOAT &&
			style.hasProperty(FLOAT)) {
			buttonWidget = new GewtMaterialAnchorButton();
		} else {
			buttonWidget = new GewtMaterialButton();
		}

		if (buttonType != null) {
			buttonWidget.setType(buttonType);
		}

		GewtMaterial.checkApplyButtonScale(buttonWidget, style);

		buttonWidget.setWaves(GewtMaterial.getDefaultAnimation());

		return (W) buttonWidget;
	}

	/**
	 * Maps the {@link ButtonStyle} from a style data object to a GwtMaterial
	 * {@link ButtonType} constant.
	 *
	 * @param buttonStyle style The style data to read the button style from
	 * @return The button type or NULL for a default or if no mapping exists
	 */
	private ButtonType mapButtonType(ButtonStyle buttonStyle) {
		ButtonType buttonType;

		switch (buttonStyle) {
			case FLAT:
				buttonType = ButtonType.FLAT;
				break;

			case FLOAT:
				buttonType = ButtonType.FLOATING;
				break;

			case LINK:
				buttonType = ButtonType.LINK;
				break;

			case DEFAULT:
			case OUTLINE:
			default:
				buttonType = null;
		}

		return buttonType;
	}
}
