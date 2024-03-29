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
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.ui.MaterialCardTitle;

import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * A {@link MaterialCardTitle} subclass that also implements
 * {@link ImageAttribute}.
 *
 * @author eso
 */
public class GewtMaterialCardTitle extends MaterialCardTitle
	implements ImageAttribute {

	private ImageAttributeMixin<GewtMaterialCardTitle> imageAttrMixin =
		new ImageAttributeMixin<>(this);

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return getIcon().addClickHandler(handler);
	}

	@Override
	public Image getImage() {
		return imageAttrMixin.getImage();
	}

	@Override
	public void setImage(Image image) {
		imageAttrMixin.setImage(image);
	}
}
