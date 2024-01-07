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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardAction;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardImage;
import gwt.material.design.client.ui.MaterialCardReveal;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialImage;

import de.esoco.ewt.GewtMaterial;
import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * GWT Material implementation of card layouts.
 *
 * @author eso
 */
public class MaterialCardLayout extends AbstractMaterialLayout {

	private MaterialCardContent cardContent;

	private MaterialCardAction cardAction;

	private MaterialCardImage cardImage;

	@Override
	public void addWidget(HasWidgets container, Widget widget, StyleData style,
		int index) {
		if (widget instanceof AbstractButton) {
			if (cardAction == null) {
				cardAction = new MaterialCardAction();
				cardAction.add(widget);
				widget = cardAction;

				GewtMaterial.checkApplyAlignment(widget, style);
			} else {
				cardAction.add(widget);
				widget = null;
			}
		} else if (widget instanceof MaterialImage && cardImage == null &&
			cardContent == null) {
			// if first image on card wrap it into card image
			cardImage = new MaterialCardImage();
			cardImage.add(widget);
			widget = cardImage;
		} else if (widget instanceof MaterialCardContent) {
			cardContent = (MaterialCardContent) widget;
		} else if (widget instanceof MaterialCardAction) {
			cardAction = (MaterialCardAction) widget;
		} else if (widget instanceof MaterialCardImage) {
			cardImage = (MaterialCardImage) widget;
		} else if (!(widget instanceof MaterialCardReveal)) {
			if (cardContent == null) {
				if (widget instanceof MaterialCardTitle && cardImage != null) {
					cardImage.add(widget);
					widget = null;
				} else {
					cardContent = new MaterialCardContent();
					cardContent.add(widget);
					widget = cardContent;
				}
			} else {
				cardContent.add(widget);
				widget = null;
			}
		}

		if (widget != null) {
			super.addWidget(container, widget, style, index);
		}
	}

	@Override
	public void clear(HasWidgets container) {
		super.clear(container);

		cardContent = null;
		cardAction = null;
	}

	@Override
	protected MaterialWidget creatMaterialLayoutContainer(Container container,
		StyleData containerStyle) {
		return new MaterialCard();
	}
}
