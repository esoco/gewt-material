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

import gwt.material.design.client.base.mixin.HTMLMixin;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;

import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Label.LabelWidgetFactory;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialCardTitle;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialIcon;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialImage;
import de.esoco.ewt.impl.gwt.material.widget.GewtMaterialTitle;
import de.esoco.ewt.style.StyleData;
import de.esoco.ewt.style.StyleFlag;

import de.esoco.lib.property.LabelStyle;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.StyleProperties.HAS_IMAGES;
import static de.esoco.lib.property.StyleProperties.LABEL_STYLE;

/**
 * The factory for {@link MaterialButton} widgets.
 *
 * @author eso
 */
public class MaterialLabelFactory<W extends Widget & HasText>
	extends MaterialWidgetFactory<W> {

	private LabelWidgetFactory<W> defaultFactory = new LabelWidgetFactory<>();

	@Override
	@SuppressWarnings("unchecked")
	public W createMaterialWidget(Component component, StyleData style) {
		Widget widget = null;

		if (style.hasFlag(StyleFlag.HYPERLINK)) {
			widget = new MaterialLink();
		} else {
			LabelStyle labelStyle =
				style.getProperty(LABEL_STYLE, LabelStyle.DEFAULT);

			switch (labelStyle) {
				case DEFAULT:
					widget = style.hasFlag(HAS_IMAGES) ?
					         new GewtMaterialIcon() :
					         new GewtMaterialLabel();
					break;

				case BRAND:
					widget = new GewtMaterialNavBrand();
					break;

				case ICON:
					widget = new GewtMaterialIcon();
					break;

				case IMAGE:
					widget = new GewtMaterialImage();
					break;

				case TITLE:
					widget = isCardElement(component) ?
					         new GewtMaterialCardTitle() :
					         new GewtMaterialTitle();
					break;

				default:
					widget =
						defaultFactory.createLabelWidget(component, labelStyle,
							style);
			}
		}

		return (W) widget;
	}

	/**
	 * Checks whether the given component is a child element of a
	 * {@link MaterialCard} widget.
	 *
	 * @param component The component to check
	 * @return TRUE if it is a child element of a card
	 */
	private boolean isCardElement(Component component) {
		Widget parentWidget = component.getParent().getWidget();

		return parentWidget instanceof MaterialCard ||
			parentWidget instanceof MaterialCardContent ||
			parentWidget instanceof MaterialCardReveal;
	}

	/**
	 * Material label that also implements {@link HasHTML}.
	 *
	 * @author eso
	 */
	public static class GewtMaterialLabel extends MaterialLabel
		implements HasHTML {

		private HTMLMixin<GewtMaterialLabel> htmlMixin = new HTMLMixin<>(this);

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getHTML() {
			return htmlMixin.getHTML();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setHTML(String html) {
			htmlMixin.setHTML(html);
		}
	}

	/**
	 * Subclassed to implement {@link HasText}.
	 *
	 * @author eso
	 */
	public static class GewtMaterialNavBrand extends MaterialNavBrand
		implements HasText {
	}
}
