//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import gwt.material.design.client.base.HasIcon;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.graphics.Color;
import de.esoco.ewt.impl.gwt.WidgetFactory;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.RelativeScale;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.ContentProperties.ICON;
import static de.esoco.lib.property.StyleProperties.ICON_ALIGNMENT;
import static de.esoco.lib.property.StyleProperties.ICON_COLOR;
import static de.esoco.lib.property.StyleProperties.ICON_SIZE;


/********************************************************************
 * The base class for all {@link MaterialWidget} factories.
 *
 * @author eso
 */
public abstract class MaterialWidgetFactory<W extends IsWidget>
	implements WidgetFactory<W>
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final W createWidget(Component rComponent, StyleData rStyle)
	{
		Widget aWidget = createMaterialWidget(rComponent, rStyle);

		if (aWidget instanceof HasIcon)
		{
			applyIcon((HasIcon) aWidget, rStyle);
		}

		return (W) aWidget;
	}

	/***************************************
	 * Must be implemented by subclasses to create the {@link MaterialWidget}
	 * for the given component.
	 *
	 * @param  rComponent The component to create the widget for
	 * @param  rStyle     The style of the new widget
	 *
	 * @return The new material widget instance
	 */
	protected abstract Widget createMaterialWidget(
		Component rComponent,
		StyleData rStyle);

	/***************************************
	 * Applies any icon definition from the style data to the {@link HasIcon}
	 * argument.
	 *
	 * @param rHasIcon The material widget with icon attribute
	 * @param rStyle   The style to check for icon definitions
	 */
	protected void applyIcon(HasIcon rHasIcon, StyleData rStyle)
	{
		String sIcon = rStyle.getProperty(ICON, null);

		if (sIcon != null)
		{
			IconType eIconType = IconType.valueOf(sIcon);

			rHasIcon.setIconType(eIconType);
		}

		RelativeScale eIconSize =
			rStyle.getProperty(ICON_SIZE, RelativeScale.XLARGE);

		// XLARGE not supported by GwtMaterial, therefore used
		// as default instead of NULL
		if (eIconSize != RelativeScale.XLARGE)
		{
			rHasIcon.setIconSize(IconSize.valueOf(eIconSize.name()));
		}

		int nColor = rStyle.getIntProperty(ICON_COLOR, -1);

		if (nColor >= 0)
		{
			// set on Style because setIconColor expects color names
			rHasIcon.getIcon().getElement().getStyle()
					.setColor(Color.toHtml(nColor));
		}

		Alignment eAlignment = rStyle.getProperty(ICON_ALIGNMENT, null);

		if (eAlignment != null)
		{
			rHasIcon.setIconPosition(eAlignment != Alignment.END
									 ? IconPosition.LEFT : IconPosition.RIGHT);
		}
	}
}
