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

	private LabelWidgetFactory<W> aDefaultFactory = new LabelWidgetFactory<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public W createMaterialWidget(Component rComponent, StyleData rStyle) {
		Widget aWidget = null;

		if (rStyle.hasFlag(StyleFlag.HYPERLINK)) {
			aWidget = new MaterialLink();
		} else {
			LabelStyle eLabelStyle =
				rStyle.getProperty(LABEL_STYLE, LabelStyle.DEFAULT);

			switch (eLabelStyle) {
				case DEFAULT:
					aWidget = rStyle.hasFlag(HAS_IMAGES) ?
					          new GewtMaterialIcon() :
					          new GewtMaterialLabel();
					break;

				case BRAND:
					aWidget = new GewtMaterialNavBrand();
					break;

				case ICON:
					aWidget = new GewtMaterialIcon();
					break;

				case IMAGE:
					aWidget = new GewtMaterialImage();
					break;

				case TITLE:
					aWidget = isCardElement(rComponent) ?
					          new GewtMaterialCardTitle() :
					          new GewtMaterialTitle();
					break;

				default:
					aWidget = aDefaultFactory.createLabelWidget(rComponent,
						eLabelStyle, rStyle);
			}
		}

		return (W) aWidget;
	}

	/**
	 * Checks whether the given component is a child element of a
	 * {@link MaterialCard} widget.
	 *
	 * @param rComponent The component to check
	 * @return TRUE if it is a child element of a card
	 */
	private boolean isCardElement(Component rComponent) {
		Widget rParentWidget = rComponent.getParent().getWidget();

		return rParentWidget instanceof MaterialCard ||
			rParentWidget instanceof MaterialCardContent ||
			rParentWidget instanceof MaterialCardReveal;
	}

	/**
	 * Material label that also implements {@link HasHTML}.
	 *
	 * @author eso
	 */
	public static class GewtMaterialLabel extends MaterialLabel
		implements HasHTML {

		private HTMLMixin<GewtMaterialLabel> aHtmlMixin =
			new HTMLMixin<>(this);

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getHTML() {
			return aHtmlMixin.getHTML();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setHTML(String sHtml) {
			aHtmlMixin.setHTML(sHtml);
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
